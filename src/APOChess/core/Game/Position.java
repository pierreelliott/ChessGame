package APOChess.core.Game;

public class Position {
    private int posX;
    private int posY;

    public Position(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
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
    public void setPosX(int posX) {
        this.posX = posX;
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
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (posX != position.posX) return false;
        return posY == position.posY;
    }
}
