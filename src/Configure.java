package src;

import akka.actor.ActorRef;
import java.util.regex.Pattern;

public class Configure {
    private final String filePath;
    private final ActorRef collection;
    private final Pattern pattern;

    public Configure(String filePath, Pattern pattern, ActorRef collection) {
        this.filePath = filePath;
        this.pattern = pattern;
        this.collection = collection;
    }

    public String getFilePath() {
        return filePath;
    }

    public ActorRef getCollectionActor() {
        return collection;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
