package APOChess.gui.controller;

import APOChess.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    /**
     * Main pointer
     */
    protected Main main;

    public MainController(Main main) {
        this.main = main;
    }

    /**
     * Initialization of the gui
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    /**
     * FileGame>Quit button pressed
     */
    public void menuQuit(){
        main.logger.log(Level.INFO, "Quit menu triggered");
        Platform.exit();
    }

    /**
     * Help>About button pressed
     */
    public void menuAbout(){
        main.logger.log(Level.INFO, "About menu triggered");
        main.showAbout();
    }
}
