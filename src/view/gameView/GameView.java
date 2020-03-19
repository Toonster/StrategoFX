package view.gameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import view.boardView.BoardView;

public class GameView extends HBox {

    private GridPane board;
    private TilePane redCapturedUnits;
    private TilePane blueCapturedUnits;
    private VBox vBox;
    private VBox rightVBox;
    private Button saveBtn;
    private Button exitBtn;
    private Label selectedUnitTitleLbl;
    private Label selectedUnitLbl;
    private Label log;
    private Button rulesBtn;
    private Button btnSettings;

    public GameView() {
        intialiseNodes();
        layoutNodes();
    }

    private void intialiseNodes() {
        board = new BoardView();
        redCapturedUnits = new TilePane();
        blueCapturedUnits = new TilePane();
        vBox = new VBox();
        rightVBox = new VBox();
        saveBtn = new Button("Save Game");
        exitBtn = new Button("Exit");
        log = new Label();
        rulesBtn = new Button("Rules");
        btnSettings = new Button("Settings");
        selectedUnitTitleLbl = new Label("Selected Unit");
        selectedUnitLbl = new Label("Selected Unit");
    }

    private void layoutNodes() {
        this.getStylesheets().add("stratego.css");
        saveBtn.setId("setupBtn");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        rulesBtn.setMaxWidth(Double.MAX_VALUE);
        btnSettings.setMaxWidth(Double.MAX_VALUE);
        selectedUnitTitleLbl.setMaxWidth(Double.MAX_VALUE);
        selectedUnitLbl.setMaxWidth(Double.MAX_VALUE);
        log.setMaxWidth(Double.MAX_VALUE);
        exitBtn.setId("setupBtn");
        rulesBtn.setId("setupBtn");
        btnSettings.setId("setupBtn");
        selectedUnitTitleLbl.setId("selectedUnitLbl");
        selectedUnitLbl.setId("selectedUnitLbl");
        redCapturedUnits.setId("capturedUnitsPane");
        blueCapturedUnits.setId("capturedUnitsPane");
        selectedUnitTitleLbl.setAlignment(Pos.CENTER);
        selectedUnitLbl.setAlignment(Pos.CENTER);
        log.setId("logLbl");
        log.setMinHeight(200);
        log.setAlignment(Pos.CENTER);
        Image image = new Image("stratego.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0,1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        redCapturedUnits.setPrefColumns(10);
        redCapturedUnits.setAlignment(Pos.CENTER);
        redCapturedUnits.setMinHeight(100);
        redCapturedUnits.setMaxWidth(board.getMaxWidth());
        blueCapturedUnits.setMaxWidth(board.getMaxWidth());
        blueCapturedUnits.setMinHeight(100);
        blueCapturedUnits.setAlignment(Pos.CENTER);
        blueCapturedUnits.setPrefColumns(10);
        vBox.setSpacing(10);
        vBox.setMinWidth(300);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        vBox.getChildren().addAll(selectedUnitTitleLbl, selectedUnitLbl, log, rulesBtn, btnSettings, saveBtn, exitBtn);
        vBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(blueCapturedUnits, board, redCapturedUnits);
        rightVBox.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vBox, rightVBox);
        this.setAlignment(Pos.CENTER);
        this.setFillHeight(true);
        HBox.setHgrow(rightVBox, Priority.ALWAYS);
        rightVBox.setMaxSize(1000,1000);
    }

    protected GridPane getBoard() {
        return board;
    }

    protected Button getSaveBtn() {
        return saveBtn;
    }

    protected Label getLog() {
        return log;
    }

    protected Button getExitBtn() {
        return exitBtn;
    }

    protected TilePane getRedCapturedUnits() {
        return redCapturedUnits;
    }

    protected TilePane getBlueCapturedUnits() {
        return blueCapturedUnits;
    }

    protected Button getBtnSettings() {
        return btnSettings;
    }

    protected Button getRulesBtn() {
        return rulesBtn;
    }

    protected Label getSelectedUnitLbl() {
        return selectedUnitLbl;
    }
}
