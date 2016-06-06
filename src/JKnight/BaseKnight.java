package JKnight;

public abstract class BaseKnight implements Runnable {

    protected static final int UNVISITED;
    protected static final int VISITED;
    protected static final int VALID;
    protected static final int INVALID;
    protected static final int DEADEND;

    static {
        VISITED = 1;
        UNVISITED = -2;
        VALID = 2;
        INVALID = 20;
        DEADEND = -1;
    }

    protected final int[][] jumpMatrix = {{2, 1, -1, -2, -2, -1, 1, 2}, {1, 2, 2, 1, -1, -2, -2, -1}};

    public abstract void solve();

    public abstract void setup(int[] boardDimension, int[] startPosition);

    public abstract void reset(int[] startPosition);

    protected abstract String getMethod();

    protected static boolean isBetween(int value, int min, int max) {
        return ((value > min) && (value < max));
    }
}
