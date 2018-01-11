package APOChess.core.Game;

public class Position {
    private int posX;
    private int posY;

    public Position(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Position(Position p1, Position p2){
        this.posX = p1.getPosX() + p2.getPosX();
        this.posY = p1.getPosY() + p2.getPosY();
    }

    /**
     * PosX getter
     * @return The value of posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * PosX setter
     * @param posX The new value of posX
     */
    public Position setPosX(int posX) {
        this.posX = posX;
        return this;
    }

    /**
     * PosY getter
     * @return The value of posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * PosY setter
     * @param posY The new value of posY
     */
    public Position setPosY(int posY) {
        this.posY = posY;
        return this;
    }

    /**
     * Inverse the position
     * @return Position
     */
    public Position invert(){
        posX *= -1;
        posY *= -1;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (posX != position.posX) return false;
        return posY == position.posY;
    }

    @Override
    public String toString(){
        return "["+posX+";"+posY+"]";
    }
}
