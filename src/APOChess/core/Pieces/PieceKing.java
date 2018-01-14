package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceKing extends Piece {
    public PieceKing(ColorEnum color) {
        super(color, "King");
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pos;
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<= 1; j++){
                if(i == 0 && j == 0)
                    continue;
                pos = new Position(position, new Position(i,j));
                if(chessboard.isOnGrid(pos))
                    if(chessboard.isOccuped(pos)) {
                        if (chessboard.getTile(pos).getPiece().getColor() != color)
                            positions.add(pos);
                    } else
                        positions.add(pos);
            }
        }
        return positions;
    }
}
