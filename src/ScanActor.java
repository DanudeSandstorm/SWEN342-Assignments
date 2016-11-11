package src;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import java.util.regex.*;

public class ScanActor extends UntypedActor {
    private ActorRef collection;
    private String filePath;
    private final String regex;
    private boolean configured;

    public ScanActor(String regex) {
        this.regex = regex;
        configured = false;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Configure) {
            if (configured) {
                throw new RuntimeException("Actor is already configured");
            }
            configure((Configure) message);
        }

    }

    private void configure(Configure config) {
        configured = true;

        collection = config.getCollectionActor();
        filePath = config.getFilePath();

        //Found found = new Found(filePath, findMatches());
        //collection.tell(found);
    }

    private void findMatches() {}

    private void readFile() {}

}
