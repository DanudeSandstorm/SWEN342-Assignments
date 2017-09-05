package src;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ScanActor extends UntypedActor {
    private boolean configured;
    private String filepath;
    private Pattern pattern;

    public ScanActor() {
        configured = false;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Configure) {
            if (configured) {
                throw new RuntimeException("Actor is already configured");
            }
            configure((Configure) message);
        }

    }

    /**
     * Configures the ScanActor and tells the collection about the found object
     * @param config A configuration object
     */
    private void configure(Configure config) {
        configured = true;

        ActorRef collection = config.getCollectionActor();
        filepath = config.getFilePath();
        pattern = config.getPattern();

        collection.tell(new Found(filepath, read()));
    }

    /**
     * If the filename is -, reads standard input as a BufferedReader
     * Else loads the specified file into a BufferedReader
     * @return A list of matches
     */
    private List<String> read() {
        List<String> matches = new ArrayList<>();

        //Standard Input
        if (filepath.equals("-")) {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
             try {
                matches = findMatches(br);
                isr.close();
            } catch (IOException e) {
                 System.err.print("Exception occurred while trying to read from standard input");
             }
        }
        //File
        else {
            try {
                BufferedReader br =
                        new BufferedReader(new FileReader(filepath));
                try {
                    matches = findMatches(br);
                } catch (IOException e ) {
                    System.err.format(
                            "Exception occurred trying to read '%s'.",
                            filepath);
                }
            } catch (FileNotFoundException e) {
                System.err.format("'%s' is not a valid file.", filepath);
            }
        }

        return matches;
    }

    /**
     * Reads a BufferedReader input and calls checkMatch() for each line
     * @param reader A BufferedReader
     * @return A list of matches
     */
    private List<String> findMatches(BufferedReader reader) throws IOException {
        List<String> matches = new ArrayList<>();

        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            if (checkMatch(line)) {
                matches.add(lineNumber + " " + line);
            }
            lineNumber++;
        }
        reader.close();

        return matches;
    }

    /**
     * Checks a line for matches against the pattern
     * @param line a string to check for matches against pattern
     * @return true if a match is found, else false
     */
    private boolean checkMatch(String line) {
        Matcher m = pattern.matcher(line);
        return m.find();
    }
}
