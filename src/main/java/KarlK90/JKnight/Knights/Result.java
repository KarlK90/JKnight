package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Helpers.BoardStopWatch;

public class Result {
    public Result(boolean solution, String method, int[] startPosition, int[][] chessboard, BoardStopWatch watch) {
        this.solution = solution;
        this.method = method;
        this.startPosition = startPosition;
        this.chessboard = chessboard;
        this.watch = watch;
    }

    boolean solution;
    String method;
    int[] startPosition;
    int[][] chessboard;
    BoardStopWatch watch;
}
