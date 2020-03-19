package model.ai;

import model.common.Position;
import model.common.PositionGenerator;
import model.unit.Unit;

import java.util.List;
import java.util.Random;

public class Ai {

    private List<Unit> units;
    private Random rand;
    private PositionGenerator generator;

    public Ai(List<Unit> units) {
        this.units = units;
        this.rand = new Random();
        this.generator = new PositionGenerator();
    }

    public Unit chooseUnit() {
        boolean isValidUnitChosen = false;
        Unit chosenUnit = null;
        while (!isValidUnitChosen) {
            chosenUnit = units.get(rand.nextInt(units.size()));
            if (!chosenUnit.isCaptured()) {
                isValidUnitChosen = true;
            }
        }
        return chosenUnit;
    }

    public Position choosePosition() {
        return generator.generatePosition(10, 10);
    }
}
