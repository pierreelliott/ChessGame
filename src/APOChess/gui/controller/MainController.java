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

    protected Main main;

    public MainController(Main main) {
        this.main = main;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    public void menuQuit(){
        main.logger.log(Level.INFO, "Quit menu triggered");
        Platform.exit();
    }

    public void menuAbout(){
        main.logger.log(Level.INFO, "About menu triggered");
        main.showAbout();
    }
}
