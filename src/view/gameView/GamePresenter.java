package view.gameView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.common.Position;
import model.exception.StrategoException;
import model.fileManager.GameFileManager;
import model.game.Game;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.unit.Unit;
import model.unit.UnitColor;
import view.customListCell.CustomListCell;

import java.io.File;
import java.util.List;
import java.util.Optional;

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
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    int x = GridPane.getColumnIndex(btn);
                    int y = GridPane.getRowIndex(btn);
                    Position position = new Position(x, y);
                    if (model.isUnitSelected()) {
                        try {
                            if (model.processMove(position) && !model.isGameOver()) {
                                model.computerMove();
                            }
                        } catch (StrategoException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        try {
                            model.selectUnit(position);
                        } catch (StrategoException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    try {
                        model.unSelectUnit();
                    } catch (StrategoException e) {
                        System.out.println(e.getMessage());
                    }
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
                        e.printStackTrace();
                    }
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
                    ((Button) btn).setGraphic(new ImageView(new Image("red_back.png", 50, 50, false, false)));
                } else {
                    String imagePath = (selectedUnit.getColor() + "_" + selectedUnit.getRank()).toLowerCase() + ".png";
                    ((Button) btn).setGraphic(new ImageView(new Image(imagePath, 50, 50, false, false)));
                }
            }
        }
        ListView<Unit> redCapturedUnits = view.getRedCapturedUnits();
        ObservableList<Unit> obsListRed = FXCollections.observableArrayList(model.getCapturedUnits(UnitColor.RED));
        redCapturedUnits.setItems(obsListRed);
        redCapturedUnits.setCellFactory(param -> new CustomListCell());

        ListView<Unit> blueCapturedUnits = view.getBlueCapturedUnits();
        ObservableList<Unit> obsListBlue = FXCollections.observableArrayList(model.getCapturedUnits(UnitColor.BLUE));
        blueCapturedUnits.setItems(obsListBlue);
        blueCapturedUnits.setCellFactory(param -> new CustomListCell());
    }
}
