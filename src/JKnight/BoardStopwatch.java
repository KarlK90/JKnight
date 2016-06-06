package JKnight;

public class BoardStopWatch {

    private long start = 0;
    private long end = 0;

    public void reset(){
        start = 0;
        end = 0;
    }

    public BoardStopWatch start(){
        start = System.currentTimeMillis();
        return this;
    }

    public BoardStopWatch stop(){
        end = System.currentTimeMillis();
        return this;
    }

    public long getTime(){
        return end - start;
    }

    public long getTimeInSecounds(){
        return (end - start) / 1000;
    }
}
