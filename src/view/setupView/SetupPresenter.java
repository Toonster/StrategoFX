package view.setupView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.common.Position;
import model.game.Game;
import model.game.GameSetup;
import model.unit.Unit;
import model.unit.UnitColor;
import view.customListCell.CustomListCell;
import view.gameView.GamePresenter;
import view.gameView.GameView;
import view.rulesView.RulesPresenter;
import view.rulesView.RulesView;
import view.settingsView.SettingsPresenter;
import view.settingsView.SettingsView;

import java.util.Optional;


public class SetupPresenter {

    private SetupView view;
    private GameSetup model;

    public SetupPresenter(SetupView view, GameSetup model) {
        this.view = view;
        this.model = model;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        for (Node btn : view.getBoard().getChildren()) {
            if (GridPane.getRowIndex(btn) > 5) {
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int x = GridPane.getColumnIndex(btn);
                        int y = GridPane.getRowIndex(btn);
                        Unit unitToPlace = view.getListOfUnplacedUnits().getSelectionModel().getSelectedItem();
                        model.setUnitPosition(unitToPlace, new Position(x, y));
                        try {
                            String imagePath = (model.getPlacedUnit().getColor() + "_" + model.getPlacedUnit().getRank()).toLowerCase() + ".png";
                            ((Button) btn).setGraphic(new ImageView(new Image(imagePath, 50, 50, false, false)));
                        } catch (Exception ignored) {

                        }
                        updateView();
                    }
                });
            }
        }
        Button standardConfigButton = view.getStandardConfigBtn();
        standardConfigButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(view.getScene().getWindow());
                alert.setTitle("Overwrite confirmation");
                alert.setHeaderText("This setup has been used by the winner of many Stratego tournaments.\n Your units " +
                        "will be overwritten and you will not be able to alter them any more.");
                alert.setContentText("Are you sure you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    model.usePlayerUnitPreset();
                    view.getBoard().getChildren().forEach(node -> node.setDisable(true));
                    view.getBoard().getChildren().forEach(node -> ((Button) node).setOpacity(1));
                }
                updateBoard();
                updateView();
            }
        });
        Button continueBtn = view.getContinueBtn();
        continueBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (model.isSetupDone()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(view.getScene().getWindow());
                    alert.setTitle("Continue confirmation");
                    alert.setHeaderText("The game will start.");
                    alert.setContentText("Are you sure you want to continue?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Game game = new Game(model.getUnitStartingPositions());
                        GameView gameView = new GameView();
                        GamePresenter gamePresenter = new GamePresenter(gameView, game);
                        view.getScene().setRoot(gameView);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(view.getScene().getWindow());
                    alert.setTitle("Error");
                    alert.setHeaderText("Not complete");
                    alert.setContentText("Please complete the setup phase before continuing");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {
                        alert.close();
                    }
                }
            }
        });

        view.getExitBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.initOwner(view.getScene().getWindow());
                alert.setTitle("Exit");
                alert.setHeaderText("Are you sure you want to exit?");
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    System.exit(0);
                } else if (alert.getResult() == ButtonType.NO) {
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

        view.getBtnSettings().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SettingsView settingsView = new SettingsView();
                SettingsPresenter presenter = new SettingsPresenter(settingsView, view);
                view.getScene().setRoot(settingsView);
            }
        });
    }

    public void updateView() {
        ListView<Unit> units = view.getListOfUnplacedUnits();
        ObservableList<Unit> obsList = FXCollections.observableArrayList(model.getUnplacedUnits());
        units.setItems(obsList);
        units.setCellFactory(param -> new CustomListCell());
    }

    public void updateBoard() {
        for (Node btn : view.getBoard().getChildren()) {
            int x = GridPane.getColumnIndex(btn);
            int y = GridPane.getRowIndex(btn);
            Position position = new Position(x, y);
            Unit selectedUnit = model.getUnitAtPosition(position);
            if (selectedUnit == null) {
                ((Button) btn).setGraphic(null);
            } else if (selectedUnit.isColor(UnitColor.BLUE)) {
                String imagePath = (selectedUnit.getColor() + "_" + selectedUnit.getRank()).toLowerCase() + ".png";
                ((Button) btn).setGraphic(new ImageView(new Image(imagePath, 40, 40, false, false)));
            }
        }
    }

    public SetupView getView() {
        return view;
    }
}
