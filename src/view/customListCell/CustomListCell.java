package view.customListCell;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.unit.Unit;

public class CustomListCell extends ListCell<Unit> {

    private ImageView imageView = new ImageView();

    @Override
    public void updateItem(Unit unit, boolean empty) {
        super.updateItem(unit, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            String imagePath = (unit.getColor() + "_" + unit.getRank()).toLowerCase() + ".png";
            imageView.setImage(new Image(imagePath, 40, 40, false, false));
            setText(unit.getRank().name());
            setGraphic(imageView);
        }
    }
}
