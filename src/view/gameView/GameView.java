package view.gameView;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import model.unit.Unit;
import view.boardView.BoardView;

public class GameView extends BorderPane {

    private GridPane board;
    private ListView<Unit> redCapturedUnits;
    private TilePane redUnits;
    private TilePane blueUnits;
    private ListView<Unit> blueCapturedUnits;
    private VBox vBox;
    private Button saveBtn;
    private Button exitBtn;
    private TextArea log;

    public GameView() {
        intialiseNodes();
        layoutNodes();
    }

    private void intialiseNodes() {
        board = new BoardView();
        redUnits = new TilePane();
        blueUnits = new TilePane();
        redCapturedUnits = new ListView<>();
        blueCapturedUnits = new ListView<>();
        vBox = new VBox();
        saveBtn = new Button("Save Game");
        exitBtn = new Button("Exit");
        log = new TextArea();
    }

    private void layoutNodes() {
        this.getStylesheets().add("stratego.css");
        saveBtn.setId("setupBtn");
        exitBtn.setId("setupBtn");
        Image image = new Image("stratego.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0,1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        redCapturedUnits.setMaxHeight(100);
        redCapturedUnits.setOrientation(Orientation.HORIZONTAL);
        blueCapturedUnits.setMaxHeight(100);
        blueCapturedUnits.setOrientation(Orientation.HORIZONTAL);
        redUnits.setPrefColumns(2);
        redUnits.setAlignment(Pos.CENTER);
        blueUnits.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));
        this.setCenter(board);
        this.setTop(blueUnits);
        this.setBottom(redUnits);
        vBox.getChildren().addAll(log, saveBtn, exitBtn);
        vBox.setAlignment(Pos.CENTER);
        board.setAlignment(Pos.CENTER);
        this.setLeft(vBox);
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

    protected TextArea getLog() {
        return log;
    }

    protected Button getExitBtn() {
        return exitBtn;
    }

    protected TilePane getRedUnits() {
        return redUnits;
    }

    public TilePane getBlueUnits() {
        return blueUnits;
    }
}
