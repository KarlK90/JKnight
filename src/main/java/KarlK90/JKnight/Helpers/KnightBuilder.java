package KarlK90.JKnight.Helpers;

import KarlK90.JKnight.Knights.*;

import java.util.function.Supplier;

public class KnightBuilder {

    public static Supplier<Result> newFutureKnight(BaseKnight knight, int[] boardDimension, int[] startPosition){
        knight.setup(boardDimension,startPosition);
        return knight;
    }

    public static BaseKnight convert(String algorithm) {
        switch (algorithm.toLowerCase()){
            case "warnsdorf":
                return new WarnsdorfKnight();
            case "backtracking":
                return new BacktrackKnight();
            case "backtracking-random":
                return new BacktrackKnightRandom();
            default:
                throw new IllegalArgumentException("Unknown Algorithm" + algorithm);
        }
    }
}
