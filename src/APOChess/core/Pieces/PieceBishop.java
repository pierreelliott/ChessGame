package APOChess.core.Pieces;

import APOChess.core.Action.Action;
import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceBishop extends Piece {
    public PieceBishop(ColorEnum color) {
        super(color, TypeEnum.BISHOP);
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pTopRight = new Position(1,1);
        Position pTopLeft = new Position(1,-1);

        positions.addAll(getPosDirection(position, pTopRight, chessboard));
        positions.addAll(getPosDirection(position, pTopLeft, chessboard));
        positions.addAll(getPosDirection(position, pTopRight.invert(), chessboard));
        positions.addAll(getPosDirection(position, pTopLeft.invert(), chessboard));

        return positions;
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
