package APOChess.gui.controller;

import APOChess.Main;
import APOChess.gui.custom.CustomCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class GameController extends MainController {

    @FXML
    private GridPane gridID;

    @FXML
    private AnchorPane anchorID;

    private CustomCell[][] customCells;

    public GameController(Main main) {
        super(main);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        customCells = new CustomCell[8][8];
        int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle();
                Color color;
                if ((row + col) % 2 == 0) color = Color.rgb(255, 206, 158);
                else color = Color.rgb(205, 133, 63);
                square.setFill(color);

                ImageView imgv = new ImageView(new Image(getClass()
                        .getResourceAsStream("../../res/" + main.getChessboard().getBoard()[col][row].getPiece().getImage())));

                gridID.add(square, col, row);
                gridID.add(imgv, col, row);
                square.widthProperty().bind(gridID.widthProperty().divide(size));
                square.heightProperty().bind(gridID.heightProperty().divide(size));

                imgv.fitWidthProperty().bind(gridID.widthProperty().divide(size));
                imgv.fitHeightProperty().bind(gridID.heightProperty().divide(size));

                int finalCol = col;
                int finalRow = row;
                imgv.setOnMouseEntered(event -> cellEntered(finalCol, finalRow));
                square.setOnMouseEntered(event -> cellEntered(finalCol, finalRow));
                imgv.setOnMouseClicked(event -> cellClicked(finalCol, finalRow));
                square.setOnMouseClicked(event -> cellClicked(finalCol, finalRow));
                imgv.setOnMouseExited(event -> cellExited(finalCol, finalRow));
                square.setOnMouseExited(event -> cellExited(finalCol, finalRow));

                customCells[col][row] = new CustomCell(col, row, square, color, imgv);
            }
        }

        anchorID.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> resizeGrid());
        anchorID.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> resizeGrid());

    }

    /**
     * Auto resize grid with ratio
     */
    public void resizeGrid(){
        if(anchorID.getWidth() > anchorID.getHeight()){
            AnchorPane.setLeftAnchor(gridID, (anchorID.getWidth() - anchorID.getHeight())/2);
            AnchorPane.setRightAnchor(gridID, (anchorID.getWidth() - anchorID.getHeight())/2);
        } else {
            AnchorPane.setTopAnchor(gridID, (anchorID.getHeight() - anchorID.getWidth())/2);
            AnchorPane.setBottomAnchor(gridID, (anchorID.getHeight() - anchorID.getWidth())/2);
        }
    }

    // TODO cellClicked
    public void cellClicked(int col, int row){
        customCells[col][row].setColor(Color.BLACK);
        main.logger.log(Level.INFO, "Cell ["+col+";"+row+"] clicked.");
    }

    // TODO cellEntered
    public void cellEntered(int col, int row) {
        main.logger.log(Level.INFO, "Cell ["+col+";"+row+"] entered.");
        customCells[col][row].setColor(Color.rgb(200,200,200));
    }

    //TODO cellExited
    public void cellExited(int col, int row){
        main.logger.log(Level.INFO, "Cell ["+col+";"+row+"] exited.");
        customCells[col][row].applyDefaultColor();
    }

    /**
     * Show menu window.
     * @param event
     */
    @FXML
    public void showMenu(ActionEvent event){
        main.showMenu();
    }

}
