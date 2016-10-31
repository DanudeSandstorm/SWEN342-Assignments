package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.*;

public class CGrep {

	public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Not enough arguments");
            return;
        }

        final ExecutorService pool = Executors.newFixedThreadPool(3);
        final ExecutorCompletionService<Found> completionService =
                new ExecutorCompletionService<>(pool);

        // If no files given, use standard input
        if (args.length == 1) {
            completionService.submit(standardInput());
        }
        else {
            //create a "callable" task for each file
            //starts at index 2 of the args array
            for (int i = 2; i <= args.length; i++) {
                Task task = new Task(args[i]);
                completionService.submit(task);
            }
        }

        //Gets all Found promises (future objects)
        for (int i = 1; i <= args.length; i++) {
            try {
                final Future<Found> future = completionService.take();
                print(future);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (ExecutionException e) {
                System.out.println(e.getMessage());
            }
        }
	}

	private static void print(Future<Found> future)
            throws ExecutionException, InterruptedException {

        final Found found = future.get();
        System.out.println("Matches for: " + found.getName());
        ArrayList<String> matches = found.getMatches();
        for (int i = 0; i < matches.size(); i++) {
            //TODO
        }
    }

	private static Task standardInput() {
        return new Task() {
            @Override
            public Found call() {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                Found found = new Found("Standard Input");

                String s;
                try {
                    while ((s = br.readLine()) != null) {
                        //TODO
                    }
                    isr.close();
                } catch (IOException e) {}

                return found;
            }
        };
    }

}
