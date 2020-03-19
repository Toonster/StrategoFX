package view.gameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.common.Position;
import model.exception.StrategoException;
import model.fileManager.GameFileManager;
import model.game.Game;
import model.game.GameResult;
import model.unit.Unit;
import model.unit.UnitColor;
import view.gameResultView.GameResultPresenter;
import view.gameResultView.GameResultView;
import view.rulesView.RulesPresenter;
import view.rulesView.RulesView;
import view.settingsView.SettingsPresenter;
import view.settingsView.SettingsView;

import java.io.File;
import java.util.List;

public class GamePresenter {

    GameView view;
    Game model;

    public GamePresenter(GameView view, Game model) {
        this.view = view;
        this.model = model;
        addEventHandlers();
        updateView();
    }

    public GameView getView() {
        return view;
    }

    private void addEventHandlers() {
        for (Node btn : view.getBoard().getChildren()) {
            btn.setOnMouseClicked(mouseEvent -> {
                try {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        int x = GridPane.getColumnIndex(btn);
                        int y = GridPane.getRowIndex(btn);
                        Position position = new Position(x, y);
                        if (model.isUnitSelected()) {
                            if (model.processMove(position) && !model.isGameOver()) {
                                model.computerMove();
                            } else if (model.isGameOver()) {
                                GameResult result = new GameResult(model.getStatus());
                                GameResultView view = new GameResultView();
                                GameResultPresenter presenter = new GameResultPresenter(result, view);
                                this.view.getScene().setRoot(view);
                            }
                        } else {
                            model.selectUnit(position);
                        }
                    } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                        model.unSelectUnit();
                    }
                } catch (StrategoException ex) {
                    view.getLog().setText(view.getLog().getText() + "\n" + ex.getMessage());
                }
                updateView();
            });
        }

        view.getSaveBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                Stage saveStage = new Stage();
                File file = fileChooser.showSaveDialog(saveStage);

                if (file != null) {
                    try {
                        GameFileManager.save(file.getAbsolutePath(), model);
                    } catch (StrategoException e) {
                        view.getLog().setText(e.getMessage());
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(view.getScene().getWindow());
                        alert.setTitle("File load error");
                        alert.setHeaderText("Unable to load file");
                    }
                }
            }
        });

        view.getExitBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(view.getScene().getWindow());
                alert.setTitle("Exit");
                alert.setContentText("Unsaved progress will be lost!");
                alert.setHeaderText("Are you sure you want to exit?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    System.exit(0);
                } else {
                    alert.close();
                }
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

        view.getRulesBtn().setOnAction(new EventHandler<ActionEvent>() {
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
    }

    private void updateView() {
        List<Unit> visibleUnits = model.getVisibleUnits();
        for (Node btn : view.getBoard().getChildren()) {
            int x = GridPane.getColumnIndex(btn);
            int y = GridPane.getRowIndex(btn);
            Position position = new Position(x, y);
            Unit selectedUnit = model.getUnitOnTile(position);
            if (selectedUnit == null) {
                ((Button) btn).setGraphic(null);
            } else {
                if (selectedUnit.isColor(UnitColor.RED) && !visibleUnits.contains(selectedUnit)) {
                    ((Button) btn).setGraphic(new ImageView(new Image("red_back.png", 40, 40, false, false)));
                } else {
                    String imagePath = (selectedUnit.getColor() + "_" + selectedUnit.getRank()).toLowerCase() + ".png";
                    ((Button) btn).setGraphic(new ImageView(new Image(imagePath, 40, 40, false, false)));
                }
                /*String imagePath = (selectedUnit.getColor() + "_" + selectedUnit.getRank()).toLowerCase() + ".png";
                ((Button) btn).setGraphic(new ImageView(new Image(imagePath, 40, 40, false, false)));*/
            }
        }

        List<Unit> redCapturedUnits2 = model.getCapturedUnits(UnitColor.RED);
        view.getRedCapturedUnits().getChildren().clear();
        for (Unit unit : redCapturedUnits2) {
            String imagePath = (unit.getColor() + "_" + unit.getRank()).toLowerCase() + ".png";
            ImageView imageView = new ImageView(new Image(imagePath, 50, 50, false, false));
            view.getRedCapturedUnits().getChildren().add(imageView);
        }

        List<Unit> blueCapturedUnits2 = model.getCapturedUnits(UnitColor.BLUE);
        view.getBlueCapturedUnits().getChildren().clear();
        for (Unit unit : blueCapturedUnits2) {
            String imagePath = (unit.getColor() + "_" + unit.getRank()).toLowerCase() + ".png";
            ImageView imageView = new ImageView(new Image(imagePath, 50, 50, false, false));
            view.getBlueCapturedUnits().getChildren().add(imageView);
        }

        if (view.getLog().getText().lines().count() > 6) {
            view.getLog().setText("");
        }

        if (model.getSelectedUnit() != null) {
            Position position = model.getUnitPosition(model.getSelectedUnit());
            view.getSelectedUnitLbl().setText(model.getSelectedUnit().getRank().name() + " (" + (position.getX() + 1) + " , " + (position.getY() + 1) + ")" + "\n(Right-click to unselect)");
        } else {
            view.getSelectedUnitLbl().setText("No unit selected");
        }
    }
}
