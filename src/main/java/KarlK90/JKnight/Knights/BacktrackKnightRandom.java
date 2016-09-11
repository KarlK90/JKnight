package KarlK90.JKnight.Knights;

public class BacktrackKnightRandom extends BacktrackKnight {

    private final String METHOD = "Backtracking - Random Jump Decision";

    @Override
    protected String getMethod(){
        return this.METHOD;
    }

    @Override
    protected void jumpForward() {
        while (true) {
            int rnd = generator.nextInt(8);
            if (path[jump][rnd + OFFSET] != INVALID && path[jump][rnd + OFFSET] != DEADEND) {
                path[jump][rnd + OFFSET] = VISITED;
                jump++;
                path[jump][0] = path[jump - 1][0] + jumpMatrix[0][rnd];
                path[jump][1] = path[jump - 1][1] + jumpMatrix[1][rnd];

                for (int x = 0; x < 8; x++) {
                    path[jump][x + OFFSET] = 0;
                }
                chessboard[path[jump][0]][path[jump][1]] = jump;
                break;
            }
        }
    }
}
