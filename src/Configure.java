package src;

import akka.actor.ActorRef;

public class Configure {
    private final String filePath, pattern;
    private final ActorRef collection;

    public Configure(String filePath, String pattern, ActorRef collection) {
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

    public String getPattern() {
        return pattern;
    }
}
