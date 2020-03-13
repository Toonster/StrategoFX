package view.gameView;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.unit.Unit;
import view.boardView.BoardView;

public class GameView extends BorderPane {

    private GridPane board;
    private ListView<Unit> redCapturedUnits;
    private ListView<Unit> blueCapturedUnits;
    private Button saveBtn;

    public GameView() {
        intialiseNodes();
        layoutNodes();
    }

    private void intialiseNodes() {
        board = new BoardView();
        redCapturedUnits = new ListView<>();
        blueCapturedUnits = new ListView<>();
        saveBtn = new Button("Save Game");
    }

    private void layoutNodes() {
        this.getStylesheets().add("stratego.css");
        this.setBackground(new Background(new BackgroundImage(new Image("stratego.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT,0,false, Side.BOTTOM,0,false),
                new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,true,true,true,true))));
        redCapturedUnits.setPrefHeight(50);
        redCapturedUnits.setOrientation(Orientation.HORIZONTAL);
        blueCapturedUnits.setPrefHeight(50);
        blueCapturedUnits.setOrientation(Orientation.HORIZONTAL);
        this.setPadding(new Insets(10));
        this.setCenter(board);
        this.setTop(blueCapturedUnits);
        this.setBottom(redCapturedUnits);
        this.setLeft(saveBtn);
    }

    protected GridPane getBoard() {
        return board;
    }

    protected ListView<Unit> getRedCapturedUnits() {
        return redCapturedUnits;
    }

    protected ListView<Unit> getBlueCapturedUnits() {
        return blueCapturedUnits;
    }

    protected Button getSaveBtn() {
        return saveBtn;
    }
}
