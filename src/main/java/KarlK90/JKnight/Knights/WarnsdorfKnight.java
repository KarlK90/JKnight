package KarlK90.JKnight.Knights;

import KarlK90.JKnight.Models.Point;
import KarlK90.JKnight.Models.WarnsdorfNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class WarnsdorfKnight extends BaseKnight{

    protected static final int OFFSET = 1; // Neighbors offset
    private WarnsdorfNode[] path; // [][0][0] = x, [][0][1] = y, [][1-7][0] = Jump Matrix Index, [][1-7][1] = Successor Count
    private final String METHOD = "Warnsdorf Rule";

    @Override
    protected String getMethod(){
        return this.METHOD;
    }

    @Override
    protected State nextJump() {

        ArrayList<Integer> jumpList = new ArrayList<>();

        for (int i = 0; i < 8; i++){
            path[jump].Neighbor[i][0] = i;
            Point nextJump = new Point(
                    path[jump].Position.x + jumpMatrix[0][i],
                    path[jump].Position.y + jumpMatrix[1][i]);
            if (chessboard[nextJump.x][nextJump.y] == UNVISITED){
                path[jump].Neighbor[i][1] = calculateSuccessorsCount(nextJump);
            }else {
                path[jump].Neighbor[i][1] = -1;
            }
        }

        Arrays.sort(path[jump].Neighbor, Comparator.comparingInt(o -> o[1]));

        int smallestCount = -1;
        for (int i = 0; i < 8; i++){
            if (path[jump].Neighbor[i][1] >= 0){
                smallestCount = path[jump].Neighbor[i][1];
                break;
            }
        }

        if (smallestCount < 0){
             return State.NoSolution;
        }

        for (int i = 0; i < 8; i++){
            if (path[jump].Neighbor[i][1] == smallestCount){
                jumpList.add(path[jump].Neighbor[i][0]);
            }
        }

        if (jumpList.size() == 1){
            jumpForward(jumpList.get(0));
        }else{
            jumpForward(jumpList.get(generator.nextInt(jumpList.size())));
        }

        return State.Running;
    }

    protected int calculateSuccessorsCount(Point jump){
        int count = 0;
        Point nextJump = new Point();

        for (int i = 0; i < 8; i++) {
            nextJump.x = jump.x + jumpMatrix[0][i];
            nextJump.y = jump.y + jumpMatrix[1][i];

            if (chessboard[nextJump.x][nextJump.y] == UNVISITED) {
                count++;
            }
        }
        return count;
    }

    protected void jumpForward(int jumpMatrixIndex){
        jump++;
        path[jump].Position = new Point(
                path[jump - 1].Position.x + jumpMatrix[0][jumpMatrixIndex],
                path[jump - 1].Position.y + jumpMatrix[1][jumpMatrixIndex]);

        chessboard[path[jump].Position.x][path[jump].Position.y] = jump;
    }

    @Override
    public void setup(Point boardDimension, Point startPosition) {
        super.setup(boardDimension, startPosition);

        path = new WarnsdorfNode[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            path[i] = new WarnsdorfNode();
            path[i].Position = new Point();
            path[i].Neighbor = new int[8][2];
        }
        path[jump].Position = getStartPositionWithMarginApplied(startPosition);
    }
}
