package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceKing extends Piece {
    public PieceKing(ColorEnum color) {
        super(color, TypeEnum.KING);
        canMoveToThreatenedTiles = false;
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pos;
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<= 1; j++){
                if(i == 0 && j == 0) {
                    continue;
                }
                positions.add(new Position(position, new Position(i,j)));
            }
        }
        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        if(moved) {
            return positions;
        }

        Position posRRook1 = new Position(position, new Position(3,0));
        Position posRRook2 = new Position(position, new Position(-4,0));

        positions.add(posRRook1);
        positions.add(posRRook2);

        return positions;
    }

    @Override
    public ArrayList<Position> getThreatenedTiles(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        // TODO Récupérer les positions où le roi peut aller bouffer des pièces adverses

        return positions;
    }
}
