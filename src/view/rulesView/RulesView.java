package view.rulesView;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class RulesView extends GridPane {
    private Label titelLbl;
    private Button backBtn;
    private Label rulesLbl;

    public RulesView(){
        initialiseNodes();
        layoutNodes();
    }

    private void layoutNodes() {
        getStylesheets().add("stratego.css");
        add(titelLbl, 3, 1, 2, 1);
        add(rulesLbl, 3,2,2,1);
        add(backBtn, 3,3,3,1);
        setAlignment(Pos.CENTER);
        Image image = new Image("stratego.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0,1.0, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
    }

    private void initialiseNodes() {
        titelLbl = new Label("Stratego Game rules");
        rulesLbl = new Label();
        Path bestandPath = Paths.get("resources" + File.separator + "files" + File.separator + "rules.txt");
        try {
            Scanner fileScanner = new Scanner(bestandPath);
            StringBuilder builder = new StringBuilder();
            while (fileScanner.hasNext()){
                builder.append(fileScanner.nextLine());
                builder.append("\n");
            }
            rulesLbl.setText(builder.toString());
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        backBtn = new Button("Back");
        backBtn.setId("main");
        rulesLbl.setId("rules");
        titelLbl.setId("titel");
    }

    public Button getBackBtn() {
        return backBtn;
    }
}
