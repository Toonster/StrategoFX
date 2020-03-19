package view.rulesView;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class RulesView extends VBox {
    private Label titelLbl;
    private Button backBtn;
    private Label rulesLbl;

    public RulesView() {
        initialiseNodes();
        layoutNodes();
    }

    private void layoutNodes() {
        getStylesheets().add("stratego.css");
        this.getChildren().add(titelLbl);
        this.getChildren().add(rulesLbl);
        this.getChildren().add(backBtn);
        this.setSpacing(10);
        setAlignment(Pos.CENTER);
        Image image = new Image("stratego.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
    }

    private void initialiseNodes() {
        titelLbl = new Label("Stratego Game rules");
        rulesLbl = new Label();
        backBtn = new Button("Back");
        backBtn.setId("main");
        rulesLbl.setId("rules");
        titelLbl.setId("titel");
    }

    protected Button getBackBtn() {
        return backBtn;
    }

    protected Label getRulesLbl() {
        return rulesLbl;
    }
}
