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

    public ColorEnum getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getImage(){
        return color.getString() + type + ".png";
    }

    //TODO Décommenter et implémenter pour chacune des classes
    public abstract ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard);
}
