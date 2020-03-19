package view.settingsView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.mainMenu.MainMenuPresenter;

public class SettingsPresenter {

    private SettingsView view;
    private Pane previousPane;
    private boolean isMusicPlaying;


    public SettingsPresenter(SettingsView view, Pane previousPane) {
        this.view = view;
        this.previousPane = previousPane;
        addEventHandlers();
        updateView();
    }

    private void updateView() {
    }

    private void addEventHandlers() {
        view.getBtnFullscreen().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Stage stage = (Stage) view.getScene().getWindow();

                if (!stage.isFullScreen()) {
                    stage.setFullScreen(true);
                } else {
                    stage.setFullScreen(false);
                }
            }
        });
        view.getBtnback().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                view.getScene().setRoot(previousPane);
            }
        });

        view.getBtnSound().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
/*                if (!isMusicPlaying) {
                    MainMenuView mainMenuView = new MainMenuView();
                    mainMenuView.getMusic().play();
                    mainMenuView.getMusic().setCycleCount(100);
                    isMusicPlaying = true;
                } else {
                    mainMenuView.getMusic().stop();
                    isMusicPlaying = false;
        }*/
            }
        });
    }
}
