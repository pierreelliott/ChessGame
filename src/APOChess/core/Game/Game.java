package APOChess.core.Game;

import APOChess.Main;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Pieces.Piece;

import java.util.ArrayList;

public class Game {
    private ColorEnum playerTurn;
    private Main main;
    private boolean pieceSelected;
    private Chessboard board;
    private Position selectedPiecePosition;

    public Game(Main main) {
        this(main, "");
    }

    public Game(Main main, String filepath) {
        this.main = main;
        this.pieceSelected = false;
        this.playerTurn = ColorEnum.WHITE;

        board = new Chessboard(main);

        if(filepath.equalsIgnoreCase("")) {
            board.initialize();
        } else {
            board.initialize(filepath);
        }
    }

    public ArrayList<Position> getMoves(Piece piece,int col, int row) {
        ArrayList<Position> positions = new ArrayList<>();
        Position pos = new Position(col, row);

        if (board.isOnGrid(new Position(col, row))) {
            ArrayList<Position> standardMoves = board.getAvailableMoves(piece,pos);
            ArrayList<Position> specialMoves = board.getSpecialeMoves(piece,pos);

            positions.addAll(standardMoves);
            positions.addAll(specialMoves);
        }

        return positions;
    }

    public ArrayList<Position> getMoves(Piece piece,Position pos) {
        return getMoves(piece,pos.getPosX(), pos.getPosY());
    }

    /**
     * Select the piece located at the given column and row.
     * If the location isn't correct, any actual piece is deselected.
     * @param col Piece's location's column
     * @param row Piece's location's row
     * @return <em>True</em> if a piece has been selected (ie, the selection is on the board and it's the player turn), <em>False</em> otherwise
     */
    public boolean selectPiece(int col, int row) {
        /* If the Tile selected is on the grid and the piece on it belongs to the player */
        if(board.isOnGrid(new Position(col, row)) && board.getTile(col, row).getPiece().getColor() == playerTurn) {
            selectedPiecePosition = new Position(col, row);
            pieceSelected = true;
            return pieceSelected;
        }
        /* If the player selects a wrong Tile (not on the board, or not his), the current piece is unselected */
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
            // FIXME Problem for special moves with King/Rook
            if( !(board.getTile(selectedPiecePosition).getPiece().getColor() == board.getTile(newPos).getPiece().getColor()) ) {
                board.getTile(selectedPiecePosition).getPiece().move();
                updateThreatenedTiles(newPos);
                board.getTile(newPos).setPiece(board.getTile(selectedPiecePosition).getPiece());
                board.getTile(selectedPiecePosition).resetPiece();

                playerTurn = ColorEnum.getOpposite(playerTurn);
            }
            // No matter if the piece hasn't moved, it is deselected
            pieceSelected = false;
        }
    }

    private void updateThreatenedTiles(Position newPos) {
        ArrayList<Position> positions = board.getThreatenedTiles(getSelectedPiece(), selectedPiecePosition);
        for (Position pos: positions) {
            board.getTile(pos).removeThreat(getSelectedPiece());
        }

        positions = board.getThreatenedTiles(getSelectedPiece(), newPos);
        for (Position pos: positions) {
            board.getTile(pos).addThreat(getSelectedPiece());
        }
    }

    private boolean isCapturing(Position newPos) {
        if(board.isOnGrid(selectedPiecePosition) && board.isOnGrid(newPos)) {
            if( !(board.getTile(selectedPiecePosition).getPiece().getColor() == board.getTile(newPos).getPiece().getColor()) ) {
                return true;
            }
        }
        return false;
    }

    private void capturePiece(Position pos) {

    }

    public boolean canMoveTo(int col, int row) {
        Position newPos = new Position(col,row);
        if(pieceSelected && board.isOnGrid(newPos)) {
            ArrayList<Position> positions = getMoves(getSelectedPiece(),selectedPiecePosition);
            if (positions.contains(newPos)) {
                return true;
            }
        }
        return false;
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

    public String getPieceImage(int col, int row) {
        Position pos = new Position(col, row);
        if(board.isOnGrid(pos)) {
            return board.getTile(pos).getPiece().getImage();
        }
        return "";
    }
}
