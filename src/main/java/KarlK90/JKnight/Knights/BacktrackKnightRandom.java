package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Models.BacktrackNode;
import KarlK90.JKnight.Models.Point;

public class BacktrackKnightRandom extends BaseKnight {
    private final String METHOD = "Backtracking - Random Jump Decision";
    BacktrackNode[] path;

    @Override
    protected void setup(Point boardDimension, Point startPosition){
        super.setup(boardDimension, startPosition);
        path = new BacktrackNode[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            path[i] = new BacktrackNode();
            path[i].Position = new Point();
            path[i].Neighbor = new int[8];
        }

        path[jump].Position = getStartPositionWithMarginApplied(startPosition);
    }

    @Override
    protected String getMethod() {
        return this.METHOD;
    }

    @Override
    protected State nextJump() {
        boolean hasValidJumps = false;
        boolean validJumpFound;
        for (int i = 0; i < 8; i++) {
            int status = path[jump].Neighbor[i];
            if (status == INVALID || status == DEADEND) continue;

            validJumpFound = chessboard[path[jump].Position.x + jumpMatrix[0][i]][path[jump].Position.y + jumpMatrix[1][i]] == UNVISITED;

            if (validJumpFound) {
                path[jump].Neighbor[i] = VALID;
                hasValidJumps = true;
            } else {
                path[jump].Neighbor[i] = INVALID;
            }
        }

        if (hasValidJumps) {
            return jumpForward();
        } else {
            return jumpBack();
        }
    }


    protected State jumpForward() {
        while (true) {
            int rnd = generator.nextInt(8);
            if (path[jump].Neighbor[rnd] == VALID) {
                path[jump].Neighbor[rnd] = VISITED;

                jump++;
                path[jump].Position = new Point(path[jump - 1].Position.x + jumpMatrix[0][rnd], path[jump - 1].Position.y + jumpMatrix[1][rnd]);
                for (int i = 0; i < path[jump].Neighbor.length; i++) {
                    path[jump].Neighbor[i] = 0;
                }
                chessboard[path[jump].Position.x][path[jump].Position.y] = jump;
                break;
            }
        }

        return State.Running;
    }

    protected State jumpBack() {
        chessboard[path[jump].Position.x][path[jump].Position.y] = UNVISITED;

        if (--jump < 0) return State.NoSolution;

        for (int i = 0; i < 8; i++) {
            if (path[jump].Neighbor[i] == VISITED) {
                path[jump].Neighbor[i] = DEADEND;
            }
        }

        return State.Running;
    }
}
