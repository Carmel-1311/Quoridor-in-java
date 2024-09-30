package quoridor;

public class Wall {
    int x, y;
    boolean isHorizontal;

    Wall(int x, int y, boolean isHorizontal) {
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
    }
}
