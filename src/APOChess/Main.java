package APOChess;

import APOChess.core.Game.Chessboard;
import APOChess.gui.controller.GameController;
import APOChess.gui.controller.MenuController;
import APOChess.gui.controller.PromoController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    /**
     * Logger for debugging and log
     */
    public Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Menu window config
     */
    private Stage menu;

    /**
     * Game window config
     */
    private Stage game;

    /**
     * Initalize first window
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("gui/fx/MainFX.fxml"));
        primaryStage.setTitle("APO Chess");
        fxmlLoader.setController(new MenuController(this));
        primaryStage.setScene(new Scene(fxmlLoader.load(), 600, 600));
        primaryStage.show();
        menu = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Show Menu Window
     */
    public void showMenu(){
        menu.show();
        game.hide();
    }

    public void loadGame(File file){
        newGameMore(false, file); //FIXME a vérifier pour le boolean
    }

    /**
     * Show New Game windows
     * @param solo
     */
    public void newGame(boolean solo){
        newGameMore(solo, null);
    }

    /**
     * Show New Game windows
     * @param solo
     */
    public void newGameMore(boolean solo, File file){
        menu.hide();

        GridPane root = new GridPane();
        final int size = 8;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("gui/fx/GameFX.fxml"));

            fxmlLoader.setController(new GameController(this, file));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            game = new Stage();
            game.setTitle("APO Chess APOChess.core.Game");
            game.setScene(scene);
            game.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    /**
     * Show About Window
     */
    public void showAbout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("gui/fx/AboutFX.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 275);
            Stage stage = new Stage();
            stage.setTitle("APO Chess About");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public Stage getStageMenu(){
        return menu;
    }
}
