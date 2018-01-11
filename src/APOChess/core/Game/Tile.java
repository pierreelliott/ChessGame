package APOChess.core.Game;

import APOChess.core.Pieces.*;

public class Tile {
    /**
     * The piece currently on the tile
     */
    private Piece piece;
    
    /**
     * Empty constructor
     */
    public Tile() {
        this.piece = null;
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
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * Get the tile's piece
     * @return The Piece on the tile, or null if there's none
     */
    public Piece getPiece() {
        return this.piece;
    }
    
    /**
     * Verify if the Tile contains a piece
     * @return True if there is a piece, false otherwise
     */
    public boolean isOccuped()
    {
        return (this.piece != null);	
    }
    
    /**
     * Get the color of the chess piece on the tile
     * @return The color of the chess piece on the Tile or null if there's none
     */
    public String getColor() {
        return (this.piece != null) ? this.piece.getColor() : null;
    }
}
