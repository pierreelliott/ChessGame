package APOChess.core.Action;

import APOChess.core.Game.Position;

public class ActionRemove implements Action{

    /**
     * Position of a piece to remove
     */
    private Position pos;

    /**
     * Store the position of a piece to remove
     * @param pos
     */
    public ActionRemove(Position pos){
        this.pos = pos;
    }

    /**
     * Get the position of the piece to remove
     * @return
     */
    public Position getPos() {
        return pos;
    }
}
