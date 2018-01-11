package APOChess.core.Game;

public class Move {
    private Position departPosition;
    private Position endPosition;

    public Move(Position departPosition, Position endPosition) {
        this.departPosition = departPosition;
        this.endPosition = endPosition;
    }

    public Position getDepartPosition() {
        return departPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }
    
    
}
