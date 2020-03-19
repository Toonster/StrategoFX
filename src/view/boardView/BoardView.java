package view.boardView;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class BoardView extends GridPane {

    public BoardView() {
        initializeNodes();
        layoutNodes();
    }

    private void initializeNodes() {
        int paneRows = 10;
        int paneColumns = 10;
        for (int i = 0; i < paneRows; i++) {
            for (int j = 0; j < paneColumns; j++) {
                Button btn = new Button();
                this.add(btn, i, j);
            }
        }
    }

    private void layoutNodes() {
        this.getStylesheets().add("stratego.css");
        this.setBackground(new Background(new BackgroundImage(new Image("stratego.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, false, Side.BOTTOM, 0, false),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true))));
        Image image = new Image("grid.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        this.setAlignment(Pos.CENTER);
        for (Node btn : this.getChildren()) {
            ((Button) btn).setMinSize(65, 65);
            ((Button) btn).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setHgrow(btn, Priority.ALWAYS);
            GridPane.setVgrow(btn, Priority.ALWAYS);
            GridPane.setFillWidth(btn, true);
            GridPane.setFillHeight(btn, true);
        }
        this.setMaxSize(Screen.getPrimary().getBounds().getMaxY() * 0.7, 800);
    }
}
