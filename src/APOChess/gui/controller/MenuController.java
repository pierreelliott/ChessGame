package APOChess.gui.controller;

import APOChess.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
        this.main.newGameMore(true, null);
    }

    /**
     * New multi player game button pressed
     * @param event
     */
    @FXML
    private void newMultiGame(ActionEvent event) {
        this.main.newGameMore(false, null);
    }

    /**
     * Load game button pressed
     * @param event
     */
    @FXML
    private void loadGame(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Game FileGame");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            this.main.loadGame(selectedFile);
        }

    }

}
