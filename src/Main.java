import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.game.GameResult;
import model.game.GameStatus;
import view.gameResultView.GameResultPresenter;
import view.gameResultView.GameResultView;
import view.mainMenu.MainMenuPresenter;
import view.mainMenu.MainMenuView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        MainMenuView mainMenuView = new MainMenuView();
        MainMenuPresenter mainMenuPresenter = new MainMenuPresenter(mainMenuView);
        /*MediaPlayer.playMusic();*/
        GameResult gameResult = new GameResult(GameStatus.BLUE_NO_MOVES);
        GameResultView gameResultView = new GameResultView();
        GameResultPresenter gameResultPresenter = new GameResultPresenter(gameResult, gameResultView);

        Scene scene = new Scene(gameResultView, 1200, 800);
        stage.setTitle("Stratego");
        stage.setScene(scene);
        stage.show();

    }
}
