package KarlK90.JKnight.Models;

import KarlK90.JKnight.Helper.BoardStopWatch;

public class Result {
    public Result(boolean isSolution, String method, Point startPosition, Point boardDimension, Integer[][] chessboard, BoardStopWatch watch, Long jumpCounter) {
        this.isSolution = isSolution;
        this.method = method;
        this.startPosition = startPosition;
        this.boardDimension = boardDimension;
        this.chessboard = chessboard;
        this.watch = watch;
        this.jumpCounter = jumpCounter;
    }

    public boolean isSolution;
    public String method;
    public Point startPosition;
    public Point boardDimension;
    public Integer[][] chessboard;
    public BoardStopWatch watch;
    public Long jumpCounter;
}
