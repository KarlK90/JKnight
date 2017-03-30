package KarlK90.JKnight.Knights;

import KarlK90.JKnight.JKnightSettings;
import KarlK90.JKnight.Models.Point;
import KarlK90.JKnight.Models.Result;

import java.util.Random;
import java.util.function.Supplier;

public class KnightBuilder {
    private static KnightBuilder instance = new KnightBuilder();
    private static JKnightSettings settings;
    private Random generator = new Random();

    private KnightBuilder() {
    }

    public static KnightBuilder Instance(JKnightSettings settings) {
        KnightBuilder.settings = settings;
        return instance;
    }

    public KnightBuilder Instance() {
        return instance;
    }

    public Supplier<Result> build() {
        JKnightSettings buildSettings = settings;
        if (buildSettings == null) {
            buildSettings = new JKnightSettings();
        }
        BaseKnight knight = convert(settings.algorithm);
        knight.setup(buildSettings.getBoardDimension(), getNewStartPosition(buildSettings));
        return knight;
    }

    public KnightBuilder setStartPosition(Point start) {
        settings.setStartPoint(start);
        return instance;
    }

    public KnightBuilder setBoardDimension(Point dimension) {
        settings.setBoardDimension(dimension);
        return instance;
    }

    public KnightBuilder setAlgorithm(String algorithm) {
        settings.algorithm = algorithm;
        return instance;
    }

    private static BaseKnight convert(String algorithm) {
        switch (algorithm.toLowerCase()) {
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

    private Point getNewStartPosition(JKnightSettings settings) {
        Point startPoint = settings.getStartPoint();
        if (startPoint == null) {
            Point boardDimension = settings.getBoardDimension();
            return new Point(generator.nextInt(boardDimension.x), generator.nextInt(boardDimension.y));
        } else {
            return startPoint;
        }
    }
}
