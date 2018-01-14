package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceKnight extends Piece {
    public PieceKnight(ColorEnum color) {
        super(color, TypeEnum.KNIGHT);
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pTopRight1 = new Position(2,1);
        Position pTopRight2 = new Position(1,2);

        Position pBottomRight1 = new Position(-2, 1);
        Position pBottomRight2 = new Position(-1, 2);

        ArrayList<Position> posToTest = new ArrayList<>();
        posToTest.add(pTopRight1);
        posToTest.add(pTopRight2);
        posToTest.add(pBottomRight1);
        posToTest.add(pBottomRight2);

        Position pos;
        for(int i = 0; i<2; i++){
            for (Position posV : posToTest) {
                pos = new Position(position, posV);
                if(chessboard.isOnGrid(pos))
                    if(chessboard.isOccuped(pos)) {
                        if (chessboard.getTile(pos).getPiece().getColor() != color)
                            positions.add(pos);
                    } else
                        positions.add(pos);
                posV.invert();
            }
        }

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position, Chessboard chessboard) {
        return new ArrayList<>();
    }
}
