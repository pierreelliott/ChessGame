package APOChess.core.Pieces;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;

public class PiecePawn extends Piece {
    public PiecePawn(ColorEnum color) {
        super(color, "Pawn");
    }

    @Override
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pFront = new Position(0, 1);
        Position pFront2 = new Position(0, 2);
        Position enPassantLeft = new Position(-1, 1);
        Position enPassantRight = new Position(1, 1);
        Position pLeft = new Position(-1,0);
        Position pRight = new Position(1,0);

        if(color == ColorEnum.WHITE){
            pFront = pFront.invert();
            pFront2 = pFront2.invert();
            enPassantLeft = enPassantLeft.invert();
            enPassantRight = enPassantRight.invert();
            pLeft.invert();
            pRight.invert();
        }

        // Avance normale droite et double //ENG
        pFront = new Position(position, pFront);
        pFront2 = new Position(position, pFront2);

        boolean firstMoveToward = false;
        if(chessboard.isOnGrid(pFront))
            if(!chessboard.isOccuped(pFront)){
                firstMoveToward = true;
                positions.add(pFront);
            }

        if(
            firstMoveToward &&
            (position.getPosY() == 6 && color == ColorEnum.WHITE) ||
            (position.getPosY() == 1 && color == ColorEnum.BLACK)
            ){
            if(chessboard.isOnGrid(pFront2))
                if(chessboard.isOnGrid(pFront2))
                    if(!chessboard.isOccuped(pFront2))
                        positions.add(pFront2);
        }

        //TODO finir les déplacements possibles
//        // Capture normale angles //ENG
//        pLeft = new Position(position, pLeft);
//        pRight = new Position(position, pRight);
//        enPassantLeft = new Position(position, enPassantLeft);
//        enPassantRight = new Position(position, enPassantRight);
//        if(chessboard.isOnGrid(pLeft))
//            if(chessboard.isOccuped(pLeft)) // TODO rajouter des regles sur le type de pion a prendre je pense
//                if(chessboard.getTile(pLeft).getPiece().getColor() != color) // Is not the same color
//                    positions.add(pLeft);


        return positions;
    }
}
