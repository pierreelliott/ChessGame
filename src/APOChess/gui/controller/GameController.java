package APOChess.gui.controller;

import APOChess.Main;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Game;
import APOChess.core.Game.Position;
import APOChess.core.Pieces.PieceBishop;
import APOChess.core.Pieces.PieceKnight;
import APOChess.core.Pieces.PieceQueen;
import APOChess.core.Pieces.PieceRook;
import APOChess.gui.custom.CustomCell;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
     * CustomCell associated to the selected Piece on gui.
     */
    private CustomCell lastClick;

    /**
     * Game
     */
    private Game game;

    /**
     * <em>true</em> when a promotion action is waiting
     */
    private boolean waitingPromotion = false;

    /**
     * Position of the piece to promote
     */
    private Position posToPromote = null;

    private boolean needIA;

    /**
     * Default constructor
     * @param main Main
     */
    public GameController(Main main, boolean needIA) {
        this(main, needIA, null);
    }

    /**
     * File loading constructor
     * @param main Main
     * @param needIA Boolean
     * @param file File to load
     */
    public GameController(Main main, boolean needIA, File file) {
        super(main);
        this.game = new Game(main, file);
        this.needIA = needIA;
    }

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
                imgv.setOnMouseClicked(event -> cellClicked(finalCol, finalRow));
                square.setOnMouseClicked(event -> cellClicked(finalCol, finalRow));

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

    /**
     * When a cell is clicked
     * @param col int
     * @param row int
     */
    private void cellClicked(int col, int row){
        main.logger.log(Level.FINE, "Cell ["+col+";"+row+"] clicked.");
        if(waitingPromotion || game.isFinished())
            return;

        if(!game.isPieceSelected()){ // First selection
            if(game.selectPiece(col, row)) {
                ArrayList<Position> positionsStandard = game.getStandardMoves(col, row);
                ArrayList<Position> positionsSpecial = game.getSpecialMoves(col, row);
                lastClick = customCells[col][row];

                for (Position p : positionsStandard ) {
                    customCells[p.getPosX()][p.getPosY()].setColor(Color.RED);
                }
                for (Position p : positionsSpecial ) {
                    customCells[p.getPosX()][p.getPosY()].setColor(Color.YELLOW);
                }
            }

        } else { // A piece has been already selected
            if(game.canMoveTo(col, row)){ // Can move to that position

                ArrayList<Position> positionsToRefresh = game.processSpecialMoves(col,row);
                for (Position p : positionsToRefresh) {
                    customCells[p.getPosX()][p.getPosY()].setImage(game.getPieceImage(p));
                }
                if(game.isNeedPromote()){
                    posToPromote = new Position(col,row);
                    try { // Loading Promote gui
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("../fx/PromoFX.fxml"));

                        fxmlLoader.setController(
                                new PromoController(main, this, game.getSelectedPiece().getColor())
                        );
                        Scene scene = new Scene(fxmlLoader.load(), 600, 200);
                        Stage stage = new Stage();
                        stage.setOnCloseRequest(Event::consume);
                        stage.setTitle("APO Chess Promotion");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        main.logger.log(Level.SEVERE, "Failed to create new Promote Window.", e);
                    }
                    game.setNeedPromote(false);
                }

                // Processing if it is the end of the game.
                if(game.isKing(col, row))
                    game.setFinished(true);

                if(game.isFinished())
                    showEndWindow(game.getSelectedPiece().getColor());

                // Need to get the image before moving anything
                String pieceImage = game.getSelectedPiece().getImage();
                game.movePiece(col,row);

                // Update image of the cell
                customCells[col][row].setImage(pieceImage);

                // Update previous cell with empty piece
                customCells[lastClick.getCol()][lastClick.getRow()]
                        .setImage(game.getPieceImage(lastClick.getCol(), lastClick.getRow()));

                if(needIA && !game.isFinished()){
                    ArrayList<Position> positions = game.IA();
                    for (Position p : positions) {
                        customCells[p.getPosX()][p.getPosY()].setImage(game.getPieceImage(p));
                    }
                }

            } else { // The cell is not a valid move
                game.selectPiece(-1,-1); // We deselect the piece
            }
            restoreDefaultColor();
        }
    }

    /**
     * Restore the default background color of the grid.
     */
    private void restoreDefaultColor(){
        for (int row = 0; row < SIZE_GRID; row++)
            for (int col = 0; col < SIZE_GRID; col++)
                customCells[col][row].applyDefaultColor();
    }

    /**
     * Show menu window.
     * @param event ActionEvent
     */
    @FXML
    public void showMenu(ActionEvent event){
        main.showMenu();
    }

    /**
     * Show the finished game window
     * @param colorEnum ColorEnum Color of the winner
     */
    private void showEndWindow(ColorEnum colorEnum){
        try { // Loading End gui
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../fx/EndFX.fxml"));

            fxmlLoader.setController(
                    new EndController(main, colorEnum)
            );
            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setOnCloseRequest(Event::consume);
            stage.setTitle("APO Chess Game Finished");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            main.logger.log(Level.SEVERE, "Failed to create new Promote Window.", e);
        }
    }

    /**
     * Waiting promotion setter
     * Disable clicking on cell
     * @param waitingPromotion boolean
     */
    public void setWaitingPromotion(boolean waitingPromotion) {
        this.waitingPromotion = waitingPromotion;
    }

    /**
     * Disable the grid on the gui
     */
    public void disableGrid(){
        gridID.setDisable(true);
    }

    /**
     * Enable the grid on the gui
     */
    public void enableGrid(){
        gridID.setDisable(false);
    }

    /**
     * Process promoting
     * Called by PromoController
     * @param typeEnum TypeEnum Type chosen
     * @param colorEnum ColorEnum Color of the piece
     * @see PromoController
     */
    public void promote(TypeEnum typeEnum, ColorEnum colorEnum){
        if(posToPromote == null){
            main.logger.log(Level.SEVERE, "Promote error pos");
            return;
        }
        game.promote(posToPromote, typeEnum, colorEnum);
        customCells[posToPromote.getPosX()][posToPromote.getPosY()]
                .setImage(game.getPieceImage(posToPromote));
        posToPromote = null;
    }
}
