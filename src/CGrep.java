package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        //Use standard input
        if (args.length == 1) {
            try {
                Task task = standardInput();
                completionService.submit(task);
            }
            catch (IOException e) {}
        }
        else {
            //create a "callable" task for each file
            //starts at index 2
            for (int i = 2; i <= args.length; i++) {
                //Some for loop
                Task task = new Task();
                completionService.submit(task);
            }
        }

        for (int i = 1; i <= args.length; i++) {
            try {
                final Future<Found> future = completionService.take();
                try {
                    final Found found = future.get();
                } catch (ExecutionException e) {}
            } catch (InterruptedException e) {}
        }
	}

	private static Task standardInput() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        String s;
        while ((s = br.readLine()) != null) {
            //TODO
        }
        isr.close();

        return new Task();
    }

}
