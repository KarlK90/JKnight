package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Helper.BoardStopWatch;
import KarlK90.JKnight.Models.Point;
import KarlK90.JKnight.Models.Result;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public abstract class BaseKnight implements Supplier<Result> {
    public enum State {
        Running,
        NoSolution
    }

    static final int UNVISITED = -2;
    static final int VISITED = 1;
    static final int VALID = 2;
    static final int INVALID = -3;
    static final int DEADEND = -1;
    static final int MARGIN = 4;
    static final int[][] jumpMatrix = {{2, 1, -1, -2, -2, -1, 1, 2}, {1, 2, 2, 1, -1, -2, -2, -1}};

    Integer jump = 0;
    Integer fieldCount;
    Point startPosition;
    Point boardDimension;
    Integer[][] chessboard;

    Random generator = new Random();
    BoardStopWatch watch = new BoardStopWatch();

    protected abstract String getMethod();

    void setup(Point boardDimension, Point startPosition) {
        fieldCount = boardDimension.x * boardDimension.y;
        this.startPosition = getStartPositionWithMarginApplied(startPosition);
        this.boardDimension = getBoardDimensionWithMarginApplied(boardDimension);
        chessboard = getChessboardWithMargin();
        chessboard[this.startPosition.x][this.startPosition.y] = 0;
        watch.reset();
    }

    private Integer[][] getChessboardWithMargin() {
        Integer[][] board = new Integer[boardDimension.x][boardDimension.y];
        for (Integer[] row : board) {
            Arrays.fill(row, UNVISITED);
        }

        final int margin = MARGIN / 2;

        for (int r = 0; r < margin; r++) {
            for (int c = 0; c < boardDimension.y - 1; c++) {
                board[r][c] = INVALID;
            }
        }

        for (int r = boardDimension.x - margin; r < boardDimension.x; r++) {
            for (int c = 0; c < boardDimension.y - 1; c++) {
                board[r][c] = INVALID;
            }
        }

        for (int r = 0; r < boardDimension.x; r++) {
            for (int c = 0; c < margin; c++) {
                board[r][c] = INVALID;
            }
        }

        for (int r = 0; r < boardDimension.x; r++) {
            for (int c = boardDimension.y - margin; c < boardDimension.y; c++) {
                board[r][c] = INVALID;
            }
        }

        return board;
    }

    final Point getStartPositionWithMarginApplied(Point startPosition){
        return new Point(startPosition.x + MARGIN / 2, startPosition.y + MARGIN / 2);
    }

    final Point getBoardDimensionWithMarginApplied(Point boardDimension){
        return new Point(boardDimension.x + MARGIN, boardDimension.y + MARGIN);
    }

    final Result getResult(boolean isSolution, long jumpCounter) {
        return new Result(isSolution,
                getMethod(),
                new Point(startPosition.x - MARGIN / 2, startPosition.y - MARGIN / 2),
                new Point(boardDimension.x - MARGIN, boardDimension.y - MARGIN),
                chessboard,
                watch,
                jumpCounter);
    }

    @Override
    public Result get() {
        return solve();
    }

    public Result solve() {
        long jumpCounter = 0;
        State running = State.Running;
        watch.start();
        while (jump < fieldCount - 1 && running == State.Running) {
            running = nextJump();
            jumpCounter++;
        }
        watch.stop();
        return getResult(running == State.Running, jumpCounter);
    }

    protected abstract State nextJump();
}
