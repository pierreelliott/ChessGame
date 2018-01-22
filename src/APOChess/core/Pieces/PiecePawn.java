package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PiecePawn extends Piece {
    public PiecePawn(ColorEnum color) {
        super(color, TypeEnum.PAWN);
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position) {
        // FIXME Met-on en doublon (rapport à "en passant") le fait de pouvoir manger en diago ?
        ArrayList<Position> positions = new ArrayList<>();

        Position ahead = new Position(0, 1);

        if(color == ColorEnum.WHITE){
            ahead.invert();
        }

        ahead = new Position(position,ahead);
        positions.add(ahead);

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        Position captureLeft = new Position(-1, 1);
        Position captureRight = new Position(1, 1);
        Position fastForward = new Position(0,2);

        if(color == ColorEnum.WHITE){
            captureLeft.invert();
            captureRight.invert();
            fastForward.invert();
        }

        captureLeft = new Position(position,captureLeft);
        captureRight = new Position(position,captureRight);
        fastForward = new Position(position,fastForward);

        positions.add(captureLeft);
        positions.add(captureRight);

        if(!moved) {
            positions.add(fastForward);
        }

        return positions;
    }

    @Override
    public ArrayList<Position> getThreatenedTiles(Position position) {
        ArrayList<Position> positions = new ArrayList<>();

        // TODO Récupérer les positions où le pion peut aller bouffer des pièces adverses

        return positions;
    }
}
