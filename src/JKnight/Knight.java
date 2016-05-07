package JKnight;

import java.util.Random;

/**
 * Created by KarlK.
 */
public abstract class Knight {

    protected Random NumberGenerator = new Random();

    protected int positionX = 0, positionY = 0;

    protected final int[] rangeX = {  3,  2, -2, -3, -3, -2,  2, -2};
    protected final int[] rangeY = {  2,  3,  3,  2, -2, -3, -3,  3};

    public void setX(int positionX) {
        this.positionX = positionX;
    }

    public void setY(int positionY) {
        this.positionY = positionY;
    }

    public int getX() {
        return this.positionX;
    }

    public int getY() {
        return this.positionY;
    }

    public void Solve() {
    }

    protected void Jump() {
    }

    protected boolean isBetween(int value, int min, int max)
    {
        return((value > min) && (value < max));
    }
}
