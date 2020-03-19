package model.game;

import model.unit.UnitColor;

public class GameResult {

    private UnitColor winner;
    private GameStatus status;

    public GameResult(GameStatus status) {
        this.status = status;
        processResult();
    }

    private void processResult() {
        if (status == GameStatus.RED_CAPTURED || status == GameStatus.RED_NO_MOVES) {
            winner = UnitColor.BLUE;
        } else {
            winner = UnitColor.RED;
        }
    }

    public UnitColor getWinner() {
        return winner;
    }
}
