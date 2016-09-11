package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Helpers.BoardStopWatch;
import KarlK90.JKnight.Helpers.ICallableListener;

import java.util.Random;
import java.util.function.Supplier;

public abstract class BaseKnight implements Supplier<Result> {

    static final int UNVISITED;
    static final int VISITED;
    static final int VALID;
    static final int INVALID;
    static final int DEADEND;

    static {
        VISITED = 1;
        UNVISITED = -2;
        VALID = 2;
        INVALID = 20;
        DEADEND = -1;
    }

    int jump, fieldCount;
    int[] startPosition = new int[2];
    int[] boardDimension = new int[2];
    int[][] chessboard;

    Random generator = new Random();
    BoardStopWatch watch = new BoardStopWatch();
    ICallableListener listener;

    final int[][] jumpMatrix = {{2, 1, -1, -2, -2, -1, 1, 2}, {1, 2, 2, 1, -1, -2, -2, -1}};

    abstract public Result get();

    public abstract Result solve();

    public abstract void setup(int[] boardDimension, int[] startPosition, ICallableListener context);

    public abstract void reset(int[] startPosition);

    final boolean isBetween(int value, int min, int max) {
        return ((value > min) && (value < max));
    }

    final boolean isLegalJump(int[] jump, int[][]board){
        return isBetween(jump[0], -1, board[0].length) && isBetween(jump[1], -1, board[1].length) && board[jump[0]][jump[1]] == UNVISITED;
    }
}
