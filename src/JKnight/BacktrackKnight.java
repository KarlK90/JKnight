package JKnight;


/**
 * Created by KarlK.
 */
public class BacktrackKnight extends Knight {

    private int iJump = 0;
    private int[][] iHistory;
    private Chessboard oChessboard;

    BacktrackKnight(int[] aBoardDimension, int[] aStartPosition){
        this.oChessboard = new Chessboard(aBoardDimension);
        this.iHistory = new int[(aBoardDimension[0] * aBoardDimension[1])][2];
        this.setX(aStartPosition[0]);
        this.setY(aStartPosition[1]);
    }

    public void Jump(){

        do{

            this.iJump++;
        }while (this.iJump < this.iHistory.length);

    }




}
