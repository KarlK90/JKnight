package KarlK90.JKnight.Helpers;

import KarlK90.JKnight.Knights.*;

import java.util.function.Supplier;

public class KnightBuilder {

    public static Supplier<Result> newFutureKnight(BaseKnight knight, int[] boardDimension, int[] startPosition, ICallableListener context){
        knight.setup(boardDimension,startPosition, context);
        return knight;
    }

    public static BaseKnight convert(String value) {
        switch (value.toLowerCase()){
            case "warnsdorf":
                return new WarnsdorfKnight();
            case "backtrack":
                return new BacktrackKnight();
            case "backtrack-random":
                return new BacktrackKnightRandom();
            default:
                throw new IllegalArgumentException();
        }
    }
}
