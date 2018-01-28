package APOChess.core.Action;

import APOChess.core.Game.Position;

public class ActionMove implements Action{

    /**
     * Start position
     */
    private Position posStart;

    /**
     * End position
     */
    private Position posEnd;

    /**
     * Store start and end position.
     * @param posStart
     * @param posEnd
     */
    public ActionMove(Position posStart, Position posEnd){
        this.posStart = posStart;
        this.posEnd = posEnd;
    }

    /**
     * Get start position
     * @return
     */
    public Position getPosStart() {
        return posStart;
    }

    /**
     * Get end Position
     * @return
     */
    public Position getPosEnd() {
        return posEnd;
    }
}
