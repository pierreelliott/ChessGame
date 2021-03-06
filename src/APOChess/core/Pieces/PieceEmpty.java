package APOChess.core.Pieces;

import APOChess.core.Action.Action;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceEmpty extends Piece {
    public PieceEmpty() {
        super(ColorEnum.EMPTY, TypeEnum.EMPTY);
    }

    @Override
    public String getImage(){
        return "Empty.png";
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position, Chessboard chessboard) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Action> getActions(Position positionStart, Position positionEnd) {
        return new ArrayList<>();
    }
}
