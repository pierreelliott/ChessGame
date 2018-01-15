package APOChess.core.Game;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Pieces.*;

import java.util.ArrayList;

public class Tile {
    /**
     * The piece currently on the tile
     */
    private Piece piece;
    /**
     * A list of all pieces that endanger this Tile (for the King)
     */
    private ArrayList<Piece> threats;
    
    /**
     * Empty constructor
     */
    public Tile() {
        this.piece = new PieceEmpty();
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

    public ArrayList<Piece> getThreats() {
        return threats;
    }

    public void setThreats(ArrayList threats) {
        this.threats = threats;
    }

    /**
     *
     * @param p
     */
    public void addThreat(Piece p) {
        if(!threats.contains(p)) {
            threats.add(p);
        }
    }

    public void removeThreat(Piece p) {
        if(threats.contains(p)) {
            threats.remove(p);
        }
    }

    public boolean isDangerous(ColorEnum color) {
        for (Piece threat: threats) {
            if(threat.getColor().equals(ColorEnum.getOpposite(color))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify if the Tile contains a piece
     * @return True if there is a piece, false otherwise
     */
    public boolean isOccuped()
    {
        return !(this.piece instanceof PieceEmpty);
    }
    
//    /**
//     * Get the color of the chess piece on the tile
//     * @return The color of the chess piece on the Tile or null if there's none
//     */
//    public ColorEnum getColor() {
//        return this.piece.getColor();
//    }
}
