package APOChess.gui.controller;

import APOChess.Main;
import APOChess.core.Game.Game;
import APOChess.core.Game.Position;
import APOChess.core.Pieces.PieceEmpty;
import APOChess.gui.custom.CustomCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class GameController extends MainController {

    /**
     * Size of the grid
     */
    private static final int SIZE_GRID = 8;

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
     * Array of CustomCell to manage gui display and references
     */
    private CustomCell[][] customCells;

    /**
     * <em>true</em> when the player selected a Piece.
     */
    private boolean clicked = false;

    /**
     * CustomCell associated to the selected Piece on gui.
     */
    private CustomCell lastClick;

    private Game game;

    public GameController(Main main) {
        super(main);
        this.game = new Game(main);
    }

    /**
     * Initialization of the gui
     * @param url
     * @param resourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        customCells = new CustomCell[SIZE_GRID][SIZE_GRID];
        for (int row = 0; row < SIZE_GRID; row++) {
            for (int col = 0; col < SIZE_GRID; col++) {
                // Creation of square for the background of the cells
                Rectangle square = new Rectangle();
                Color color;
                if ((row + col) % 2 == 0) color = Color.rgb(255, 206, 158);
                else color = Color.rgb(205, 133, 63);
                square.setFill(color);

                // Creation of the image associated to the cell
                ImageView imgv = new ImageView(new Image(getClass()
                        .getResourceAsStream("../../res/" + game.getPieceImage(col, row))));

                gridID.add(square, col+1, row+1);
                gridID.add(imgv, col+1, row+1);

                // Resizing to fit parent gui
                square.widthProperty().bind(gridID.widthProperty().divide(SIZE_GRID+2));
                square.heightProperty().bind(gridID.heightProperty().divide(SIZE_GRID+2));

                imgv.fitWidthProperty().bind(gridID.widthProperty().divide(SIZE_GRID+2));
                imgv.fitHeightProperty().bind(gridID.heightProperty().divide(SIZE_GRID+2));

                // Registering functions associated with actions
                int finalCol = col;
                int finalRow = row;
                imgv.setOnMouseEntered(event -> cellEntered(finalCol, finalRow));
                square.setOnMouseEntered(event -> cellEntered(finalCol, finalRow));
                imgv.setOnMouseClicked(event -> cellClicked(finalCol, finalRow));
                square.setOnMouseClicked(event -> cellClicked(finalCol, finalRow));
                imgv.setOnMouseExited(event -> cellExited(finalCol, finalRow));
                square.setOnMouseExited(event -> cellExited(finalCol, finalRow));

                // Backup everything into a CustomCell
                customCells[col][row] = new CustomCell(col, row, square, color, imgv);
            }
        }

        // Registering listener for window resizing
        anchorID.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> resizeGrid());
        anchorID.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> resizeGrid());

    }

    /**
     * Auto resize grid with ratio
     */
    private void resizeGrid(){
        if(anchorID.getWidth() > anchorID.getHeight()){
            AnchorPane.setLeftAnchor(gridID, (anchorID.getWidth() - anchorID.getHeight())/2);
            AnchorPane.setRightAnchor(gridID, (anchorID.getWidth() - anchorID.getHeight())/2);
        } else {
            AnchorPane.setTopAnchor(gridID, (anchorID.getHeight() - anchorID.getWidth())/2);
            AnchorPane.setBottomAnchor(gridID, (anchorID.getHeight() - anchorID.getWidth())/2);
        }
    }

    private void cellClicked(int col, int row){
        Color colorSelected = Color.RED;
        if(!game.isPieceSelected()){ // If the player hasn't selected a piece
            if(game.selectPiece(col, row)) {
                ArrayList<Position> positions = game.getMoves(game.getSelectedPiece(),col, row);
                lastClick = customCells[col][row];

                for (Position p : positions ) {
                    customCells[p.getPosX()][p.getPosY()].setColor(colorSelected);
                }
            }

        } else { // If the player has selected a piece
            Color debugColor = customCells[col][row].getColor();
            if(game.canMoveTo(col, row)){ // Si la case est une position valide //ENG
                // TODO implémenter la gestion de la pièce enlevé s'il y a écrasement

                String pieceImage = game.getSelectedPiece().getImage(); // Need to get the image before moving anything
                game.movePiece(col,row);

                // On met a jour la nouvelle case graphique //ENG
                customCells[col][row].setImage(pieceImage);

                // On met a jour graphiquement l'ancienne case contenant la pièce, par du vide. //ENG
                customCells[lastClick.getCol()][lastClick.getRow()].setImage(new PieceEmpty().getImage());

                restoreDefaultColor();
            } else { // Si la case n'est pas une position valide //ENG
                game.selectPiece(-1,-1); // We deselect the piece
                restoreDefaultColor();
            }
        }

        main.logger.log(Level.FINE, "Cell ["+col+";"+row+"] clicked.");
    }

    private void cellEntered(int col, int row) { //TODO
        main.logger.log(Level.FINE, "Cell ["+col+";"+row+"] entered.");
        //customCells[col][row].setColor(Color.rgb(200,200,200));
    }

    private void cellExited(int col, int row){ //TODO
        main.logger.log(Level.FINE, "Cell ["+col+";"+row+"] exited.");
        //customCells[col][row].setDefaultColor();
    }

    /**
     * Restore the default background color of the grid.
     */
    private void restoreDefaultColor(){
        for (int row = 0; row < SIZE_GRID; row++)
            for (int col = 0; col < SIZE_GRID; col++)
                customCells[col][row].setDefaultColor();
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
