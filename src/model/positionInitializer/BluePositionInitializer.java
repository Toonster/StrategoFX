package model.positionInitializer;

import model.common.Position;

import java.util.ArrayList;
import java.util.List;

public class BluePositionInitializer implements IPositionInitializer {

    List<Position> positions;

    public BluePositionInitializer() {
        positions = new ArrayList<>();
    }

    @Override
    public List<Position> initializePositions() {
        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                positions.add(new Position(x, y));
            }
        }
        return positions;
    }
}
