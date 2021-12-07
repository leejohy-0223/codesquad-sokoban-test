package sokoban;

public class PlayerPosition {
    private int posX;
    private int posY;

    public PlayerPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void moveToHere(int nx, int ny) {
        posX = nx;
        posY = ny;
    }
}
