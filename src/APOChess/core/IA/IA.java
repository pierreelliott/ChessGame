package APOChess.core.IA;

import APOChess.core.Enum.ColorEnum;
import APOChess.core.Game.Chessboard;
import APOChess.core.Game.Position;

import java.util.ArrayList;
import java.util.Random;

public class IA {

    /**
     * Chessboard for testing piece
     */
    private Chessboard board;

    /**
     * List of movement possible
     */
    private ArrayList<IAMovement> movements;

    /**
     * Random generator
     */
    private Random generator;

    /**
     * <em>false</em> when there is no possible moves.
     */
    private boolean isMovesPossible;

    /**
     * Constructor
     * @param chessboard Chessboard
     */
    public IA(Chessboard chessboard){
        this.board = chessboard;
        this.movements = new ArrayList<>();
        this.generator = new Random();
        this.isMovesPossible = false;
    }

    /**
     * Process the listing of possible movement with current chessboard
     */
    public void processMovementsList(){
        isMovesPossible = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.getTile(i,j).getPiece().getColor() == ColorEnum.BLACK){
                    if(board.getAvailableMoves(i,j).isEmpty() && board.getSpecialeMoves(i,j).isEmpty())
                        continue;
                    // Add the piece position with the possible move positions for that piece
                    movements.add(
                            new IAMovement(
                                    new Position(i,j),
                                    board.getAvailableMoves(i,j),
                                    board.getSpecialeMoves(i,j))
                    );
                    isMovesPossible = true;
                }
            }
        }
    }

    /**
     * <em>false</em> when there is no possible moves.
     * @return boolean
     */
    public boolean isMovesPossible(){
        return isMovesPossible;
    }

    /**
     * Pick a random movement possible
     * @return IAMovement
     */
    public IAMovement selectIAMovement(){
        return movements.get(generator.nextInt(movements.size()));
    }
}
