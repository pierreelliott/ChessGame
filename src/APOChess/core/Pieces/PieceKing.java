package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Enum.TypeEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PieceKing extends Piece {
    public PieceKing(ColorEnum color) {
        super(color, TypeEnum.KING);
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pos;
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<= 1; j++){
                if(i == 0 && j == 0) {
                    continue;
                }
                pos = new Position(position, new Position(i,j));
                if(chessboard.isOnGrid(pos)) {
                    if (chessboard.isOccuped(pos)) {
                        if (chessboard.getTile(pos).getPiece().getColor() != color) {
                            positions.add(pos);
                        }
                    } else {
                        positions.add(pos);
                    }
                }
            }
        }
        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        if(moved) {
            return positions;
        }

        Position posR1 = new Position(1,0);
        Position posR2 = new Position(2,0);
        Position posRRook1 = new Position(3,0);

        Position posL1 = new Position(-1,0);
        Position posL2 = new Position(-2,0);
        Position posL3 = new Position(-3,0);
        Position posRRook2 = new Position(-4,0);

        posR1 = new Position(position, posR1);
        posR2 = new Position(position, posR2);
        posRRook1 = new Position(position, posRRook1);
        posL2 = new Position(position, posL2);
        posL3 = new Position(position, posL3);
        posRRook2 = new Position(position, posRRook2);

        ArrayList<Position> posEmpty = new ArrayList<>();
        posEmpty.add(posR1);
        posEmpty.add(posR2);
        posEmpty.add(posL1);
        posEmpty.add(posL2);
        posEmpty.add( posL3);

        // Check first condition : no other piece.
        for (Position pos : posEmpty) {
            if(chessboard.isOnGrid(pos)) {
                if(chessboard.isOccuped(pos)) {
                    return positions;
                }
            }
        }

        // Check second condition : There is a rook
        if(chessboard.isOnGrid(posRRook1)) {
            if (chessboard.isOccuped(posRRook1)) {
                if (chessboard.getTile(posRRook1).getPiece().type == TypeEnum.ROOK &&
                        !chessboard.getTile(posRRook1).getPiece().hasMoved()) {
                    positions.add(posR2);
                }
            }
        }
        if(chessboard.isOnGrid(posRRook2)) {
            if (chessboard.isOccuped(posRRook2)) {
                if (chessboard.getTile(posRRook2).getPiece().type == TypeEnum.ROOK &&
                        !chessboard.getTile(posRRook2).getPiece().hasMoved()) {
                    positions.add(posL2);
                }
            }
        }

        return positions;
    }
}
