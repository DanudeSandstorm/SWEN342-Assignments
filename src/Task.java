package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class Task implements Callable<Found> {

    private String fileName;
    private String pattern;

    public Task(String pattern, String fileName) {
        this.pattern = pattern;
        this.fileName = fileName;
    }

    public Found call() {
        Found found;
        try {
            BufferedReader br =
                    new BufferedReader(new FileReader(fileName));
            try {
                found = read(br);
            } catch (IOException e ) {
                found = new Found("Error");
            }
        } catch (FileNotFoundException e) {
            System.err.format(
                    "Exception occurred trying to read '%s'.",
                    fileName);
            found = new Found("Error");
        }
        return found;
    }

    /**
     *  Takes a buffered reader and checks each line for matches
     * @param reader A BufferedReader
     * @return a found object
     */
    protected Found read(BufferedReader reader) throws IOException {
        Found found = new Found(fileName);

        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            if (checkMatch(line)) {
                found.addMatch(lineNumber, line);
            }
            lineNumber++;
        }

        return found;
    }

    /**
     * checks a line for matches against the pattern
     * @param line a string
     * @return true if a match is found, else false
     */
    private boolean checkMatch(String line) {
        //TODO
        return false;
    }

}
