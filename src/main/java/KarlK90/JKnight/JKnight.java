package KarlK90.JKnight;

import KarlK90.JKnight.Helpers.BoardPrinter;
import KarlK90.JKnight.Helpers.ICallableListener;
import KarlK90.JKnight.Helpers.KnightBuilder;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static KarlK90.JKnight.Helpers.BoardPrinter.*;

public class JKnight implements ICallableListener {

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

    @Parameter(names = {"--tries"}, description = "Number of attempted tries, -1 = unlimited", required = false)
    private int tries = -1;

    @Parameter(names = {"--method"}, description = "Solving algorithm: warnsdorf, backtrack, backtrack-random", required = true )
    private String method;

    @Parameter(names = "--help",description = "Display Help", help = true)
    private boolean help;

    private Random generator = new Random();

    private ExecutorService executor = Executors.newCachedThreadPool();

    private int activeThreads = 0;

    private BoardPrinter printer = new BoardPrinter();

    public static void main(String[] args){
        printMOTD();
        JKnight jKnight = new JKnight();
        jKnight.init(args);
    }

    private static void printMOTD() {
        System.out.println(ANSI_RED + " _____     __  __                                __          __      \n" +
                "/\\___ \\   /\\ \\/\\ \\              __              /\\ \\        /\\ \\__   \n" +
                "\\/__/\\ \\  \\ \\ \\/'/'     ___    /\\_\\      __     \\ \\ \\___    \\ \\ ,_\\  \n" +
                "   _\\ \\ \\  \\ \\ , <    /' _ `\\  \\/\\ \\   /'_ `\\    \\ \\  _ `\\   \\ \\ \\/  \n" +
                "  /\\ \\_\\ \\  \\ \\ \\\\`\\  /\\ \\/\\ \\  \\ \\ \\ /\\ \\L\\ \\    \\ \\ \\ \\ \\   \\ \\ \\_ \n" +
                "  \\ \\____/   \\ \\_\\ \\_\\\\ \\_\\ \\_\\  \\ \\_\\\\ \\____ \\    \\ \\_\\ \\_\\   \\ \\__\\\n" +
                "   \\/___/     \\/_/\\/_/ \\/_/\\/_/   \\/_/ \\/___L\\ \\    \\/_/\\/_/    \\/__/\n" +
                "                                         /\\____/                     \n" +
                "                                         \\_/__/                      \n"+ANSI_RESET);
        System.out.println("Version 0.1 - MIT License - Copyright (c) 2016 Stefan Kerkmann\n");
    }

    private void init(String[] args){
        JCommander jCommander = new JCommander(this);
        jCommander.setProgramName("JKnight");

        try {
            jCommander.parse(args);
        }catch (Exception e){
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET+"\n");
            jCommander.usage();
        }

        if (help){
            jCommander.usage();
        }else {
            parseListArguments();
            startCompleteableFuture();
        }
    }

    @Override
    public void callableFinished(){
       /* if (tries == -1) newKnightThread();
        if (tries > 1) newKnightThread();
        if (tries == 0 ) executor.shutdown();*/
    }

    private void startCompleteableFuture(){
        CompletableFuture.supplyAsync(KnightBuilder.newFutureKnight(KnightBuilder.convert(method), boardDimension, getNewStartPosition(), this))
                .thenAccept(printer.setup());
    }


    private void startThreads(){
        // if more threads then tries are requested, only start as many threads that requested tries are calculated
        threads = threads > tries && tries != -1 ? tries : threads;

        for (int i = 0; i < threads; i++) {
            // newKnightThread();
        }
    }

    /*private Future newKnightThread(){
        if(tries != -1) tries--;
        return executor.submit(KnightBuilder.newFutureKnight(KnightBuilder.convert(method), boardDimension, getNewStartPosition(), this));
    }*/

    private void parseListArguments(){
        startPosition = parseToIntArray(sStartPosition);
        boardDimension = parseToIntArray(sBoardDimension);
    }

    private int[] parseToIntArray(final List<String> stringList){
        if (stringList != null){
        int[] intArray = new int[stringList.size()];
        for(int i=0 ; i<stringList.size(); i++){
            intArray[i] = Integer.parseInt(stringList.get(i));
        }
        return intArray;}
        return null;
    }

    private int[] getNewStartPosition(){
        if (randomStart && startPosition == null){
            return new int[]{generator.nextInt(boardDimension[0]), generator.nextInt(boardDimension[1])};
        }else {
            return startPosition;
        }
    }
}
