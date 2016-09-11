package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Helpers.BoardStopWatch;

public class Result {
    Result(boolean solution, String method, int[] startPosition, int[] boardDimension, int[][] chessboard, BoardStopWatch watch) {
        this.solution = solution;
        this.method = method;
        this.startPosition = startPosition;
        this.boardDimension = boardDimension;
        this.chessboard = chessboard;
        this.watch = watch;
    }

    public boolean solution;
    public String method;
    public int[] startPosition;
    public int[] boardDimension;
    public int[][] chessboard;
    public BoardStopWatch watch;
}
