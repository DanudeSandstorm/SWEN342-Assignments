package src;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class CGrep {

    private ArrayList<String> fileList = new ArrayList<String>();

	public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("Not enough arguments");

        final ExecutorService es = Executors.newFixedThreadPool(3);

        //Use standard input
        if (args.length == 1) {
            try {
                standardInput();
            }
            catch (Exception e) {}
        }
        //else
        //create a "callable" task for each file


	}

	private static void standardInput() throws Exception {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        String s;
        while ((s = br.readLine()) != null) {
            //TODO
        }
        isr.close();
    }

}
