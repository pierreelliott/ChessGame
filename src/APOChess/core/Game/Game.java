package APOChess.core.Game;

import APOChess.Main;
import APOChess.core.Action.Action;
import APOChess.core.Action.ActionMove;
import APOChess.core.Action.ActionPromotion;
import APOChess.core.Action.ActionRemove;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.IA.IA;
import APOChess.core.IA.IAMovement;
import APOChess.core.Pieces.*;
import APOChess.gui.controller.PromoController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class Game {
    private ColorEnum playerTurn;
    private boolean pieceSelected;
    private Chessboard board;
    private Position selectedPiecePosition;
    private boolean isFinished = false;
    private IA ia;
    private Main main;
    private Random generator;
    private boolean needPromote = false;

    /**
     * Constructor for a default game
     * @param main Main
     */
    public Game(Main main) {
        this(main, null);
    }

    /**
     * Constructor
     * @param main Main
     * @param file File for loading a board
     */
    public Game(Main main, File file) {
        this.main = main;
        this.pieceSelected = false;
        this.playerTurn = ColorEnum.WHITE;
        this.generator = new Random();

        board = new Chessboard(main);
        ia = new IA(board);

        if(file == null) {
            board.initialize();
        } else {
            board.initialize(file);
        }
    }

    /**
     * Get standard moves for a piece located on a position
     * @param col int
     * @param row int
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getStandardMoves(int col, int row) {
        if (board.isOnGrid(new Position(col, row)) &&
                board.getTile(col, row).getPiece().getColor() == playerTurn) {
            return board.getAvailableMoves(col, row);
        }
        return new ArrayList<>();
    }

    /**
     * Get standard moves for a piece located on a position
     * @param p Position
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getStandardMoves(Position p) {
        return getStandardMoves(p.getPosX(), p.getPosY());
    }

    /**
     * Get special moves for a pice located on a position
     * @param col int
     * @param row int
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getSpecialMoves(int col, int row) {
        if (board.isOnGrid(new Position(col, row)) &&
                board.getTile(col, row).getPiece().getColor() == playerTurn) {
            return board.getSpecialeMoves(col, row);
        }
        return new ArrayList<>();
    }

    /**
     * Get special moves for a piece located on a position
     * @param p Position
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getSpecialMoves(Position p) {
        return getSpecialMoves(p.getPosX(), p.getPosY());
    }

    /**
     * Return possible moves of a piece located on a position
     * @param col int
     * @param row int
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getMoves(int col, int row) {
        ArrayList<Position> positions = new ArrayList<>();
        positions.addAll(getStandardMoves(col,row));
        positions.addAll(getSpecialMoves(col,row));
        return positions;
    }

    /**
     * Return possible moves of a piece located on a position
     * @param p Position
     * @return ArrayList<Position>
     */
    public ArrayList<Position> getMoves(Position p) {
        return getMoves(p.getPosX(), p.getPosY());
    }

    /**
     * Select the piece located at the given column and row.
     * If the location isn't correct, any actual piece is deselected.
     * @param col Piece's location's column
     * @param row Piece's location's row
     * @return <em>True</em> if a piece has been selected (ie, the selection is on the board and it's the player turn)
     */
    public boolean selectPiece(int col, int row) {
        return selectPiece(new Position(col, row));
    }

    /**
     * Select the piece located at the given column and row.
     * If the location isn't correct, any actual piece is deselected.
     * @param position Position
     * @return <em>True</em> if a piece has been selected (ie, the selection is on the board and it's the player turn)
     */
    public boolean selectPiece(Position position) {
        if(board.isOnGrid(position) && board.getTile(position).getPiece().getColor() == playerTurn) {
            selectedPiecePosition = position;
            pieceSelected = true;
            return pieceSelected;
        }
        selectedPiecePosition = null;
        pieceSelected = false;
        return pieceSelected;
    }

    /**
     * Move a piece to the new Position (from col and row parameters)
     * @param col Column of the new piece's Position
     * @param row Row of the new piece's Position
     */
    public void movePiece(int col, int row) {
        movePiece(new Position(col, row));
    }

    /**
     * Move a piece to the new Position (from col and row parameters)
     * @param newPos Position
     */
    public void movePiece(Position newPos) {
        boolean a = board.isOnGrid(selectedPiecePosition);
        boolean b = board.isOnGrid(newPos);
        if(a && b) {
            /* If the two pieces aren't the same color, we can move */
            if( !(board.getTile(selectedPiecePosition).getPiece().getColor() == board.getTile(newPos).getPiece().getColor()) ) {
                board.getTile(selectedPiecePosition).getPiece().move();
                board.getTile(newPos).setPiece(board.getTile(selectedPiecePosition).getPiece());
                board.getTile(selectedPiecePosition).resetPiece();

                playerTurn = ColorEnum.getOpposite(playerTurn);
            }
            // No matter if the piece hasn't moved, it is deselected
            pieceSelected = false;
        }
    }

    /**
     * Move a piece from a position to another
     * @param posStart Position from
     * @param posEnd Position to
     */
    public void moveOtherPiece(Position posStart, Position posEnd){
        if(board.isOnGrid(posStart) && board.isOnGrid(posEnd)){
            board.getTile(posEnd).setPiece(board.getTile(posStart).getPiece());
            board.getTile(posStart).resetPiece();
        }
    }

    /**
     * <em>true</em> when the piece can move to a position
     * @param col int
     * @param row int
     * @return boolean
     */
    public boolean canMoveTo(int col, int row) {
        Position newPos = new Position(col,row);
        if(pieceSelected && board.isOnGrid(newPos)) {
            ArrayList<Position> StandardPositions = getStandardMoves(selectedPiecePosition);
            ArrayList<Position> SpecialPositions = getSpecialMoves(selectedPiecePosition);
            if (StandardPositions.contains(newPos) || SpecialPositions.contains(newPos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a piece on the board
     * @param col int
     * @param row int
     */
    public void removePiece(int col, int row){
        removePiece(new Position(col, row));
    }

    /**
     * Remove a piece on the board
     * @param positionPiece Position
     */
    public void removePiece(Position positionPiece){
        if(board.isOnGrid(positionPiece)){
            board.getTile(positionPiece).resetPiece();
        }
    }

    /**
     * <em>true</em> if the position includes the ennemy king
     * @param col int
     * @param row int
     * @return boolean
     */
    public boolean isKing(int col, int row){
        return isKing(new Position(col, row));
    }

    /**
     * <em>true</em> if the position includes the ennemy king
     * @param position Position
     * @return boolean
     */
    public boolean isKing(Position position){
        if(pieceSelected && board.isOnGrid(position)) {
            return board.getTile(position).getPiece().getType() == TypeEnum.KING &&
                    getSelectedPiece().getColor() != board.getTile(position).getPiece().getColor();
        }
        return false;
    }

    /**
     * <em>true</em> when the position is a special move
     * @param col int
     * @param row int
     * @return boolean
     */
    public boolean isSpecialMove(int col, int row) {
        return isSpecialMove(new Position(col, row));
    }

    /**
     * <em>true</em> when the position is a special move
     * @param newPos Position
     * @return boolean
     */
    public boolean isSpecialMove(Position newPos) {
        if(pieceSelected && board.isOnGrid(newPos)) {
            if (getSpecialMoves(selectedPiecePosition).contains(newPos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return a list of Action associated of the piece on a location
     * @param col int
     * @param row int
     * @return ArrayList<Action>
     */
    public ArrayList<Action> getActions(int col, int row){
        return getActions(new Position(col, row));
    }

    /**
     * Return a list of Action associated of the piece on a location
     * @param position Position
     * @return ArrayList<Action>
     */
    public ArrayList<Action> getActions(Position position){
        return board.getTile(selectedPiecePosition)
                .getPiece().getActions(selectedPiecePosition, position);
    }



    /**
     * <em>True</em> if a piece is selected
     * @return True if a piece is selected, False otherwise
     */
    public boolean isPieceSelected() {
        return pieceSelected;
    }

    /**
     * Get the currently selected piece
     * @return Piece or null if no pieces are selected
     */
    public Piece getSelectedPiece() {
        if(selectedPiecePosition != null) {
            return board.getTile(selectedPiecePosition).getPiece();
        }
        return null;
    }

    /**
     * Get the position of the currently selected piece
     * @return Position
     */
    public Position getSelectedPiecePosition() {
        return selectedPiecePosition;
    }

    /**
     * Get image  of a piece.
     * @param col int
     * @param row int
     * @return string
     */
    public String getPieceImage(int col, int row) {
        return getPieceImage(new Position(col, row));
    }

    /**
     * Get image of a piece.
     * @param pos Position
     * @return string
     */
    public String getPieceImage(Position pos) {
        if(board.isOnGrid(pos))
            return board.getTile(pos).getPiece().getImage();
        return "";
    }

    /**
     * Set a piece at a position
     * @param pos Position
     * @param piece Piece
     */
    public void setPiece(Position pos, Piece piece){
        board.getTile(pos).setPiece(piece);
    }

    /**
     * <em>true</em> when game is finished
     * @return boolean
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Set finished status of the game
     * @param finished boolean
     */
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * Process a special movement.
     * Return a list of Position to refresh on the gui.
     * @param col int
     * @param row int
     * @return ArrayList<Position> List of Position to refresh on the gui
     */
    public ArrayList<Position> processSpecialMoves(int col, int row) {
        ArrayList<Position> refreshPos = new ArrayList<>();
        if(isSpecialMove(col,row)){ // Is a special move
            main.logger.log(Level.INFO, "Cell ["+col+";"+row+"] is a special move !.");
            ArrayList<Action> actions = getActions(col,row); // Knowing what to do
            for (Action action : actions) {
                if(action instanceof ActionMove){ // Moving another piece on the board
                    Position posStart = ((ActionMove) action).getPosStart();
                    Position posEnd = ((ActionMove) action).getPosEnd();
                    moveOtherPiece(posStart, posEnd);
                    refreshPos.add(posStart);
                    refreshPos.add(posEnd);
                } else if (action instanceof ActionRemove){ // Removing a piece on the board
                    Position posRemove = ((ActionRemove) action).getPos();

                    if(isKing(posRemove))
                        setFinished(true);

                    removePiece(posRemove);
                    refreshPos.add(posRemove);
                    main.logger.log(Level.WARNING, "Cell "+posRemove.toString()+" is removed !.");
                } else if(action instanceof ActionPromotion){ // Promoting the piece
                    needPromote = true;
                }
            }
        }
        return refreshPos;
    }

    /**
     * Get isNeedPromote
     * @return boolean
     */
    public boolean isNeedPromote() {
        return needPromote;
    }

    /**
     * Set needPromote
     * @param needPromote boolean
     */
    public void setNeedPromote(boolean needPromote) {
        this.needPromote = needPromote;
    }

    /**
     * Processing promotion
     * @param posToPromote Position of the piece to promote
     * @param typeEnum TypeEnum of the new piece
     * @param colorEnum ColorEnum of the new piece
     */
    public void promote(Position posToPromote, TypeEnum typeEnum, ColorEnum colorEnum){
        switch (typeEnum){
            case BISHOP:
                setPiece(posToPromote, new PieceBishop(colorEnum));
                break;
            case KNIGHT:
                setPiece(posToPromote, new PieceKnight(colorEnum));
                break;
            case QUEEN:
                setPiece(posToPromote, new PieceQueen(colorEnum));
                break;
            case ROOK:
                setPiece(posToPromote, new PieceRook(colorEnum));
                break;
            default:{
                main.logger.log(Level.SEVERE, "Promote error type");
            }
        }
    }

    //////////////////////////////////////////////////
    //                   IA                         //
    //////////////////////////////////////////////////

    /**
     * Process IA playing.
     * @return ArrayList<Position> List of cell to refresh
     */
    public ArrayList<Position> IA(){
        ArrayList<Position> positions = new ArrayList<>();
        IAMovement iaMovement;
        playerTurn = ColorEnum.BLACK;

        // Select a good piece
        do {
            ia.processMovementsList();
            if(!ia.isMovesPossible()){
                main.logger.log(Level.SEVERE, "IA no possible moves !!!");
                return positions;
            }
            iaMovement = ia.selectIAMovement();
        } while(!selectPiece(iaMovement.getPosition()));

        // Pick a random future position for that piece.
        Position destPosition = iaMovement.getRandomMove();

        // It totaly should be on the grid, but better preventing errors
        if(!board.isOnGrid(destPosition)){
            main.logger.log(Level.INFO, "IA destination of the move not on grid = " + destPosition.toString());
            return positions;
        }

        // Process special moves
        if(isSpecialMove(destPosition)){
            main.logger.log(Level.INFO, "IA special move !");
            ArrayList<Action> actions = getActions(destPosition); // Knowing what to do
            for (Action action : actions){
                if(action instanceof ActionMove){ // Moving another piece on the board
                    Position posStart = ((ActionMove) action).getPosStart();
                    Position posEnd = ((ActionMove) action).getPosEnd();
                    moveOtherPiece(posStart, posEnd);
                    positions.add(posStart);
                    positions.add(posEnd);
                } else if (action instanceof ActionRemove){ // Removing a piece on the board
                    Position posRemove = ((ActionRemove) action).getPos();

                    if(isKing(posRemove))
                        setFinished(true);

                    removePiece(posRemove);
                    positions.add(posRemove);

                    main.logger.log(Level.WARNING, "IA "+posRemove.toString()+" is removed !.");
                } else if(action instanceof ActionPromotion){ // Promoting the piece
                    switch (generator.nextInt(4)){ // Auto promote the piece
                        case 0:
                            setPiece(destPosition, new PieceBishop(ColorEnum.BLACK));
                            break;
                        case 1:
                            setPiece(destPosition, new PieceKnight(ColorEnum.BLACK));
                            break;
                        case 2:
                            setPiece(destPosition, new PieceQueen(ColorEnum.BLACK));
                            break;
                        case 3:
                            setPiece(destPosition, new PieceRook(ColorEnum.BLACK));
                            break;
                        default:{
                            main.logger.log(Level.SEVERE, "Promote error type");
                        }
                    }
                }
            }
        }
        positions.add(iaMovement.getPosition());
        positions.add(destPosition);
        if(isKing(destPosition))
            isFinished = true;
        movePiece(destPosition);
        playerTurn = ColorEnum.WHITE;
        return positions;
    }
}
