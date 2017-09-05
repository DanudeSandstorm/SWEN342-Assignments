package src;

import akka.actor.ActorRef;
import java.util.regex.Pattern;

class Configure {
    private final String filePath;
    private final ActorRef collection;
    private final Pattern pattern;

    Configure(String filePath, Pattern pattern, ActorRef collection) {
        this.filePath = filePath;
        this.pattern = pattern;
        this.collection = collection;
    }

    String getFilePath() {
        return filePath;
    }

    ActorRef getCollectionActor() {
        return collection;
    }

    Pattern getPattern() {
        return pattern;
    }
}
