package APOChess.core.Game;

import APOChess.core.Pieces.Piece;
import APOChess.core.Pieces.PieceEmpty;

public class Tile {
    /**
     * The piece currently on the tile
     */
    private Piece piece;

    /**
     * Empty constructor
     */
    public Tile() {
        this(new PieceEmpty());
    }
    
    /**
     * Not empty constructor
     * @param piece The piece to put on the tile
     */
    public Tile(Piece piece) {
        this.piece = piece;
    }

    /**
     * Set the tile's piece
     * @param piece The piece to put on the tile
     * @return Tile
     */
    public Tile setPiece(Piece piece) {
        this.piece = piece;
        return this;
    }
    
    /**
     * Get the tile's piece
     * @return The Piece on the tile, or null if there's none
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Reset the piece to a EmptyPiece
     * @return Tile
     */
    public Tile resetPiece(){
        this.piece = new PieceEmpty();
        return this;
    }

    /**
     * Verify if the Tile contains a piece
     * @return True if there is a piece, false otherwise
     */
    public boolean isOccuped()
    {
        return !(this.piece instanceof PieceEmpty);
    }

}
