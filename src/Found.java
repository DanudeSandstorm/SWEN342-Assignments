package src;

import java.util.Collections;
import java.util.List;

public class Found {
    private final String fileName;
    private final List<String> matches;

    public Found(String fileName, List<String> matches) {
        this.fileName = fileName;
        this.matches = Collections.unmodifiableList(matches);
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getMatches() {
        return matches;
    }
}
