package APOChess.core.IA;

import APOChess.core.Game.Position;

import java.util.ArrayList;
import java.util.Random;

public class IAMovement {

    /**
     * Position of the current Piece
     */
    private final Position position;

    /**
     * Array of special position that the piece can move to.
     */
    private final ArrayList<Position> moves;

    /**
     * Random generator
     */
    private Random generator;

    /**
     * Constructor
     * @param position Position of the current Piece
     * @param destStandardMoves  ArrayList<Position> Array of stardard position that the piece can move to.
     * @param destSpecialdMoves  ArrayList<Position> Array of special position that the piece can move to.
     */
    public IAMovement(Position position, ArrayList<Position> destStandardMoves, ArrayList<Position> destSpecialdMoves){

        this.position = position;
        this.moves = new ArrayList<>();
        this.moves.addAll(destStandardMoves);
        this.moves.addAll(destSpecialdMoves);
        this.generator = new Random();
    }

    /**
     * Position of the current Piece
     * @return Position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Get a possible move
     * @return Position
     */
    public Position getRandomMove(){
        return moves.get(generator.nextInt(moves.size()));
    }
}
