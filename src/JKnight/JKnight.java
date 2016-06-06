package JKnight;

import java.util.Random;

public class JKnight {

    public static void main(String[] args) {
        int[] boardDimension = {8, 8};
        int[] startPosition = {0, 0};
        Random generator = new Random();

        BacktrackKnight knight = new BacktrackKnight(boardDimension, startPosition);
        BacktrackKnightRandom knightRandom = new BacktrackKnightRandom(boardDimension, startPosition);

        Thread knightThread = new Thread(knight);
        Thread knightRandomThread = new Thread(knightRandom);

        knightThread.start();
        knightRandomThread.start();

        while (true) {
            if (!knightRandomThread.isAlive()) {
                int[] start = {generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])};
                knightRandom.reset(start);
                knightRandomThread = new Thread(knightRandom);
                knightRandomThread.start();
            }
            if (!knightThread.isAlive()) {
                int[] start = {generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])};
                knight.reset(start);
                knightThread = new Thread(knight);
                knightThread.start();
            }
        }
    }
}
