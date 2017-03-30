package KarlK90.JKnight;

import KarlK90.JKnight.Models.Point;
import com.beust.jcommander.Parameter;

import java.util.List;

import static KarlK90.JKnight.JKnightSettingsHelper.ParseToPoint;

public class JKnightSettings {
    @Parameter(names = {"--threads"}, description = "Number of knights and threads", required = false)
    public int threads = 1;

    @Parameter(names = {"--board"}, arity = 2, description = "Rows and columns of the board: e.g. --board 8 8", required = false)
    private List<String> sBoardDimension;
    private Point boardDimension;

    @Parameter(names = {"--start"}, arity = 2, description = "Start position of the knight: e.g. --start 4 4", required = false)
    private List<String> sStartPosition;
    private Point startPosition;

    @Parameter(names = {"--solutions"}, description = "Number of attempted solutions, -1 = unlimited", required = false)
    public int solutions = -1;

    @Parameter(names = {"--algorithm"}, description = "Solving algorithm: warnsdorf, backtracking, backtracking-random", required = false)
    public String algorithm = "warnsdorf";

    @Parameter(names = "--help", description = "Display Help", help = true)
    public boolean help;

    public Point getStartPoint(){
        if (startPosition != null) return startPosition;

        startPosition = ParseToPoint(sStartPosition);
        return startPosition;
    }

    public void setStartPoint(Point start){
        startPosition = start;
    }

    public Point getBoardDimension(){
        if (boardDimension != null) return boardDimension;

        boardDimension = ParseToPoint(sBoardDimension);

        // enforce default build values
        if (boardDimension == null){
            boardDimension = new Point(8, 8);
        }

        return boardDimension;
    }

    public void setBoardDimension(Point dimension){
        boardDimension = dimension;
    }
}

