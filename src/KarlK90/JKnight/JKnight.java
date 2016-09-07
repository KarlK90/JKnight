package KarlK90.JKnight;

import KarlK90.JKnight.Knights.BacktrackKnight;
import KarlK90.JKnight.Knights.BacktrackKnightRandom;
import KarlK90.JKnight.Knights.KnightBuilder;
import KarlK90.JKnight.Knights.WarnsdorfKnight;

import java.util.Random;

public class JKnight {

    public static void main(String[] args) {
        int[] boardDimension = {32, 32};
        int[] startPosition = {0, 0};
        Random generator = new Random();

        Thread knightThread = KnightBuilder.getThreaded(new BacktrackKnight(boardDimension, startPosition));
        Thread knightRandomThread = KnightBuilder.getThreaded(new BacktrackKnightRandom(boardDimension, startPosition));
        Thread knightWarnsdorfThread = KnightBuilder.getThreaded(new WarnsdorfKnight(boardDimension, startPosition));

        knightThread.start();
        knightRandomThread.start();
        knightWarnsdorfThread.start();

        while (true) {
            if (!knightRandomThread.isAlive()) {
                knightRandomThread = KnightBuilder.getThreaded(
                        new BacktrackKnightRandom(boardDimension,
                        new int[]{generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])}
                        ));
                knightRandomThread.start();
            }
            if (!knightThread.isAlive()) {
                knightThread = KnightBuilder.getThreaded(
                        new BacktrackKnight(boardDimension,
                                new int[]{generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])}
                        ));
                knightThread.start();
            }
            if (!knightWarnsdorfThread.isAlive()) {
                knightWarnsdorfThread = KnightBuilder.getThreaded(
                        new WarnsdorfKnight(boardDimension,
                                new int[]{generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])}
                        ));
                knightWarnsdorfThread.start();
            }
        }
    }


}
