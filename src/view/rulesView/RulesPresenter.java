package view.rulesView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.game.GameSetup;
import view.mainMenu.MainMenuPresenter;
import view.mainMenu.MainMenuView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class RulesPresenter {
    private RulesView view;

    public RulesPresenter(RulesView view) {
        this.view = view;
        updateView();
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBackBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.getScene().getWindow().hide();
            }
        });
    }

    private void updateView() {
        Path filePath = Paths.get("src","resources", "files","rules.txt");
        try {
            Scanner fileScanner = new Scanner(filePath);
            StringBuilder builder = new StringBuilder();
            while (fileScanner.hasNext()){
                builder.append(fileScanner.nextLine());
                builder.append("\n");
            }
            view.getRulesLbl().setText(builder.toString());
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
