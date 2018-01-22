package APOChess.core.Game;

import APOChess.Main;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Pieces.Piece;
import APOChess.core.Pieces.PieceKing;
import APOChess.core.Pieces.PiecePawn;

import java.util.ArrayList;

public class Game {
    private ColorEnum playerTurn;
    private boolean pieceSelected;
    private Chessboard board;
    private Position selectedPiecePosition;

    public Game(Main main) {
        this(main, "");
    }

    public Game(Main main, String filepath) {
        this.pieceSelected = false;
        this.playerTurn = ColorEnum.WHITE;

        board = new Chessboard(main);

        if(filepath.equalsIgnoreCase("")) {
            board.initialize();
        } else {
            board.initialize(filepath);
        }
    }

    /**
     * Select the piece located at the given column and row. If the location isn't correct, does nothing.
     * If a piece was selected, moves the piece to the new location (if possible);
     * otherwise, selects this piece if it belongs to the current player.
     * @param col Selected Tile's location's column
     * @param row Selected Tile's location's row
     */
    public void selectTile(int col, int row) {
        Position pos = new Position(col,row);
        if(board.isOnGrid(pos)) {
            if(pieceSelected) { // If a piece is selected
                movePiece(pos); // Move the selected piece to the new pos

                selectedPiecePosition = null; // Deselect the piece
                pieceSelected = false;
            } else {
                Piece piece = board.getTile(pos).getPiece();
                if(piece.getColor() == playerTurn) { // If the piece selected belongs to the player, select it
                    selectedPiecePosition = pos;
                    pieceSelected = true;
                }
            }
        }
    }

    /**
     * Move a piece to the given Position if it is possible.
     * If a piece has been moved, the current player change.
     * @param newPos
     */
    private void movePiece(Position newPos) {
        // TODO Check if player's king isn't under check
        Piece piece = getSelectedPiece();
        if(canMoveTo(playerTurn,piece,selectedPiecePosition,newPos)) {
            if(board.isOccuped(newPos)) {

            } else {
                piece.move();
                board.getTile(newPos).setPiece(piece);
                board.getTile(selectedPiecePosition).resetPiece();

                // TODO Handle the possible second move
            }

            playerTurn = ColorEnum.getOpposite(playerTurn);
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

    // Fixme Je ne sais plus du tout ce que j'avais voulu faire...
    private boolean isCapturing(Position newPos) {
        if(board.isOnGrid(selectedPiecePosition) && board.isOnGrid(newPos)) {
            if( !(board.getTile(selectedPiecePosition).getPiece().getColor() == board.getTile(newPos).getPiece().getColor()) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the positions from normal moves (for the given Piece) returned by the Chessboard follow chess game rules
     * @param player Player who owns the piece
     * @param piece Piece which we want the special moves from
     * @param from Position of the piece on the board
     * @return Valid new positions from normal moves for the given Piece
     */
    public ArrayList<Position> getNormalMoves(ColorEnum player, Piece piece, Position from) {
        ArrayList<Position> positions = new ArrayList<>();
        ArrayList<Position> normalMoves = board.getAvailableMoves(piece, from);

        if (piece instanceof PieceKing) {
            for(Position pos : normalMoves) {
                // TODO Vérifier que le roi n'est pas en échec (peut-être faire une fonction externe)
            }
        } else if (piece instanceof PiecePawn) {
            for(Position pos : normalMoves) {
                // TODO Handle Pawn's promotion
                if(!board.isOccuped(pos)) { // If there's no one on the Tile
                    positions.add(pos);
                }
            }
        } else {
            // If not a King or a Pawn, then there's no conditions on normal moves
            positions.addAll(normalMoves);
        }
        return positions;
    }

    /**
     * Check if the positions from normal moves (for the currently selected Piece) returned by the Chessboard follow chess game rules
     * @return Valid new positions from normal moves for the currently selected Piece
     */
    public ArrayList<Position> getNormalMoves() {
        if(pieceSelected) {
            return getNormalMoves(playerTurn,getSelectedPiece(),selectedPiecePosition);
        }
        return new ArrayList<>();
    }

    /**
     * Check if the positions from special moves (for the given Piece) returned by the Chessboard follow chess game rules
     * @param player Player who owns the piece
     * @param piece Piece which we want the special moves from
     * @param from Position of the piece on the board
     * @return Valid new positions from special moves for the given Piece
     */
    public ArrayList<Position> getSpecialMoves(ColorEnum player, Piece piece, Position from) {
        // TODO Faudrait séparer un peu plus le code (je pense que ça doit être possible)
        ArrayList<Position> positions = new ArrayList<>();
        ArrayList<Position> specialMoves = board.getSpecialMoves(piece, from);

        if (piece instanceof PieceKing) {
            for(Position pos : specialMoves) {
                Piece p = board.getTile(pos).getPiece();
                if(p.getColor() == player &&            // Does the Piece belong to the player ?
                        p.getType() == TypeEnum.ROOK &&     // Is it a Rook ?
                        p.hasMoved() == false) {            // Has it already moved ?

                    if(true) { // TODO Est-elle "en échec" ?
                        positions.add(pos);
                    }
                }
            }
        } else if (piece instanceof PiecePawn) {
            // TODO (Peut-être) revoir l'ordre des conditions

            for(Position pos : specialMoves) {
                Piece p = board.getTile(pos).getPiece();

                if(p.getColor() != player) { // Does the Piece on the Tile not belong to the player ?

                    Position adj = new Position(pos.getPosX(), from.getPosY());
                    if (board.getTile(adj).getPiece().getColor() == ColorEnum.getOpposite(player)) {
                        // Was there an opponent's Piece on the adjacent Tile ?
                        positions.add(pos);
                    }
                }

                if(p.getColor() == ColorEnum.getOpposite(player)) {
                    // The Pawn simply capture the opponent Piece
                    positions.add(pos);
                }

                // We do not manage the Pawn's "fast forward" here, the piece manage it by itself
            }
        }
        return positions;
    }

    /**
     * Check if the positions from special moves (for the currently selected Piece) returned by the Chessboard follow chess game rules
     * @return Valid new positions from special moves for the currently selected Piece
     */
    public ArrayList<Position> getSpecialMoves() {
        if(pieceSelected) {
            return getSpecialMoves(playerTurn,getSelectedPiece(),selectedPiecePosition);
        }
        return new ArrayList<>();
    }

    /**
     * Test if the given piece of the given player can move from a position to another
     * @param player Player who owns the piece
     * @param piece Piece which move is checked
     * @param from Starting position of the piece
     * @param to End position of the piece
     * @return
     */
    public boolean canMoveTo(ColorEnum player, Piece piece, Position from, Position to) {
        if(board.isOnGrid(from) && board.isOnGrid(to)) {
            ArrayList<Position> positions = getSpecialMoves(player,piece,from);
            if(positions.contains(to)) {
                return true;
            }

            positions = getNormalMoves(player,piece,from);
            if(positions.contains(to)) {
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

    // Fixme Voir si y en a toujours besoin (normalement oui)
    public String getPieceImage(int col, int row) {
        Position pos = new Position(col, row);
        if(board.isOnGrid(pos)) {
            return board.getTile(pos).getPiece().getImage();
        }
        return "";
    }

    public String[][] getBoard() {
        String[][] imageBoard = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                imageBoard[i][j] = board.getTile(i,j).getPiece().getImage();
            }
        }
        return imageBoard;
    }
}
