package JKnight;

/**
 * Created by KarlK.
 */
public abstract class Knight {

    protected static final int UNVISITED = 0;
    protected static final int VISITED = 1;
    protected static final int VALID = 2;
    protected static final int INVALID = 20;
    protected static final int DEADEND = -1;

    protected final int[][] jumpMatrix = {{  2,  1, -1, -2, -2, -1,  1, 2},{  1,  2,  2,  1, -1, -2, -2,  -1}};

    public abstract void Solve();
    protected abstract void nextJump();

    protected static boolean isBetween(int value, int min, int max)
    {
        return((value > min) && (value < max));
    }
}
