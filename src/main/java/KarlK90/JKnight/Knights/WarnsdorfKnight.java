package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Exceptions.NoSolutionException;
import java.util.ArrayList;
import java.util.Arrays;

public class WarnsdorfKnight extends BacktrackKnight{

    protected static final int OFFSET = 1; // Neighbors offset
    private int[][][] path; // [][0][0] = x, [][0][1] = y, [][1-7][0] = Jump Matrix Index, [][1-7][1] = Successor Count
    private final String METHOD = "Warnsdorf Rule";

    @Override
    protected void nextJump() throws NoSolutionException {
        int smallestCount = -1;
        ArrayList<Integer> jumpList = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            path[jump][i + OFFSET][0] = i;
            int[] nextJump = new int[] {
                    path[jump][0][0] + jumpMatrix[0][i],
                    path[jump][0][1] + jumpMatrix[1][i]
            };
            if (isLegalJump(nextJump, chessboard)){
                path[jump][i + OFFSET][1] = calculateSuccessorsCount(nextJump);
            }else {
                path[jump][i + OFFSET][1] = -1;
            }
        }

        Arrays.sort(path[jump], OFFSET, OFFSET + 8, (o1, o2) -> Integer.compare(o1[1],o2[1]));

        for (int i = 0; i < 8; i++){
            if (path[jump][i + OFFSET][1] >= 0){
                smallestCount = path[jump][i + OFFSET][1];
                break;
            }
        }

        if (smallestCount < 0){
             throw new NoSolutionException();
        }

        for (int i = 0; i < 8; i++){
            if (path[jump][i + OFFSET][1] == smallestCount){
                jumpList.add(path[jump][i + OFFSET][0]);
            }
        }

        if (jumpList.size() == 1){
            jumpForward(jumpList.get(0));
        }else{
                jumpForward(jumpList.get(generator.nextInt(jumpList.size())));
        }
    }

    protected int calculateSuccessorsCount(final int[]jump){
        int count = 0;
        int[] nextJump = new int[2];

        for (int i = 0; i < 8; i++) {
            nextJump[0] = jump[0]+ jumpMatrix[0][i];
            nextJump[1] = jump[1]+ jumpMatrix[1][i];
            if (isLegalJump(nextJump, chessboard)) {
                count++;
            }
        }
        return count;
    }

    protected void jumpForward(int jumpMatrixIndex){
        jump++;
        path[jump][0][0] = path[jump - 1][0][0] + jumpMatrix[0][jumpMatrixIndex];
        path[jump][0][1] = path[jump - 1][0][1] + jumpMatrix[1][jumpMatrixIndex];
        chessboard[path[jump][0][0]][path[jump][0][1]] = jump;
    }

    @Override
    public void reset(int[] startPosition) {
        for (int[] row : chessboard) {
            Arrays.fill(row, UNVISITED);
        }
        jump = 0;
        path = new int[fieldCount][9][2];
        path[jump][0][0] = startPosition[0];
        path[jump][0][1] = startPosition[1];
        chessboard[startPosition[0]][startPosition[1]] = 0;
        watch.reset();
        printer.setup(METHOD,startPosition,boardDimension,watch);
    }
}
