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
    public ArrayList<Position> getPossibleMoves(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pTopRight1 = new Position(2,1);
        Position pTopRight2 = new Position(1,2);

        Position pBottomRight1 = new Position(-2, 1);
        Position pBottomRight2 = new Position(-1, 2);

        positions.add(pTopRight1);
        positions.add(pTopRight2);
        positions.add(pBottomRight1);
        positions.add(pBottomRight2);

        positions.add(pTopRight1.invert());
        positions.add(pTopRight2.invert());
        positions.add(pBottomRight1.invert());
        positions.add(pBottomRight2.invert());

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Position> getThreatenedTiles(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        // TODO Récupérer les positions où le cavalier peut aller bouffer des pièces adverses

        return positions;
    }
}
