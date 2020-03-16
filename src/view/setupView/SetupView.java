package view.setupView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.unit.Unit;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import view.boardView.BoardView;


public class SetupView extends HBox {

    private GridPane board;
    private ListView<Unit> listOfUnplacedUnits;
    private VBox vBox;
    private TextArea infoText;
    private Button continueBtn;
    private Button standardConfigBtn;
    private Button exitBtn;
    private VBox rightVBox;
    private Label unplacedUnitsLabel;

    public SetupView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        board = new BoardView();
        listOfUnplacedUnits = new ListView<>();
        vBox = new VBox();
        infoText = new TextArea("Welcome to the setup phase of Stratego. Place your units on the bottom 4 rows of the board. Units can be overwritten. A standard configuration is available. Click the next button once all your units have been placed and you want to continue.");
        continueBtn = new Button("Continue");
        standardConfigBtn = new Button("Use preset");
        exitBtn = new Button("Exit");
        rightVBox = new VBox();
        unplacedUnitsLabel = new Label("Unplaced Units");
    }

    private void layoutNodes() {

        continueBtn.setId("setupBtn");
        standardConfigBtn.setId("setupBtn");
        exitBtn.setId("setupBtn");
        this.getStylesheets().add("stratego.css");
        Image image = new Image("stratego.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0,1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        listOfUnplacedUnits.setPrefWidth(200);
        vBox.setPrefWidth(200);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(0,10,0,0));
        rightVBox.setPadding(new Insets(10));
        listOfUnplacedUnits.setPadding(new Insets(0,0,0,10));
        unplacedUnitsLabel.setTextFill(Color.WHITE);
        unplacedUnitsLabel.setStyle("-fx-font-size: 20");
        infoText.setWrapText(true);
        infoText.setFont(new Font(15));
        infoText.setEditable(false);
        continueBtn.setMaxWidth(Double.MAX_VALUE);
        standardConfigBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        this.setPadding(new Insets(10));
        rightVBox.getChildren().addAll(unplacedUnitsLabel, listOfUnplacedUnits);
        vBox.getChildren().addAll(infoText, continueBtn, standardConfigBtn, exitBtn);
        listOfUnplacedUnits.setEditable(true);
        this.getChildren().addAll(vBox, board, rightVBox);
        HBox.setHgrow(board, Priority.ALWAYS);
        rightVBox.setMinSize(100,300);
        vBox.setMinSize(100,500);
        vBox.setAlignment(Pos.CENTER);
        rightVBox.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
    }

    protected ListView<Unit> getListOfUnplacedUnits() {
        return listOfUnplacedUnits;
    }

    protected GridPane getBoard() {
        return board;
    }

    protected Button getStandardConfigBtn() {
        return standardConfigBtn;
    }

    protected Button getContinueBtn() {
        return continueBtn;
    }

    protected Button getExitBtn() {
        return exitBtn;
    }
}
