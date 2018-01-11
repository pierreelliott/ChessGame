package APOChess;

import APOChess.core.Game.Chessboard;
import APOChess.gui.controller.GameController;
import APOChess.gui.controller.MenuController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public Logger logger = Logger.getLogger(getClass().getName());
    private Stage menu;
    private Stage game;
    private Chessboard chessboard;

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

    public void showMenu(){
        menu.show();
        game.hide();
    }

    public void loadGame(){

    }

    public void newGame(boolean solo){
        menu.hide();

        chessboard = new Chessboard();
        chessboard.initialize();

        GridPane root = new GridPane();
        final int size = 8;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("gui/fx/GameFX.fxml"));
        /*
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
         */
            fxmlLoader.setController(new GameController(this));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

//            scene.widthProperty().addListener(new ChangeListener<Number>() {
//                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//                    System.out.println("Width: " + newSceneWidth);
//
//                }
//            });
//            scene.heightProperty().addListener(new ChangeListener<Number>() {
//                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                    System.out.println("Height: " + newSceneHeight);
//                }
//            });


            game = new Stage();
            game.setTitle("APO Chess APOChess.core.Game");
            game.setScene(scene);
            game.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void showAbout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("gui/fx/AboutFX.fxml"));
            //fxmlLoader.setController(new GameController(this));
            Scene scene = new Scene(fxmlLoader.load(), 450, 275);
            Stage stage = new Stage();
            stage.setTitle("APO Chess About");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public Chessboard getChessboard(){
        return chessboard;
    }
}
