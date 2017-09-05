package src;

import java.util.Collections;
import java.util.List;

class Found {
    private final String name;
    private final List<String> matches;

    Found(String name, List<String> matches) {
        this.name = name;
        this.matches = Collections.unmodifiableList(matches);
    }

    String getName() {
        return name;
    }

    List<String> getMatches() {
        return matches;
    }
}
