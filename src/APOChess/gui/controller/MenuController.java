package APOChess.gui.controller;

import APOChess.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.logging.Logger;

public class MenuController extends MainController {

    public MenuController(Main main) {
        super(main);
    }

    /**
     * New solo game button pressed
     * @param event
     */
    @FXML
    private void newSoloGame(ActionEvent event) {
        this.main.newGame(true);
    }

    /**
     * New multi player game button pressed
     * @param event
     */
    @FXML
    private void newMultiGame(ActionEvent event) {
        this.main.newGame(false);
    }

    /**
     * Load game button pressed
     * @param event
     */
    @FXML
    private void loadGame(ActionEvent event) {
        this.main.loadGame();
    }

}
