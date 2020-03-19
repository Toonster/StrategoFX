package model.unitPositionPreset;

import model.common.Position;
import model.unit.Rank;
import model.unit.Unit;
import model.unit.UnitColor;
import model.unit.UnitManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerUnitPreset {

    Map<Position, Unit> unitStartingPositions;
    List<Unit> units;
    List<Unit> bombs;
    List<Unit> captains;
    List<Unit> scouts;
    List<Unit> miners;
    List<Unit> sergeants;
    List<Unit> lieutenants;
    List<Unit> majors;
    List<Unit> colonels;

    public PlayerUnitPreset(UnitManager unitManager) {
        unitStartingPositions = new HashMap<>();
        units = new ArrayList<>();
        units = unitManager.getUnitsOfColor(UnitColor.BLUE);
        bombs = new ArrayList<>();
        bombs = getUnitsOfRank(Rank.Bomb);
        captains = new ArrayList<>();
        captains = getUnitsOfRank(Rank.Captain);
        scouts = new ArrayList<>();
        scouts = getUnitsOfRank(Rank.Scout);
        miners = new ArrayList<>();
        miners = getUnitsOfRank(Rank.Miner);
        sergeants = new ArrayList<>();
        sergeants = getUnitsOfRank(Rank.Sergeant);
        lieutenants = new ArrayList<>();
        lieutenants = getUnitsOfRank(Rank.Lieutenant);
        majors = new ArrayList<>();
        majors = getUnitsOfRank(Rank.Major);
        colonels = new ArrayList<>();
        colonels = getUnitsOfRank(Rank.Colonel);
    }

    private List<Unit> getUnitsOfRank(Rank rank) {
        return units.stream().filter(unit -> unit.getRank() == rank).collect(Collectors.toList());
    }

    public void placeUnits() {
        unitStartingPositions.put(new Position(0, 6), captains.get(0));
        unitStartingPositions.put(new Position(1, 6), scouts.get(0));
        unitStartingPositions.put(new Position(2, 6), sergeants.get(0));
        unitStartingPositions.put(new Position(3, 6), units.stream().filter(unit -> unit.getRank() == Rank.General).findFirst().orElse(null));
        unitStartingPositions.put(new Position(4, 6), captains.get(1));
        unitStartingPositions.put(new Position(5, 6), scouts.get(1));
        unitStartingPositions.put(new Position(6, 6), scouts.get(2));
        unitStartingPositions.put(new Position(7, 6), units.stream().filter(unit -> unit.getRank() == Rank.Marshal).findFirst().orElse(null));
        unitStartingPositions.put(new Position(8, 6), scouts.get(3));
        unitStartingPositions.put(new Position(9, 6), captains.get(2));

        unitStartingPositions.put(new Position(0, 7), lieutenants.get(0));
        unitStartingPositions.put(new Position(1, 7), scouts.get(4));
        unitStartingPositions.put(new Position(2, 7), majors.get(0));
        unitStartingPositions.put(new Position(3, 7), lieutenants.get(1));
        unitStartingPositions.put(new Position(4, 7), bombs.get(0));
        unitStartingPositions.put(new Position(5, 7), scouts.get(5));
        unitStartingPositions.put(new Position(6, 7), majors.get(1));
        unitStartingPositions.put(new Position(7, 7), majors.get(2));
        unitStartingPositions.put(new Position(8, 7), colonels.get(0));
        unitStartingPositions.put(new Position(9, 7), miners.get(0));

        unitStartingPositions.put(new Position(0, 8), sergeants.get(1));
        unitStartingPositions.put(new Position(1, 8), colonels.get(1));
        unitStartingPositions.put(new Position(2, 8), units.stream().filter(unit -> unit.getRank() == Rank.Spy).findFirst().orElse(null));
        unitStartingPositions.put(new Position(3, 8), miners.get(1));
        unitStartingPositions.put(new Position(4, 8), bombs.get(1));
        unitStartingPositions.put(new Position(5, 8), scouts.get(6));
        unitStartingPositions.put(new Position(6, 8), captains.get(3));
        unitStartingPositions.put(new Position(7, 8), lieutenants.get(2));
        unitStartingPositions.put(new Position(8, 8), lieutenants.get(3));
        unitStartingPositions.put(new Position(9, 8), bombs.get(2));

        unitStartingPositions.put(new Position(0, 9), miners.get(2));
        unitStartingPositions.put(new Position(1, 9), bombs.get(3));
        unitStartingPositions.put(new Position(2, 9), sergeants.get(2));
        unitStartingPositions.put(new Position(3, 9), bombs.get(4));
        unitStartingPositions.put(new Position(4, 9), sergeants.get(3));
        unitStartingPositions.put(new Position(5, 9), scouts.get(7));
        unitStartingPositions.put(new Position(6, 9), miners.get(3));
        unitStartingPositions.put(new Position(7, 9), miners.get(4));
        unitStartingPositions.put(new Position(8, 9), bombs.get(5));
        unitStartingPositions.put(new Position(9, 9), units.stream().filter(unit -> unit.getRank() == Rank.Flag).findFirst().orElse(null));
    }

    public Map<Position, Unit> getUnitStartingPositions() {
        return unitStartingPositions;
    }
}
