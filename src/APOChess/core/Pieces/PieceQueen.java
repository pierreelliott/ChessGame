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
    public ArrayList<Position> getPossibleMoves(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pTopRight = new Position(1,1);
        Position pTopLeft = new Position(1,-1);

        positions.addAll(getPosDirection(position, pTopRight));
        positions.addAll(getPosDirection(position, pTopLeft));
        positions.addAll(getPosDirection(position, pTopRight.invert()));
        positions.addAll(getPosDirection(position, pTopLeft.invert()));

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

        // TODO Récupérer les positions où la reine peut aller bouffer des pièces adverses

        return positions;
    }
}
