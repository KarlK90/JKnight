package KarlK90.JKnight;

import java.util.Arrays;

public class BoardPrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final int THRESHOLD = 10000; // Threshold for switching from ms to s

    public BoardPrinter(String method, int[] startPosition, int[] boardDimension, BoardStopWatch watch) {
        setup(method, startPosition, boardDimension, watch);
    }

    String method, message, columnString;
    int[] startPosition;
    int[] boardDimension;
    BoardStopWatch watch;

    public void print(BoardPrinter printer) {
        System.out.println(printer.message + "\n");
    }

    public void printSolutionFound(int[][] board) {
        print(newMessage().solutionFound().method().startPosition().board());
        printBoard(board);
        print(newMessage().runtime());
    }

    public void printNoSolutionFound() {
        print(newMessage().noSolutionFound().method().badStartPosition().board());
        print(newMessage().runtime());
    }

    public void setup(String method, int[] startPosition, int[] boardDimension, BoardStopWatch watch) {
        this.method = method;
        this.startPosition = startPosition;
        this.watch = watch;
        this.boardDimension = boardDimension;
        this.columnString = CalculateColumnString(boardDimension[0]*boardDimension[1]);
    }

    private BoardPrinter runtime() {
        String time;
        time = watch.getTime() > THRESHOLD ? Long.toString(watch.getTimeInSecounds()) + "s" : Long.toString(watch.getTime()) + "ms";
        message += ANSI_GREEN + "Runtime: " + ANSI_YELLOW + time;
        return this;
    }

    private String CalculateColumnString(int fieldCount){
            char[] array = new char[String.valueOf(fieldCount).length()];
            Arrays.fill(array, ' ');
            return new String(array);
    }

    private void printBoard(int[][] board) {
        System.out.print(ANSI_PURPLE);
        for (int[] row : board) {
            for (int column : row) {
                String out = String.valueOf(column);
                System.out.print(columnString.substring(out.length()) + out + " ");
            }
            System.out.println();
        }
        System.out.println(ANSI_WHITE);
    }

    private BoardPrinter solutionFound() {
        message += ANSI_GREEN + "Solution! ";
        return this;
    }

    private BoardPrinter noSolutionFound() {
        message += ANSI_RED + "No Solution! ";
        return this;
    }

    private BoardPrinter method() {
        message += ANSI_WHITE + "Method: " + method;
        return this;
    }

    private BoardPrinter startPosition() {
        message += " - Start: " + ANSI_GREEN + startPosition[1] + " " + startPosition[0];
        return this;
    }

    private BoardPrinter badStartPosition() {
        message += " - Start: " + ANSI_RED + startPosition[1] + " " + startPosition[0];
        return this;
    }

    private BoardPrinter board() {
        message += ANSI_WHITE + " Board: " + ANSI_YELLOW + boardDimension[0] + "x" + boardDimension[1];
        return this;
    }

    private BoardPrinter newMessage() {
        message = "";
        return this;
    }

}
