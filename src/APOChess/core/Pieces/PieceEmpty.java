package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;

public class PieceEmpty extends Piece {
    public PieceEmpty() {
        super(ColorEnum.EMPTY, "Empty");
    }

    @Override
    public String getImage(){
        return "Empty.png";
    }
}
