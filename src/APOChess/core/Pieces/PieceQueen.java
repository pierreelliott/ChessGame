package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceQueen extends Piece {
    public PieceQueen(ColorEnum color) {
        super(color, TypeEnum.QUEEN);
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

        Position pTop = new Position(0,1);
        Position pRight = new Position(1,0);

        positions.addAll(getPosDirection(position, pTop, chessboard));
        positions.addAll(getPosDirection(position, pRight, chessboard));
        positions.addAll(getPosDirection(position, pTop.invert(), chessboard));
        positions.addAll(getPosDirection(position, pRight.invert(), chessboard));

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position, Chessboard chessboard) {
        return new ArrayList<>();
    }
}
