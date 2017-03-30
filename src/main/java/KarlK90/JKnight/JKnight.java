package KarlK90.JKnight;

import KarlK90.JKnight.Helper.BoardPrinter;
import KarlK90.JKnight.Knights.KnightBuilder;
import com.beust.jcommander.JCommander;

import java.util.concurrent.*;

import static KarlK90.JKnight.Helper.BoardPrinter.*;

public class JKnight {
    private BoardPrinter printer = new BoardPrinter();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private JKnightSettings settings;

    public static void main(String[] args) {
        printMOTD();
        JKnight jKnight = new JKnight();
        if (jKnight.init(args)){
            jKnight.startThreads(jKnight.settings);
        }
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
        System.out.println("Version 0.2 - MIT License - Copyright (c) 2017 Stefan Kerkmann\n");
    }

    private boolean init(String[] args) {
        settings = new JKnightSettings();
        JCommander jCommander = new JCommander(settings);
        jCommander.setProgramName("JKnight");

        try {
            jCommander.parse(args);
        } catch (Exception e) {
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET + "\n");
            jCommander.usage();
            return false;
        }

        if (settings.help) {
            jCommander.usage();
            return false;
        }

        return true;
    }

    private void startThreads(JKnightSettings settings) {
        // if more threads then solutions are requested, only start as many threads as needed to fulfill request
        settings.threads = (settings.threads > settings.solutions && settings.solutions != -1) ? settings.solutions : settings.threads;

        for (int i = 0; i < settings.threads; i++) {
            startKnight(settings);
        }
    }

    private void startKnight(JKnightSettings settings) {
        CompletableFuture.supplyAsync(KnightBuilder.Instance(settings).build(), executor)
                .exceptionally(e -> {
                    System.out.println("Exception occurred while solving!" + e.getLocalizedMessage());
                    e.printStackTrace();
                    return null;
                })
                .thenAccept(result -> {
                    printer.printResult(result);
                    if (settings.solutions == -1) {
                        startKnight(settings);
                    } else {
                        if (settings.solutions > 0 && result.isSolution) {
                            settings.solutions--;
                            if (settings.solutions >= settings.threads) {
                                startKnight(settings);
                            } else {
                                settings.threads--;
                            }
                        }
                        if (settings.solutions > 0 && !result.isSolution) {
                            startKnight(settings);
                        }
                        if (settings.solutions <= 0) executor.shutdown();
                    }
                });
    }
}
