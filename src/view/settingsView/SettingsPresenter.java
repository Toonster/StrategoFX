package view.settingsView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.mediaplayer.MediaPlayer;

public class SettingsPresenter {

    private SettingsView view;
    private Pane previousPane;


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
                if (MediaPlayer.isMusicPlaying()) {
                    MediaPlayer.stopMusic();
                } else {
                    MediaPlayer.playMusic();
                }
            }
        });
    }
}
