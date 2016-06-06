package JKnight;

/**
 * Created by KarlK.
 */
public class BacktrackKnight extends Knight {

    private int jumpCounter, chessFieldCount, randomJumpDecision, i, temp;
    private int[] tempJump = new int[2];
    private int[] boardDimension = new int[2];
    private boolean[][] chessboard;

    private Node currentNode;
    private Node[] nodeHistory;

    BacktrackKnight(int[] boardDimension, int[] startPosition) {
        chessFieldCount = boardDimension[0] * boardDimension[1];
        this.boardDimension = boardDimension;
        nodeHistory = new Node[chessFieldCount];
        chessboard = new boolean[boardDimension[0]][boardDimension[1]];
        currentNode = new Node(0,startPosition[0],startPosition[1]);
        chessboard[startPosition[0]][startPosition[1]] = true;
    }

    public void Solve() {
        jumpCounter = 0;
        System.out.println("Beginne zu lösen!");

        while (jumpCounter < chessFieldCount) {
            nextJump();
        }

        for( Node x : nodeHistory){
            System.out.println(x.id + ": x: " + x.x + " y: " + x.y);
        }
    }


    protected void nextJump(){

        for ( i = 0; i < 8; i++){
            temp = checkNextJump(i);
            if (temp != INVALID && currentNode.jumps[i] != DEADEND ){
                currentNode.jumps[i] = VALID;
            }
            else if( currentNode.jumps[i] != DEADEND && temp == INVALID) {
                currentNode.jumps[i] = INVALID;
            }
        }

        if(currentNode.hasOnlyInvalidJumps()){
            jumpBack();
        }else {
            for (int i = 0; i < 8; i++) {
                if (currentNode.jumps[i] == INVALID || currentNode.jumps[i] == DEADEND || currentNode.jumps[i] == VISITED) {
                } else {
                    currentNode.jumps[i] = VISITED;
                    nodeHistory[jumpCounter] = currentNode;
                    jumpCounter++;
                    currentNode = new Node(jumpCounter, currentNode.x + jumpMatrix[0][i], currentNode.y + jumpMatrix[1][i]);
                    chessboard[currentNode.x][currentNode.y] = true;
                    break;
                }
            }
        }

    }

    private void jumpBack() {
        jumpCounter--;
        chessboard[currentNode.x][currentNode.y] = false;
        currentNode = nodeHistory[jumpCounter];
        for (i = 0; i < currentNode.jumps.length; i++){
            if (currentNode.jumps[i] == VISITED){
                currentNode.jumps[i] = DEADEND;
            }
        }
    }
    

    private int checkNextJump(int number){
        tempJump[0] = currentNode.x + jumpMatrix[0][number];
        tempJump[1] = currentNode.y + jumpMatrix[1][number];

        if (isBetween(tempJump[0], -1, boardDimension[0]) && isBetween(tempJump[1], -1, boardDimension[1])) {
            //Check if field has already been visited
            if (!chessboard[tempJump[0]][tempJump[1]]) {
               return VALID;
            }
            return INVALID;
        }else {
            //Move is outside of board
            return INVALID;
        }
    }
}
