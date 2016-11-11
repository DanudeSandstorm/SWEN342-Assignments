package src;

import akka.actor.ActorRef;

public class Configure {
    private final String filePath;
    private final ActorRef collection;

    public Configure(String filePath, ActorRef collection) {
        this.filePath = filePath;
        this.collection = collection;
    }

    public String getFilePath() {
        return filePath;
    }

    public ActorRef getCollectionActor() {
        return collection;
    }
}
