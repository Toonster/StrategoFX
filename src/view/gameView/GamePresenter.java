package view.gameView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.common.Position;
import model.exception.StrategoException;
import model.fileManager.GameFileManager;
import model.game.Game;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.game.GameResult;
import model.unit.Unit;
import model.unit.UnitColor;
import view.customListCell.CustomListCell;
import view.gameResultView.GameResultPresenter;
import view.gameResultView.GameResultView;

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
                    view.getLog().setText(ex.getMessage());
                }
                updateView();
            });
        }

        view.getSaveBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                Stage saveStage = new Stage();

                //Show save file dialog
                File file = fileChooser.showSaveDialog(saveStage);

                if (file != null) {
                    try {
                        GameFileManager.save(file.getAbsolutePath(), model);
                    } catch (StrategoException e) {
                        view.getLog().setText(e.getMessage());
                    }
                }
            }
        });

        view.getExitBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.NONE);
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
    }
}
