package APOChess.core.Game;

import APOChess.Main;
import APOChess.core.Action.Action;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Pieces.Piece;

import java.io.File;
import java.util.ArrayList;

public class Game {
    private ColorEnum playerTurn;
    private boolean pieceSelected;
    private Chessboard board;
    private Position selectedPiecePosition;
    private boolean isFinished = false;

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
        this.pieceSelected = false;
        this.playerTurn = ColorEnum.WHITE;

        board = new Chessboard(main);

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
     * @return <em>True</em> if a piece has been selected (ie, the selection is on the board and it's the player turn), <em>False</em> otherwise
     */
    public boolean selectPiece(int col, int row) {
        if(board.isOnGrid(new Position(col, row)) && board.getTile(col, row).getPiece().getColor() == playerTurn) {
            selectedPiecePosition = new Position(col, row);
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
        Position newPos = new Position(col, row);
        if(board.isOnGrid(selectedPiecePosition) && board.isOnGrid(newPos)) {
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
        Position newPos = new Position(col,row);
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
        return board.getTile(selectedPiecePosition)
                .getPiece().getActions(selectedPiecePosition, new Position(col, row));
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
        Position pos = new Position(col, row);
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
