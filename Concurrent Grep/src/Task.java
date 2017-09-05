package src;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Task implements Callable<Found> {

    private String fileName;
    private Pattern pattern;

    Task(String pattern, String fileName) {
        this.pattern = Pattern.compile(pattern);
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
                System.err.format(
                        "Exception occurred trying to read '%s'.",
                        fileName);
                found = Found.error(fileName);
            }
        } catch (FileNotFoundException e) {
            System.err.format("'%s' is not a valid file.", fileName);
            found = Found.error(fileName);
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
        reader.close();

        return found;
    }

    /**
     * checks a line for matches against the pattern
     * @param line a string to check for matches against pattern
     * @return true if a match is found, else false
     */
    private boolean checkMatch(String line) {
        Matcher m = pattern.matcher(line);
        boolean b = m.find();
        //  return pattern.matcher(line).matches();
        return b;
    }

}
