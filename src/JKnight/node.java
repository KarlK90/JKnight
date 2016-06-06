package JKnight;

/**
 * Created by horst on 31/05/2016.
 */
public class Node implements Cloneable {

    public int id, x ,y;
    public int[] jumps = new int[8];

    Node(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        }

    protected Object clone() {
        try {
            Node clone = (Node) super.clone();
            clone.jumps = this.jumps.clone();
            return clone;
        }catch (CloneNotSupportedException e){
            return null;
        }
    }

    public boolean hasOnlyInvalidJumps(){
        for (int i : jumps){
            if (i == Knight.VALID ) return false;
        }
        return true;
    }
}
