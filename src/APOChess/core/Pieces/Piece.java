package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public abstract class Piece {
    protected ColorEnum color;
    protected TypeEnum type;
    protected boolean moved = false;
    protected boolean canMoveToThreatenedTiles = true;

    public Piece(ColorEnum color, TypeEnum type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Color getter
     * @return ColorEnum
     */
    public ColorEnum getColor() {
        return color;
    }

    /**
     * Type enum
     * @return TypeEnum
     */
    public TypeEnum getType() {
        return type;
    }

    /**
     * File name for the image
     * @return String
     */
    public String getImage(){
        return color.getString() + type.toString() + ".png";
    }

    /**
     * Return a list of positions where the piece can be moved.
     * @param position Position of the Piece on the grid
     * @return ArrayList<Position>
     */
    public abstract ArrayList<Position> getPossibleMoves(Position position);
    // TODO enlever les vérifications pour savoir si la pièce peut bouger ou non (style si la couleur est différente de la pièce actuelle)

    /**
     * Return a list of positions where the piece can be moved thanks to a special move.
     * @param position Position of the Piece on the grid
     * @return ArrayList<Position>
     */
    public abstract ArrayList<Position> getSpecialMoves(Position position);
    // TODO enlever les vérifications pour savoir si la pièce peut bouger ou non (style si la couleur est différente de la pièce actuelle)

    public abstract ArrayList<Position> getThreatenedTiles(Position position);

    /**
     * Returns a list of Position associated to a direction from a position.
     * @param position Position of the Piece
     * @param direction Position. Direction to walk
     * @param chessboard Chessboard for viewing other pieces
     * @return ArrayList<Position>
     */
    protected ArrayList<Position> getPosDirection(Position position, Position direction){
        ArrayList<Position> positions = new ArrayList<>();

        if(direction.getPosY() == 0 && direction.getPosX() == 0)
            return positions;

        Position pos = new Position(position, direction);
        for (int i =0; i < 7; i++) {
            positions.add(pos);
            pos = new Position(pos, direction);
        }

        return positions;
    }

    /**
     * Set the piece as has already moved at least one time.
     */
    public void move(){
        moved = true;
    }

    /**
     * <em>true</em> if the piece has already moved at least one time.
     * @return boolean
     */
    public boolean hasMoved(){
        return moved;
    }
}
