package model.game;

import model.ai.Ai;
import model.battle.Battle;
import model.battle.BattleResult;
import model.board.Board;
import model.board.Tile;
import model.common.Position;
import model.exception.StrategoException;
import model.turn.Turn;
import model.unit.Rank;
import model.unit.Unit;
import model.unit.UnitColor;
import model.unit.UnitManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * de werking van het spel wordt hierin uitgewerkt.
 * Game is het aanspreekpunt van het model voor de presenter
 *
 * @author LABR, WEBR
 * @version 1.12
 */

public class Game {

    private ArrayList<Unit> units;
    private Board board;
    private Turn currentTurn;
    private Ai ai;
    private Map<Unit, Integer> visibleUnitsWithVisibilityCounter;
    private GameStatus status;

    /**
     * De constructor krijgt een map binnen die alle units met hun unieke posities bevat.
     * <p>
     * Via deze map initialiseert de ctr al het benodigde
     *
     * @param initialUnitPositions de beginplaatsen van units
     */

    public Game(Map<Position, Unit> initialUnitPositions) {
        board = new Board();
        units = new ArrayList<>(initialUnitPositions.values());
        for (Map.Entry<Position, Unit> entry : initialUnitPositions.entrySet()) {
            board.setUnitIdOnTile(entry.getKey(), entry.getValue().getId());
        }
        List<Unit> aiUnits = units.stream().filter(unit -> unit.isColor(UnitColor.RED)).collect(Collectors.toList());
        ai = new Ai(aiUnits);
        currentTurn = new Turn(UnitColor.BLUE);
        visibleUnitsWithVisibilityCounter = new HashMap<>();
        status = GameStatus.RUNNING;
    }

    /**
     * Deze methode gaat de unit selecteren op de plaats waar je hebt geklikt door de unit op te halen
     * en deze mee te geven aan de currentTurn, we slagen ook de start positie op.
     *
     * @param source de beginpositie van de geselcteerde pion
     */

    public void selectUnit(Position source) throws StrategoException {
        Unit selectedUnit = getUnitOnTile(source);
        currentTurn.setSelectedUnit(selectedUnit);
        currentTurn.setStart(source);
    }

    /**
     * deze methode zorgt ervoor dat onze selected Unit op null komt te staan zodat we een nieuwe kunnen selecteren.
     */

    public void unSelectUnit() throws StrategoException {
        currentTurn.setSelectedUnit(null);
    }

    /**
     * Deze methode is de kern van Game. Ze gaat de move uitvoeren en het resultaat verwerken.
     * 1. Valideer de move
     * 2. Verwerk de move
     *
     * @param destination de plaats waar de gebruiker klikt
     * @return true/false afh of de move verwerkt is
     */

    public boolean processMove(Position destination) throws StrategoException {
        isMoveValid(destination);
        currentTurn.setDestination(destination);
        board.clearTile(currentTurn.getStart());
        Unit selectedUnit = currentTurn.getSelectedUnit();
        if (!board.isTileOccupied(destination)) {
            board.setUnitIdOnTile(destination, selectedUnit.getId());
        } else {
            Unit enemyUnit = getUnitOnTile(destination);
            processBattleResult(selectedUnit, enemyUnit);
        }
        updateStatus();
        updateUnitVisibility();
        nextTurn();
        return true;
    }

    /**
     * Deze methode gaat kijken of een move uitermate wel mag gebeuren
     *
     * @param destination de plaats waar de gebruiker klikt
     * @return true/false als de move valid is of niet
     */

    private boolean isMoveValid(Position destination) throws StrategoException {
        if (!canSelectedUnitReach(destination)) {
            throw new StrategoException("Unit can't reach");
        }
        if (!board.isRouteAvailable(currentTurn.getStart(), destination)) {
            throw new StrategoException("Route is blocked");
        }
        if (board.isTileOccupied(destination) && isFriendlyUnitAt(destination)) {
            throw new StrategoException("Friendly unit on tile");

        }
        if (!board.isTileAccessible(destination)) {
            throw new StrategoException("Can't move to water");

        }
        return true;
    }

    /**
     * Deze methode checkt of een unit naar een positie kan stappen
     *
     * @param destination de plaats waar de gebruiker klikt
     * @return boolean
     */

    private boolean canSelectedUnitReach(Position destination) {
        Unit selectedUnit = currentTurn.getSelectedUnit();
        Position start = currentTurn.getStart();
        Position distance = start.getDistanceTo(destination);
        return selectedUnit.canReach(distance);
    }

    /**
     * Dezen methode checkt of er een unit van hetzelfde team op de destination tile staat
     *
     * @param destination de plaats waar de gebruiker klikt
     * @return boolean
     */

    private boolean isFriendlyUnitAt(Position destination) {
        int idOnDestination = board.getUnitIdOnTile(destination);
        return getUnitById(idOnDestination).isColor(currentTurn.getTurnType());
    }

    /**
     * Deze methode zorgt ervoor dat er een nieuwe Turn wordt aangemaakt met een kleur verschillend van de vorige
     */

    private void nextTurn() {
        UnitColor color = currentTurn.isType(UnitColor.BLUE) ? UnitColor.RED : UnitColor.BLUE;
        currentTurn = new Turn(color);
    }

    /**
     * Deze methode maakt een Battle object aan en verwerkt het resultaat
     *
     * @param attackingUnit de aanvallende unit
     * @param defendingUnit de verdedigende unit
     */

    private void processBattleResult(Unit attackingUnit, Unit defendingUnit) {
        Battle battle = new Battle(attackingUnit, defendingUnit);
        BattleResult battleResult = battle.getResult();
        Position destination = currentTurn.getDestination();
        if (battleResult == BattleResult.DRAW) {
            board.clearTile(destination);
        }
        if (battleResult == BattleResult.WIN) {
            board.setUnitIdOnTile(destination, attackingUnit.getId());
        }
        if (battleResult == BattleResult.LOSS) {
            visibleUnitsWithVisibilityCounter.put(defendingUnit, 3);
        }
    }

    /**
     * Deze methode gaat de map met visible units updaten, elke integer value krijgt -1,
     * telkens als de value van een entry in de map 0 is wordt deze verwijderd.
     */

    private void updateUnitVisibility() {
        for (Iterator<Map.Entry<Unit, Integer>> it = visibleUnitsWithVisibilityCounter.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Unit, Integer> entry = it.next();
            if (entry.getValue() == 0) {
                it.remove();
            } else {
                visibleUnitsWithVisibilityCounter.replace(entry.getKey(), entry.getValue() - 1);
            }
        }
    }

    /**
     * Deze methode houdt de status van de Game bij
     */

    private void updateStatus() {
        if (isFlagCaptured(UnitColor.RED)) {
            status = GameStatus.RED_CAPTURED;
        }
        if (isFlagCaptured(UnitColor.BLUE)) {
            status = GameStatus.BLUE_CAPTURED;
        }
        if (!areMovesAvailable(UnitColor.RED)) {
            status = GameStatus.RED_NO_MOVES;
        }
        if (!areMovesAvailable(UnitColor.BLUE)) {
            status = GameStatus.BLUE_NO_MOVES;
        }
    }

    /**
     * Deze methode checkt of de vlag van een bepaalde kleur gecaptured is
     *
     * @param color de kleur van de vlag
     * @return boolean
     */

    private boolean isFlagCaptured(UnitColor color) {
        return units.stream().anyMatch(unit -> unit.isColor(color) && unit.isCaptured() && unit.getRank() == Rank.Flag);
    }

    /**
     * Deze methode checkt of de units van een bepaalde kleur nog kunnen bewegen
     *
     * @param color de kleur van de units
     * @return boolean
     */

    private boolean areMovesAvailable(UnitColor color) {
        return units.stream().anyMatch(unit -> unit.isColor(color) && !unit.isCaptured() && unit.getRank().getMovementspeed() >= 1);
    }

    /**
     * Deze methode verwerkt de move van de computer die looped tot er een valid move gekozen wordt door de computer
     */

    public void computerMove() {
        boolean isMoveCompleted = false;
        while (!isMoveCompleted) {
            Unit selectedUnit = ai.chooseUnit();
            Position source = board.getPositionById(selectedUnit.getId());
            try {
                selectUnit(source);
                Position destination = ai.choosePosition();
                if (isMoveValid(destination)) {
                    isMoveCompleted = true;
                    processMove(destination);
                }
            } catch (StrategoException ignored) {
            }
        }
    }

    /**
     * Getter om unit te gaan halen met een id
     *
     * @param unitId de unitId van de unit
     * @return Unit object
     */

    private Unit getUnitById(int unitId) {
        return units.stream().filter(unit -> unit.getId() == unitId).findFirst().orElse(null);
    }

    /**
     * Getter om unit te gaan halen op een positie
     *
     * @param position de positie van de tile
     * @return Unit object
     */

    public Unit getUnitOnTile(Position position) {
        int unitId = board.getUnitIdOnTile(position);
        return getUnitById(unitId);
    }

    /**
     * Check of er een unit geselecteerd is
     *
     * @return boolean
     */

    public boolean isUnitSelected() {
        return this.currentTurn.isUnitSelected();
    }

    /**
     * Getter om de captured units van een bepaalde kleur te gaan halen
     *
     * @param color kleur van de units
     * @return Lijst van units
     */

    public List<Unit> getCapturedUnits(UnitColor color) {
        return units.stream().filter(unit -> unit.isColor(color) && unit.isCaptured()).collect(Collectors.toList());
    }

    /**
     * Getter om de zichtbare units te gaan halen
     *
     * @return Lijst van units
     */

    public List<Unit> getVisibleUnits() {
        return new ArrayList<>(visibleUnitsWithVisibilityCounter.keySet());
    }

    /**
     * Query voor presenter om te weten of het spel gedaan is
     *
     * @return boolean
     */

    public boolean isGameOver() {
        return status != GameStatus.RUNNING;
    }

    /**
     * Getter om gameStatus te gaan halen
     *
     * @return gameStatus
     */

    public GameStatus getStatus() {
        return status;
    }

    /**
     * Getter om het gameField te gaan halen bij Board
     *
     * @return gameField opbject
     */

    public Tile[][] getGameField() {
        return board.getGameField();
    }

    /**
     * Methode om een onvolledige lijst van units aan te vullen.
     * Dit wordt gebruikt bij het laden van een spel
     */

    public void completeUnitList(UnitManager unitManager) {
        List<Unit> freshUnitList = unitManager.getUnits();
        for (Unit unitToAdd : freshUnitList) {
            if (units.stream().noneMatch(unit -> unit.getId() == unitToAdd.getId())) {
                unitToAdd.setCaptured();
                units.add(unitToAdd);
            }
        }
    }

    /**
     * Methode om de huidige selected unit te gaan halen bij turn
     *
     * @return unit object
     */

    public Unit getSelectedUnit() {
        return this.currentTurn.getSelectedUnit();
    }

    /**
     * vraagt aan board om te de positie waarop een id staat te returnen en returnt deze
     *
     * @param unit de unit in kwestie
     * @return positie van de unit op het bord
     */

    public Position getUnitPosition(Unit unit) {
        return board.getPositionById(unit.getId());
    }
}


