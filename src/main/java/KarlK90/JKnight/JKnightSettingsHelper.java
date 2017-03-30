package KarlK90.JKnight;

import KarlK90.JKnight.Models.Point;

import java.util.List;

public class JKnightSettingsHelper{
    public static Point ParseToPoint(final List<String> stringList) {
        if (stringList != null && stringList.size() >= 2) {
            return new Point(
                    Integer.parseInt(stringList.get(0)),
                    Integer.parseInt(stringList.get(1))
            );
        }
        return null;
    }
}
