package APOChess.gui.controller;

import APOChess.Main;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.gui.custom.CustomCell;
import javafx.fxml.FXML;
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

public class PromoController extends MainController {

    /**
     *  Grid name for javafx
     */
    @FXML
    private GridPane gridID;

    /**
     * AnchorPane name for javafx
     */
    @FXML
    private AnchorPane anchorID;

    /**
     * GameController for interacting when promoting
     */
    private GameController gameController;

    /**
     * Color of pieces to show
     */
    private ColorEnum colorEnum;

    /**
     * Constructor
     * @param main Main
     * @param gameController GameController for interacting when promoting
     * @param colorEnum ColorEnum Color of pieces to show
     */
    public PromoController(Main main, GameController gameController, ColorEnum colorEnum) {
        super(main);
        this.gameController = gameController;
        gameController.disableGrid();
        gameController.setWaitingPromotion(true);
        this.colorEnum = colorEnum;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ArrayList<TypeEnum> aTypeEnum = new ArrayList<>();
        aTypeEnum.add(TypeEnum.QUEEN);
        aTypeEnum.add(TypeEnum.ROOK);
        aTypeEnum.add(TypeEnum.BISHOP);
        aTypeEnum.add(TypeEnum.KNIGHT);
        int i = 0;
        for (TypeEnum te : aTypeEnum) {
            // Creation of square for the background of the cells
            Rectangle square = new Rectangle();
            square.setFill(Color.rgb(244, 244, 244));

            ImageView imgv = new ImageView(new Image(getClass()
                    .getResourceAsStream("../../res/" + colorEnum.toString() + te.toString() + ".png")));

            gridID.add(square, i, 0);
            gridID.add(imgv, i, 0);

            square.widthProperty().bind(gridID.widthProperty().divide(4));
            square.heightProperty().bind(gridID.heightProperty().divide(1));
            imgv.fitWidthProperty().bind(gridID.widthProperty().divide(4));
            imgv.fitHeightProperty().bind(gridID.heightProperty().divide(1));

            imgv.setOnMouseEntered(event -> cellEntered(square));
            square.setOnMouseEntered(event -> cellEntered(square));
            imgv.setOnMouseClicked(event -> cellClicked(te, colorEnum));
            square.setOnMouseClicked(event -> cellClicked(te, colorEnum));
            imgv.setOnMouseExited(event -> cellExited(square));
            square.setOnMouseExited(event -> cellExited(square));

            i++;
        }

        // Registering listener for window resizing
        anchorID.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> resizeGrid());
        anchorID.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> resizeGrid());
    }

    /**
     * Keep ratio of the grid when resizing
     */
    private void resizeGrid() {
        double padding  = (anchorID.getHeight() - anchorID.getWidth() /4)/2;
        if(padding > 0D){
            AnchorPane.setTopAnchor(gridID, padding);
            AnchorPane.setBottomAnchor(gridID, padding);
            AnchorPane.setRightAnchor(gridID, 0D);
            AnchorPane.setLeftAnchor(gridID, 0D);
        } else {
            AnchorPane.setRightAnchor(gridID, (anchorID.getWidth() - 4 * anchorID.getHeight())/2);
            AnchorPane.setLeftAnchor(gridID, (anchorID.getWidth() - 4 * anchorID.getHeight())/2);
            AnchorPane.setTopAnchor(gridID, 0D);
            AnchorPane.setBottomAnchor(gridID, 0D);
        }
    }

    /**
     * When a cell is clicked
     * @param typeEnum TypeEnum Type of the piece clicked
     * @param colorEnum ColorEnum Color of the piece clicked
     */
    private void cellClicked(TypeEnum typeEnum, ColorEnum colorEnum) {
        gameController.promote(typeEnum, colorEnum);
        gameController.setWaitingPromotion(false);
        gameController.enableGrid();
        ((Stage) gridID.getScene().getWindow()).close();
        main.logger.log(Level.INFO, "Cell ["+typeEnum.toString()+"] clicked.");
    }

    /**
     * When mouse exited a cell
     * @param square Rectangle
     */
    private void cellExited(Rectangle square) {
        square.setFill(Color.rgb(244, 244, 244));
    }

    /**
     * When mouse entered a cell
     * @param square Rectangle
     */
    private void cellEntered(Rectangle square) {
        square.setFill(Color.rgb(110, 194, 116));
    }
}
