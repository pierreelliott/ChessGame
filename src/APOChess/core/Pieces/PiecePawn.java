package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;
import APOChess.core.Game.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PiecePawn extends Piece {
    public PiecePawn(ColorEnum color) {
        super(color, "Pawn");
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position enPassantFront = new Position(0, 1);
        Position enPassantFront2 = new Position(0, 2);
        Position enPassantLeft = new Position(-1, 1);
        Position enPassantRight = new Position(1, 1);

        if(color == ColorEnum.WHITE){
            enPassantFront = enPassantFront.invert();
            enPassantFront2 = enPassantFront2.invert();
            enPassantLeft = enPassantLeft.invert();
            enPassantRight = enPassantRight.invert();
        }

        if(chessboard.isOnGrid(new Position(position, enPassantFront)))
            if(!chessboard.isOccuped(new Position(position, enPassantFront)))
                positions.add(new Position(position, enPassantFront));

        if(
            (position.getPosY() == 6 && color == ColorEnum.WHITE) ||
            (position.getPosY() == 1 && color == ColorEnum.BLACK)
            ){
            if(chessboard.isOnGrid(new Position(position, enPassantFront2)))
                if(chessboard.isOnGrid(new Position(position, enPassantFront2)))
                    if(!chessboard.isOccuped(new Position(position, enPassantFront2)))
                        positions.add(new Position(position, enPassantFront2));
        }


        return positions;
    }
}
