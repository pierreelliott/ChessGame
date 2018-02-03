package APOChess.core.Pieces;

import APOChess.core.Action.Action;
import APOChess.core.Action.ActionPromotion;
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

        // One and two steps movement
        pFront = new Position(position, pFront);
        pFront2 = new Position(position, pFront2);

        boolean firstMoveToward = false;
        if(chessboard.isOnGrid(pFront))
            if(!chessboard.isOccuped(pFront)){
                firstMoveToward = true;
                positions.add(pFront);
            }

        if(firstMoveToward && !moved){
            if(chessboard.isOnGrid(pFront2))
                if(chessboard.isOnGrid(pFront2))
                    if(!chessboard.isOccuped(pFront2))
                        positions.add(pFront2);
        }

        // Side movement
        Position enPassantSide = new Position(position, enPassantLeft);
        for(int i = 0; i<2; i++){
            if(chessboard.isOnGrid(enPassantSide))
                if(chessboard.isOccuped(enPassantSide))
                    if(chessboard.getTile(enPassantSide).getPiece().getColor() != color)
                        positions.add(enPassantSide);
            enPassantSide = new Position(position, enPassantRight);
        }

        return positions;
    }

    @Override
    public ArrayList<Position> getSpecialMoves(Position position, Chessboard chessboard) {
        ArrayList<Position> positions = new ArrayList<>();

        Position enPassantLeft = new Position(-1, 1);
        Position enPassantRight = new Position(1, 1);
        Position pLeft = new Position(-1,0);
        Position pRight = new Position(1,0);
        Position pTop = new Position(0,1);

        if(color == ColorEnum.WHITE){
            enPassantLeft.invert();
            enPassantRight.invert();
            pLeft.invert();
            pRight.invert();
            pTop.invert();
        }


        if(!(
            (color == ColorEnum.WHITE) && (position.getPosY() != 3) ||
            (color == ColorEnum.BLACK) && (position.getPosY() != 4)))
        {
            // Side movement
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
        }

        // Promotion case

        Position promotion = new Position(position, pTop);
        if(chessboard.isOnGrid(promotion)) {
            if(promotion.getPosY() == 7 || promotion.getPosY() == 0){
                if (!chessboard.isOccuped(promotion)) {
                    positions.add(promotion);
                }
            }
        }
        Position promotionLeft = new Position(position, enPassantLeft);
        if(chessboard.isOnGrid(promotionLeft)) {
            if(promotionLeft.getPosY() == 7 || promotionLeft.getPosY() == 0){
                if (chessboard.isOccuped(promotionLeft)) {
                    if (chessboard.getTile(promotionLeft).getPiece().getColor() != color) {
                        positions.add(promotionLeft);
                    }
                } else {
                    positions.add(promotionLeft);
                }
            }
        }
        Position promotionRight = new Position(position, enPassantRight);
        if(chessboard.isOnGrid(promotionRight)) {
            if(promotionRight.getPosY() == 7 || promotionRight.getPosY() == 0){
                if (chessboard.isOccuped(promotionRight)) {
                    if (chessboard.getTile(promotionRight).getPiece().getColor() != color) {
                        positions.add(promotionRight);
                    }
                } else {
                    positions.add(promotionRight);
                }
            }
        }

        return positions;
    }

    @Override
    public ArrayList<Action> getActions(Position positionStart, Position positionEnd) {
        ArrayList<Action> actions = new ArrayList<>();

        // En passant
        if(positionStart.getPosX() != positionEnd.getPosX()){
            if(positionStart.getPosX() - positionEnd.getPosX() > 0){
                actions.add(new ActionRemove(new Position(positionStart, new Position(-1,0))));
            } else {
                actions.add(new ActionRemove(new Position(positionStart, new Position(1,0))));
            }
        }

        // Promotion
        if(positionEnd.getPosY() == 0 || positionEnd.getPosY() == 7)
            actions.add(new ActionPromotion());

        return actions;
    }
}
