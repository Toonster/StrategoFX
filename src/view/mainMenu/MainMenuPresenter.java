package view.mainMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.exception.StrategoException;
import model.fileManager.GameFileManager;
import model.game.Game;
import model.game.GameSetup;
import view.gameView.GamePresenter;
import view.gameView.GameView;
import view.rulesView.RulesPresenter;
import view.rulesView.RulesView;
import view.settingsView.SettingsPresenter;
import view.settingsView.SettingsView;
import view.setupView.SetupPresenter;
import view.setupView.SetupView;

import java.io.File;

public class MainMenuPresenter {

    private MainMenuView view;

    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void updateView() {
    }

    private void addEventHandlers() {
        view.getBtnQuit().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(view.getScene().getWindow());
                alert.setTitle("Exit");
                alert.setHeaderText("Are you sure you want to exit?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    System.exit(0);
                } else {
                    alert.close();
                }
            }
        });

        view.getBtnRules().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RulesView rulesView = new RulesView();
                RulesPresenter presenter = new RulesPresenter(rulesView);
                Stage rulesStage = new Stage();
                rulesStage.initOwner(view.getScene().getWindow());
                rulesStage.initModality(Modality.APPLICATION_MODAL);
                rulesStage.setScene(new Scene(rulesView));
                rulesStage.showAndWait();
            }
        });

        view.getBtnStartNew().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GameSetup model = new GameSetup();
                SetupView setupView = new SetupView();
                SetupPresenter setupPresenter = new SetupPresenter(setupView, model);
                view.getScene().setRoot(setupView);
            }
        });

        view.getBtnSettings().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SettingsView settingsView = new SettingsView();
                SettingsPresenter presenter = new SettingsPresenter(settingsView, view);
                view.getScene().setRoot(settingsView);
            }
        });

        view.getBtnPlay().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                fileChooser.setTitle("Choose save");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                Stage openStage = new Stage();
                File file = fileChooser.showOpenDialog(openStage);

                if (file != null) {
                    Game game;
                    try {
                        game = GameFileManager.load(file.getAbsolutePath());
                        GameView gameView = new GameView();
                        GamePresenter gamePresenter = new GamePresenter(gameView, game);
                        view.getScene().setRoot(gameView);
                        gameView.getScene().getWindow().sizeToScene();
                    } catch (StrategoException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(view.getScene().getWindow());
                        alert.setTitle("Error");
                        alert.setHeaderText("Load error");
                        alert.setContentText("An issue occurred loading the file");
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.OK) {
                            alert.close();
                        }
                    }
                }
            }
        });
    }

    public Parent getView() {
        return view;
    }
}
