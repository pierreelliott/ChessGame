package APOChess.core.Pieces;

import APOChess.core.Action.Action;
import APOChess.core.Action.ActionRemove;
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
    public ArrayList<Position> getPossibleMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position pFront = new Position(0, 1);
        Position pFront2 = new Position(0, 2);
        Position enPassantLeft = new Position(-1, 1);
        Position enPassantRight = new Position(1, 1);
        Position pLeft = new Position(-1,0);
        Position pRight = new Position(1,0);

        if(color == ColorEnum.WHITE){
            pFront.invert();
            pFront2.invert();
            enPassantLeft.invert();
            enPassantRight.invert();
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
            firstMoveToward && !moved
//            (position.getPosY() == 6 && color == ColorEnum.WHITE) ||
//            (position.getPosY() == 1 && color == ColorEnum.BLACK)
            ){
            if(chessboard.isOnGrid(pFront2))
                if(chessboard.isOnGrid(pFront2))
                    if(!chessboard.isOccuped(pFront2))
                        positions.add(pFront2);
        }

        // Avance coté //ENG
        Position pSide = new Position(position, pLeft);
        Position enPassantSide = new Position(position, enPassantLeft);
        for(int i = 0; i<2; i++){
            if(chessboard.isOnGrid(enPassantSide))
                if(chessboard.isOccuped(enPassantSide))
                    if(chessboard.getTile(enPassantSide).getPiece().getColor() != color)
                        positions.add(enPassantSide);
            pSide = new Position(position, pRight);
            enPassantSide = new Position(position, enPassantRight);
        }

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        if(false)
            return positions;


        Position enPassantLeft = new Position(-1, 1);
        Position enPassantRight = new Position(1, 1);
        Position pLeft = new Position(-1,0);
        Position pRight = new Position(1,0);

        if(color == ColorEnum.WHITE){
            enPassantLeft.invert();
            enPassantRight.invert();
            pLeft.invert();
            pRight.invert();
        }

        // Avance coté //ENG
        Position pSide = new Position(position, pLeft);
        Position enPassantSide = new Position(position, enPassantLeft);
        for(int i = 0; i<2; i++){
            if(chessboard.isOnGrid(enPassantSide)) {
                if (!chessboard.isOccuped(enPassantSide)) {
                    if (chessboard.isOnGrid(pSide)) {
                        if (chessboard.isOccuped(pSide)) {
                            if (chessboard.getTile(pSide).getPiece().getColor() != color) {
                                positions.add(enPassantSide);
                            }
                        }
                    }
                }
            }
            pSide = new Position(position, pRight);
            enPassantSide = new Position(position, enPassantRight);
        }

        return positions;
    }

    @Override
    public ArrayList<Action> getActions(Position positionStart, Position positionEnd) {
        ArrayList<Action> actions = new ArrayList<>();
        if(positionStart.getPosX() - positionEnd.getPosX() > 0){
            actions.add(new ActionRemove(new Position(positionStart, new Position(-1,0))));
        } else {
            actions.add(new ActionRemove(new Position(positionStart, new Position(1,0))));
        }

        return actions;
    }
}
