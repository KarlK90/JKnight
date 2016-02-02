package JKnight;

public class JKnight {

    public static void main(String[] args) {
         int[] aBoardDimension = {8,8};
        int[] aStartPosition = {4,4};

        BacktrackKnight Knight = new BacktrackKnight(aBoardDimension, aStartPosition);

        System.out.println("Alles Initialisiert!");

        Knight.Jump();

    }
}
