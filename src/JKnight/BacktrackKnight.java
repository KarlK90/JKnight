package JKnight;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by KarlK.
 */
public class BacktrackKnight extends Knight {

    private int Jump;
    private int[] BoardDimension = new int[2];
    private int BoardSize;
    private int JumpRandomNumber;

    private Integer[] TempJump = new Integer[2];

    private ArrayList<Integer[]> JumpHistory = new ArrayList<>();
    private ArrayList<Integer> CurrentJumpHistory = new ArrayList<>();
    private Chessboard<Boolean[][]> Chessboard;

    BacktrackKnight(int[] BoardDimension, int[] StartPosition) {
        this.BoardSize = BoardDimension[0] * BoardDimension[1];
        this.BoardDimension = BoardDimension;
        this.Chessboard = new Chessboard<Boolean[][]>(BoardDimension);
        this.setX(StartPosition[0]);
        this.setY(StartPosition[1]);
    }

    public void Solve() {

        Jump = 0;

        System.out.println("Beginne zu lösen!");

        while (Jump < BoardSize - 1) {
            Jump();
            //  System.out.println(Jump);
        }

        System.out.println(JumpHistory.toString());

    }

    protected void Jump() {

        CurrentJumpHistory.clear();

        do {
            JumpRandomNumber = NumberGenerator.nextInt(8);

            if (CurrentJumpHistory.contains(JumpRandomNumber)) {

                //System.out.println("Jump " + Jump + " Zufallsnummer " + JumpRandomNumber + "Diese Stelle  Habe ich schon probiert!");
                if (CurrentJumpHistory.size() > 7) {
                    //System.out.println("Jump " + Jump + " Zufallsnummer " + JumpRandomNumber + "Habe alle Möglichkeiten probiert, gehe einen Schritt zurück!");
                    JumpBack(JumpRandomNumber);
                    continue;
                }
                continue;
            }


            TempJump[0] = positionX + rangeX[JumpRandomNumber];
            TempJump[1] = positionY + rangeY[JumpRandomNumber];

            //Check if possible Jump is inside of board dimension
            if (isBetween((int) TempJump[0], -1, BoardDimension[0]) && isBetween((int) TempJump[1], -1, BoardDimension[1])) {

                //Check if field has already been visited
                if (Chessboard.Field[(int) TempJump[0]][(int) TempJump[1]]) {
                    CurrentJumpHistory.add(JumpRandomNumber);
                    continue;
                } else {
                    //If this field was visited before ignore this field, try another field instead or jump back
                    if (JumpHistory.size() > Jump) {

                        if (Arrays.equals(JumpHistory.get(Jump + 1), TempJump)) {
                            CurrentJumpHistory.add(JumpRandomNumber);
                            continue;
                        }

                    }

                    //Successful Jump!
                    Chessboard.Field[(int) TempJump[0]][(int) TempJump[1]] = true;
                    JumpHistory.add(Jump, TempJump.clone());
                    setX((int) TempJump[0]);
                    setY((int) TempJump[1]);
                    Jump++;
                    break;
                }

            } else {
                CurrentJumpHistory.add(JumpRandomNumber);
                continue;
            }

        }
        while (true);

        return;

    }

    private void JumpBack(int JumpRandomNumber) {

        //JumpHistory.remove(JumpHistory.size() - 1);
        Jump--;

        TempJump = JumpHistory.get(Jump).clone();

        for (int i = 0; i < 8; i++) {
            if ((TempJump[0] + rangeX[i]) == getX() && (TempJump[1] + rangeY[i]) == getY()) {
                CurrentJumpHistory.clear();
                CurrentJumpHistory.add(i);
            }
        }

        Chessboard.Field[getX()][getY()] = false;
        setX((int) TempJump[0]);
        setY((int) TempJump[1]);

        return;

    }


}
