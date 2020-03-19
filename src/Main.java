import model.common.Position;
import model.game.Game;
import model.game.GameResult;
import model.game.GameSetup;
import model.game.GameStatus;
import view.gameResultView.GameResultPresenter;
import view.gameResultView.GameResultView;
import view.gameView.GamePresenter;
import view.gameView.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.mainMenu.MainMenuPresenter;
import view.mainMenu.MainMenuView;
import view.mediaplayer.MediaPlayer;
import view.setupView.SetupPresenter;
import view.setupView.SetupView;
import model.unit.Rank;
import model.unit.Unit;
import model.unit.UnitColor;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        Map<Position, Unit> initialUnitPositions = new HashMap<>();
        initialUnitPositions.put(new Position(7,6), new Unit(Rank.General, UnitColor.BLUE));
        initialUnitPositions.put(new Position(6,6), new Unit(Rank.Flag, UnitColor.RED));


        SetupView setupView = new SetupView();
        GameSetup setup = new GameSetup();
        SetupPresenter presenter2 = new SetupPresenter(setupView, setup);
        GameView view = new GameView();
        Game model = new Game(initialUnitPositions);
        GamePresenter presenter = new GamePresenter(view, model);
        MainMenuView mainMenuView = new MainMenuView();
        MainMenuPresenter mainMenuPresenter = new MainMenuPresenter(mainMenuView);
        /*MediaPlayer.playMusic();*/
        GameResult gameResult = new GameResult(GameStatus.BLUE_NO_MOVES);
        GameResultView gameResultView = new GameResultView();
        GameResultPresenter gameResultPresenter = new GameResultPresenter(gameResult, gameResultView);

        Scene scene = new Scene(view,1200,750);
        stage.setTitle("Stratego");
        stage.setScene(scene);
        stage.show();

    }
}
