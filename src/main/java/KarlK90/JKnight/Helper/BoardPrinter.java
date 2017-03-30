package KarlK90.JKnight.Helper;

import KarlK90.JKnight.Models.Result;
import KarlK90.JKnight.Models.Point;

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

    private String method, message, columnString;
    private Point startPosition;
    private Point boardDimension;
    private BoardStopWatch watch;
    private Long jumpCounter;

    public synchronized void printResult(Result result) {
        setup(result.method, result.startPosition, result.boardDimension, result.watch, result.jumpCounter);
        if (result.isSolution) {
            printSolutionFound(result.chessboard);
        } else {
            printNoSolutionFound();
        }
    }

    private void printSolutionFound(Integer[][] board) {
        print(newMessage().solutionFound().method().startPosition().board());
        printBoard(board);
        print(newMessage().runtime());
        print(newMessage().jumps());
    }

    private void printNoSolutionFound() {
        print(newMessage().noSolutionFound().method().badStartPosition().board());
        print(newMessage().runtime());
        print(newMessage().jumps());
    }

    private void setup(String method, Point startPosition, Point boardDimension, BoardStopWatch watch, Long jumps) {
        this.method = method;
        this.startPosition = startPosition;
        this.boardDimension = boardDimension;
        this.watch = watch;
        this.columnString = CalculateColumnString(boardDimension.x * boardDimension.y);
        this.jumpCounter = jumps;
    }

    private void print(BoardPrinter printer) {
        synchronized (System.out) {
            System.out.println(printer.message + "\n");
        }
    }

    private BoardPrinter runtime() {
        message += ANSI_GREEN + "Runtime: " + ANSI_YELLOW + watch.getElapsedTime() + " ";
        return this;
    }

    private BoardPrinter jumps(){
        message += ANSI_GREEN + "Jumps: " + ANSI_YELLOW + jumpCounter;
        return this;
    }

    private String CalculateColumnString(int fieldCount) {
        char[] array = new char[String.valueOf(fieldCount).length()];
        Arrays.fill(array, ' ');
        return new String(array);
    }

    private void printBoard(Integer[][] board) {
        synchronized (System.out) {
            final int margin = (board.length - boardDimension.x) / 2;
            System.out.print(ANSI_PURPLE);
            for (int r = margin; r < board.length - margin; r++) {
                Integer[] row = board[r];
                for (int c = margin; c < row.length - margin; c++) {
                    int column = row[c];
                    String out = String.valueOf(column);
                    System.out.print(columnString.substring(out.length()) + out + " ");
                }
                System.out.println();
            }
            System.out.println(ANSI_WHITE);
        }
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
        message += " - Start: " + ANSI_GREEN + startPosition.y + " " + startPosition.x;
        return this;
    }

    private BoardPrinter badStartPosition() {
        message += " - Start: " + ANSI_RED + startPosition.y + " " + startPosition.x;
        return this;
    }

    private BoardPrinter board() {
        message += ANSI_WHITE + " Board: " + ANSI_YELLOW + boardDimension.x + "x" + boardDimension.y;
        return this;
    }

    private BoardPrinter newMessage() {
        message = "";
        return this;
    }
}
