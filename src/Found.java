package src;

import java.util.ArrayList;

public class Found {

    private String fileName;
    private ArrayList<String> matches;

    public Found(String fileName) {
        this.fileName = fileName;
        matches = new ArrayList<>();
    }

    public static Found error(String fileName) {
        Found found = new Found(fileName);
        found.addMatch(-1, "Encountered an error");
        return found;
    }

    // Each string in the list consists of the line number,
    // a space, and the text of the line itself.
    public void addMatch(int position, String line) {
        matches.add(position + " " + line);
    }

    public String getName() {
        return fileName;
    }

    public ArrayList<String> getMatches() {
        return matches;
    }
}
