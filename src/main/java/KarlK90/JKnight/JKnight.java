package KarlK90.JKnight;

import KarlK90.JKnight.Helpers.BoardPrinter;
import KarlK90.JKnight.Helpers.KnightBuilder;
import KarlK90.JKnight.Knights.Result;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static KarlK90.JKnight.Helpers.BoardPrinter.*;

public class JKnight {

    @Parameter(names = {"--threads"}, description = "Number of knights and threads", required = false)
    private int threads = 1;

    @Parameter(names = {"--board"}, arity = 2, description = "Rows and columns of the board: e.g. --board 8 8", required = true)
    private List<String> sBoardDimension;
    private int[] boardDimension;

    @Parameter(names = {"--start"}, arity = 2, description = "Start position of the knight: e.g. --start 4 4", required = false)
    private List<String> sStartPosition;
    private int[] startPosition;

    @Parameter(names = {"--random-start"}, arity = 1, required = false, description = "Generates random start positions for each knight")
    private boolean randomStart = true;

    @Parameter(names = {"--solutions"}, description = "Number of attempted solutions, -1 = unlimited", required = false)
    private int solutions = -1;
    private boolean infiniteSolutions = true;

    @Parameter(names = {"--method"}, description = "Solving algorithm: warnsdorf, backtracking, backtracking-random", required = true)
    private String method;

    @Parameter(names = "--help", description = "Display Help", help = true)
    private boolean help;

    private Random generator = new Random();
    private BoardPrinter printer = new BoardPrinter();
    private ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        printMOTD();
        JKnight jKnight = new JKnight();
        jKnight.init(args);
    }

    private static void printMOTD() {
        System.out.println(ANSI_RED +
                " _____  __  __                      __      __      \n" +
                "/\\___ \\/\\ \\/\\ \\          __        /\\ \\    /\\ \\__   \n" +
                "\\/__/\\ \\ \\ \\/'/'    ___ /\\_\\     __\\ \\ \\___\\ \\ ,_\\  \n" +
                "   _\\ \\ \\ \\ , <   /' _ `\\/\\ \\  /'_ `\\ \\  _ `\\ \\ \\/  \n" +
                "  /\\ \\_\\ \\ \\ \\\\`\\ /\\ \\/\\ \\ \\ \\/\\ \\L\\ \\ \\ \\ \\ \\ \\ \\_ \n" +
                "  \\ \\____/\\ \\_\\ \\_\\ \\_\\ \\_\\ \\_\\ \\____ \\ \\_\\ \\_\\ \\__\\\n" +
                "   \\/___/  \\/_/\\/_/\\/_/\\/_/\\/_/\\/___L\\ \\/_/\\/_/\\/__/\n" +
                "                                 /\\____/            \n" +
                "                                 \\_/__/             \n" + ANSI_RESET);
        System.out.println("Version 0.1 - MIT License - Copyright (c) 2016 Stefan Kerkmann\n");
    }

    private void init(String[] args) {
        JCommander jCommander = new JCommander(this);
        jCommander.setProgramName("JKnight");

        try {
            jCommander.parse(args);
        } catch (Exception e) {
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET + "\n");
            jCommander.usage();
        }

        if (help) {
            jCommander.usage();
        } else {
            if (solutions > 0) infiniteSolutions = false;
            parseListArguments();
            startThreads();
        }
    }

    private void startThreads() {
        // if more threads then solutions are requested, only start as many threads as needed to fulfill request
        threads = threads > solutions && !infiniteSolutions ? solutions : threads;

        for (int i = 0; i < threads; i++) {
            startKnight();
        }
    }

    private void startKnight() {
        CompletableFuture.supplyAsync(buildKnight(), executor)
                .exceptionally(e -> {
                    System.out.println("Exception occurred while solving!"+e.getLocalizedMessage());
                    return null;
                })
                .thenAccept(result -> {
                    printer.printResult(result);
                    if (infiniteSolutions) {
                        startKnight();
                    } else {
                        if (solutions > 0 && result.solution) {
                            solutions--;
                            if (solutions + 1 > threads) {
                                startKnight();
                            } else {
                                threads--;
                            }
                        }
                        if (solutions > 0 && !result.solution) {
                            startKnight();
                        }
                        if (solutions <= 0) executor.shutdown();
                    }
                });
    }

    private Supplier<Result> buildKnight() {
        return KnightBuilder.newFutureKnight(KnightBuilder.convert(method), boardDimension, getNewStartPosition());
    }

    private int[] getNewStartPosition() {
        if (randomStart && startPosition == null) {
            return new int[]{generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])};
        } else {
            return startPosition;
        }
    }

    private void parseListArguments() {
        startPosition = parseToIntArray(sStartPosition);
        boardDimension = parseToIntArray(sBoardDimension);
    }

    private int[] parseToIntArray(final List<String> stringList) {
        if (stringList != null) {
            int[] intArray = new int[stringList.size()];
            for (int i = 0; i < stringList.size(); i++) {
                intArray[i] = Integer.parseInt(stringList.get(i));
            }
            return intArray;
        }
        return null;
    }
}
