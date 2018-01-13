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
}
