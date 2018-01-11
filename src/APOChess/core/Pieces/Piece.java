package APOChess.core.Pieces;

import APOChess.core.Game.Move;

public abstract class Piece {
    private String color;
    private String type;
    private String code;

    public Piece(String color, String type) {
        this.color = color;
        this.type = type.toUpperCase();
        this.code = type.substring(0,1).toUpperCase();
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    //TODO Décommenter et implémenter pour chacune des classes
    //public abstract Move[] getPossibleMoves();
}
