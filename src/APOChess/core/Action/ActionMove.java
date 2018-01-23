package APOChess.core.Action;

import APOChess.core.Game.Position;

public class ActionMove implements Action{

    private Position posStart;
    private Position posEnd;

    public ActionMove(Position posStart, Position posEnd){
        this.posStart = posStart;
        this.posStart = posEnd;
    }

    public Position getPosStart() {
        return posStart;
    }

    public Position getPosEnd() {
        return posEnd;
    }
}
