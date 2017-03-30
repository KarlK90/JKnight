package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Models.BacktrackNode;
import KarlK90.JKnight.Models.Point;

public class BacktrackKnight extends BaseKnight {

    private final String METHOD = "Backtracking Optimized";
    private BacktrackNode[] path;

    @Override
    protected String getMethod() {
        return this.METHOD;
    }

    @Override
    protected void setup(Point boardDimension, Point startPosition) {
        super.setup(boardDimension, startPosition);
        path = new BacktrackNode[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            path[i] = new BacktrackNode();
            path[i].Position = new Point();
            path[i].Neighbor = new int[8];
        }

        path[jump].Position = getStartPositionWithMarginApplied(startPosition);
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

    protected State jumpForward(Point position) {
        jump++;
        path[jump].Position = position;
        for (int i = 0; i < path[jump].Neighbor.length; i++) {
            path[jump].Neighbor[i] = 0;
        }
        chessboard[position.x][position.y] = jump;
        return State.Running;
    }

    protected State nextJump() {
        Point currentJump = new Point();
        int status;
        for (int i = 0; i < 8; i++) {
            status = path[jump].Neighbor[i];
            if (status == INVALID || status == DEADEND) continue;

            currentJump.x = path[jump].Position.x + jumpMatrix[0][i];
            currentJump.y = path[jump].Position.y + jumpMatrix[1][i];

            if (chessboard[currentJump.x][currentJump.y] == UNVISITED) {
                path[jump].Neighbor[i] = VISITED;
                return jumpForward(currentJump);
            }
        }

        return jumpBack();
    }

}
