package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Exceptions.NoSolutionException;
import KarlK90.JKnight.Helpers.ICallableListener;

import java.util.Arrays;

public class BacktrackKnight extends BaseKnight {

    private final String METHOD = "Backtracking";

    int[][] path; // [0] = x, [1] = y, [2-9] = Neighbors
    static final int OFFSET = 2; // Neighbors offset

    @Override
    public void reset(int[] startPosition) {
        for (int[] row : chessboard) {
            Arrays.fill(row, UNVISITED);
        }
        jump = 0;
        path = new int[fieldCount][10];
        path[jump][0] = startPosition[0];
        path[jump][1] = startPosition[1];
        chessboard[startPosition[0]][startPosition[1]] = 0;
        watch.reset();
    }

    @Override
    public Result get() {
        Result result = solve();
        listener.callableFinished();
        return result;
    }

    @Override
    public Result solve() {
        boolean solved = false;
        try {
            watch.start();
            while (jump != fieldCount - 1) {
                nextJump();
            }
            watch.stop();
            solved = true;
        } catch (NoSolutionException e) {
            watch.stop();
            solved = false;
        }
        return new Result(solved,METHOD,startPosition,chessboard,watch);
    }

    @Override
    public void setup(int[] boardDimension, int[] startPosition, ICallableListener context) {
        listener = context;
        fieldCount = boardDimension[0] * boardDimension[1];
        this.startPosition = startPosition;
        this.boardDimension = boardDimension;
        chessboard = new int[boardDimension[0]][boardDimension[1]];
        reset(startPosition);
    }

    protected int checkNextJump(int number) {
        int[] nextJump = new int[2];
        nextJump[0] = path[jump][0] + jumpMatrix[0][number];
        nextJump[1] = path[jump][1] + jumpMatrix[1][number];

        if (isLegalJump(nextJump,chessboard)) {
            return VALID;
        } else {
            return INVALID;
        }
    }

    protected boolean currentNodeHasOnlyInvalidJumps() {
        for (int i = 0; i < 8; i++) {
            if (path[jump][i + OFFSET] == VALID) return false;
        }
        return true;
    }

    protected void jumpBack() throws NoSolutionException {
        chessboard[path[jump][0]][path[jump][1]] = UNVISITED;

        if (--jump < 0) throw new NoSolutionException();

        for (int i = 0; i < 8; i++) {
            if (path[jump][i + OFFSET] == VISITED) {
                path[jump][i + OFFSET] = DEADEND;
            }
        }
    }

    protected void jumpForward() {
        for (int i = 0; i < 8; i++) {
            if (path[jump][i + OFFSET] != INVALID && path[jump][i + OFFSET] != DEADEND) {
                path[jump][i + OFFSET] = VISITED;
                jump++;
                path[jump][0] = path[jump - 1][0] + jumpMatrix[0][i];
                path[jump][1] = path[jump - 1][1] + jumpMatrix[1][i];

                for (int x = 0; x < 8; x++) {
                    path[jump][x + OFFSET] = 0;
                }
                chessboard[path[jump][0]][path[jump][1]] = jump;
                break;
            }
        }
    }

    protected void nextJump() throws NoSolutionException {
        for (int i = 0; i < 8; i++) {
            int temp = checkNextJump(i);
            if (temp == VALID && path[jump][i + OFFSET] != DEADEND) {
                path[jump][i + OFFSET] = VALID;
            } else if (temp == INVALID && path[jump][i + OFFSET] != DEADEND) {
                path[jump][i + OFFSET] = INVALID;
            }
        }
        if (currentNodeHasOnlyInvalidJumps()) {
            jumpBack();
        } else {
            jumpForward();
        }
    }

}
