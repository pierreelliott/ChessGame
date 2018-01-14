package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;
import APOChess.core.Game.Tile;

import java.util.ArrayList;

public abstract class Piece {
    protected ColorEnum color;
    protected String type;
    protected String code;

    public Piece(ColorEnum color, String type) {
        this.color = color;
        this.type = type.toUpperCase();
        this.code = type.substring(0,1).toUpperCase();
    }

    /**
     * Color getter
     * @return ColorEnum
     */
    public ColorEnum getColor() {
        return color;
    }

    /**
     * Type name
     * @return String
     */
    public String getType() {
        return type;
    }

    //FIXME A quoi sert cette fonction ?
    public String getCode() {
        return code;
    }

    /**
     * File name for the image
     * @return String
     */
    public String getImage(){
        return color.getString() + type + ".png";
    }

    /**
     * Return a list of positions where the piece can be moved.
     * @param position Position of the Piece on the grid
     * @param chessboard Chessboard for viewing other pieces
     * @return ArrayList<Position>
     */
    public abstract ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard);

    /**
     * Returns a list of Position associated to a direction from a position.
     * @param position Position of the Piece
     * @param direction Position. Direction to walk
     * @param chessboard Chessboard for viewing other pieces
     * @return ArrayList<Position>
     */
    protected ArrayList<Position> getPosDirection(Position position, Position direction, Chessboard chessboard){
        ArrayList<Position> positions = new ArrayList<>();

        if(direction.getPosY() == 0 && direction.getPosX() == 0)
            return positions;

        Position pos = new Position(position, direction);
        while(chessboard.isOnGrid(pos)){
            if(chessboard.isOccuped(pos)){
                if(chessboard.getTile(pos).getPiece().getColor() != color)
                    positions.add(pos);
                break;
            }
            positions.add(pos);
            pos = new Position(pos, direction);
        }
        return positions;
    }
}
