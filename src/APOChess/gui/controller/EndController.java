package APOChess.gui.controller;

import APOChess.Main;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class EndController extends MainController {

    /**
     * The only button on the window.
     */
    @FXML
    private Button exitButton;

    /**
     * The label who explain which player has won.
     */
    @FXML
    private Label labelID;

    /**
     * Color of the winner
     */
    private ColorEnum colorWinner;
    /**
     * Constructor
     * @param main Main
     */
    public EndController(Main main, ColorEnum colorWinner) {
        super(main);
        this.colorWinner = colorWinner;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        labelID.setText("Les " + (colorWinner == ColorEnum.WHITE ? "blancs" : "noirs") + " ont gagné.");
    }

    /**
     * When the close button is clicked
     */
    @FXML
    private void btnClicked() {
        ((Stage) exitButton.getScene().getWindow()).close();
        main.logger.log(Level.INFO, "Btn close clicked.");
    }
}
