package src;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Found {

    protected String fileName;
    protected ArrayList<String> matches;

    public Found(String fileName) {
        this.fileName = fileName;
        matches = new ArrayList<String>();
    }

    // Each string in the list consists of the line number,
    // a space, and the text of the line itself.
    public void addMatch(int position, String line) {

    }

    public String getName() {
        return fileName;
    }

    public ArrayList<String> getMatches() {
        return matches;
    }
}
