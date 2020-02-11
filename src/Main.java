import game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import setupView.SetupPresenter;
import setupView.SetupView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game model = new Game();
        SetupView view = new SetupView();
        SetupPresenter presenter = new SetupPresenter(view, model);
        Scene scene = new Scene(presenter.getView());
        stage.setTitle("Stratego");
        stage.setScene(scene);
        stage.show();
    }
}