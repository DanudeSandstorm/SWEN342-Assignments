package src;

import java.util.Collections;
import java.util.List;

public class Found {
    private final String name;
    private final List<String> matches;

    public Found(String name, List<String> matches) {
        this.name = name;
        this.matches = Collections.unmodifiableList(matches);
    }

    public String getName() {
        return name;
    }

    public List<String> getMatches() {
        return matches;
    }
}
