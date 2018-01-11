package APOChess.gui.controller;

import APOChess.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Logger;

public class MenuController extends MainController {

    public MenuController(Main main) {
        super(main);
    }

    @FXML
    private void newSoloGame(ActionEvent event) {
        this.main.newGame(true);
    }

    @FXML
    private void newMultiGame(ActionEvent event) {
        this.main.newGame(false);
    }

    @FXML
    private void loadGame(ActionEvent event) {
        this.main.loadGame();
    }

}
