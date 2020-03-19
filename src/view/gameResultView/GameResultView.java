package view.gameResultView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class GameResultView extends VBox {

    private Label lblMessage;
    private Label lblResult;
    private Button btnStartNew;
    private Button btnExit;

    public GameResultView() {
        initialiseNodes();
        layoutNodes();
    }

    private void layoutNodes() {
        this.getStylesheets().add("stratego.css");
        this.getChildren().add(lblResult);
        this.getChildren().add(lblMessage);
        this.getChildren().add(btnStartNew);
        this.getChildren().add(btnExit);
    }

    private void initialiseNodes() {
        lblMessage = new Label("Message");
        lblResult = new Label("Result");
        btnExit = new Button("Exit");
        btnStartNew = new Button("Start new game");
        btnExit.setId("setupBtn");
        btnStartNew.setId("setupBtn");
        lblMessage.setId("resultMessage");
        lblResult.setId("result");
        Image image = new Image("stratego.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0,1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

    protected Button getBtnStartNew() {
        return btnStartNew;
    }

    protected Button getBtnExit() {
        return btnExit;
    }

    protected Label getLblMessage() {
        return lblMessage;
    }

    protected Label getLblResult() {
        return lblResult;
    }
}

