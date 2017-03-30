package KarlK90.JKnight.Helper;

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

    public long getElapsedMilliSeconds(){
        return this.stop().end - start;
    }

    public long getElapsedSeconds(){
        return getElapsedMilliSeconds() / 1000;
    }

    public String getElapsedTime(){
        long duration = getElapsedMilliSeconds();
        String result = "";

        if (duration == 0){
            return "0 ms";
        }

        long fraction = duration / (60 * 60 * 1000);
        if (fraction > 0){
            result += fraction + "h ";
            duration = duration - fraction * (60 * 60 * 1000);
        }

        fraction = duration / (60 * 1000);
        if (fraction > 0){
            result += fraction + "m ";
            duration = duration - fraction * (60 * 1000);
        }

        fraction = duration / (1000);
        if (fraction > 0){
            result += fraction + "s ";
            duration = duration - fraction * (1000);
        }

        if (duration > 0){
            result += duration + "ms ";
        }

        return result;
    }
}
