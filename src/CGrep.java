package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.*;

public class CGrep {

    final private static int poolSize = 3;

	public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Not enough arguments");
            System.exit(1);
        }

        //Constants
        final ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        final ExecutorCompletionService<Found> completionService =
                new ExecutorCompletionService<>(pool);
        final String pattern = args[0]; //First argument

        // If no files given, use standard input
        if (args.length == 1) {
            completionService.submit( standardInput(pattern) );
        }
        else {
            //create a "callable" task for each file
            //starts at index 1; second argument of the args array
            for (int i = 1; i < args.length; i++) {
                Task task = new Task(pattern, args[i]);
                completionService.submit(task);
            }
        }

        // Gets all Found promises (future objects)
        // Prints the found object
        for (int i = 1; i <= args.length; i++) {
            try {
                final Future<Found> future = completionService.take();
                print(future);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(e.getMessage());
            }
        }
	}

    /**
     * Prints the matches from a future found object
     * @param future The future object to get and print
     */
	private static void print(Future<Found> future)
            throws ExecutionException, InterruptedException {

        final Found found = future.get();
        System.out.println("Matches for: " + found.getName());
        ArrayList<String> matches = found.getMatches();
        for (String match : matches) {
            System.out.println(match);
        }
    }

    /**
     * Creates a task of a found object for standard input
     * @param pattern The regex pattern to check against
     * @return Returns a task
     */
	private static Task standardInput(String pattern) {
        return new Task(pattern, "Standard Input") {
            @Override
            public Found call() {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                Found found;
                try {
                    found = read(br);
                    isr.close();
                } catch (IOException e) {
                    found = new Found("Error");
                }
                return found;
            }
        };
    }

}
