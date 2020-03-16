package view.rulesView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.game.GameSetup;
import view.mainMenu.MainMenuPresenter;
import view.mainMenu.MainMenuView;

public class RulesPresenter {
    private RulesView view;

    public RulesPresenter(RulesView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBackBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameSetup setup = new GameSetup();
                MainMenuView mainMenuView = new MainMenuView();
                MainMenuPresenter presenter = new MainMenuPresenter(mainMenuView);
                view.getBackBtn().getScene().setRoot(mainMenuView);
            }
        });

    }
}
