package JKnight;

public class JKnight {

    public static void main(String[] args) {
        int[] BoardDimension = {8, 8};
        int[] StartPosition = {4, 4};

        BacktrackKnight Knight = new BacktrackKnight(BoardDimension, StartPosition);

        System.out.println("Alles Initialisiert!");

        Knight.Solve();

    }
}
