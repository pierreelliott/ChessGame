package APOChess.core.Action;

import APOChess.core.Game.Position;

public class ActionRemove implements Action{

    private Position pos;

    public ActionRemove(Position pos){
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }
}
