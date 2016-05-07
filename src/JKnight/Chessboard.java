package JKnight;

/**
 * Created by KarlK.
 */
public class Chessboard{

    public boolean[][] Field;

    Chessboard(int[] BoardDimension) {
        this.Field = new boolean[BoardDimension[0]][BoardDimension[1]];
    }

}
