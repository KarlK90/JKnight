package KarlK90.JKnight.Knights;

public class KnightBuilder {
    public static Thread getThreaded(BaseKnight knight){
        return new Thread(knight);
    }
}
