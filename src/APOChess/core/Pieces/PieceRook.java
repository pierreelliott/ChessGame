package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceRook extends Piece {
    public PieceRook(ColorEnum color) {
        super(color, TypeEnum.ROOK);
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pTop = new Position(0,1);
        Position pRight = new Position(1,0);

        positions.addAll(getPosDirection(position, pTop));
        positions.addAll(getPosDirection(position, pRight));
        positions.addAll(getPosDirection(position, pTop.invert()));
        positions.addAll(getPosDirection(position, pRight.invert()));

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Position> getThreatenedTiles(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        // TODO Récupérer les positions où la tour peut aller bouffer des pièces adverses

        return positions;
    }
}
