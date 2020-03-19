package model.ai;

import model.common.Position;
import model.positionInitializer.IPositionInitializer;
import model.positionInitializer.PositionInitializerFactory;
import model.unit.Unit;
import model.unit.UnitColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AiUnitSetup {

    Map<Position, Unit> unitStartingPositions;
    List<Unit> unplacedUnits;
    Random rand;

    public AiUnitSetup(List<Unit> unplacedUnits) {
        rand = new Random();
        this.unplacedUnits = unplacedUnits;
        unitStartingPositions = new HashMap<>();
        PositionInitializerFactory factory = new PositionInitializerFactory();
        IPositionInitializer initializer = factory.create(UnitColor.RED);
        List<Position> startingPositions = initializer.initializePositions();
        for (Position startingPosition : startingPositions) {
            unitStartingPositions.put(startingPosition, null);
        }
        placeUnits();
    }

    private void placeUnits() {
        for (Position position : unitStartingPositions.keySet()) {
            Unit unitToPlace = unplacedUnits.get(rand.nextInt(unplacedUnits.size()));
            unitStartingPositions.replace(position, unitToPlace);
            unplacedUnits.remove(unitToPlace);
        }
    }

    public Map<Position, Unit> getUnitStartingPositions() {
        return unitStartingPositions;
    }
}
