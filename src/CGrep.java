package src;

import akka.actor.ActorRef;
import akka.actor.Actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CGrep {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Not enough arguments");
            System.exit(1);
        }
        String pattern = args[0];

        // Create and start a CollectionActor
        ActorRef collectionActor =
                Actors.actorOf(CollectionActor.class).start();

        //Standard Input
        if (args.length == 1) {
            collectionActor.tell(1);

            ActorRef scanActor = Actors.actorOf(ScanActor.class).start();
            scanActor.tell(new Configure("-", pattern, collectionActor));
        }
        //List of files
        else {
            List<String> files = new ArrayList<>(Arrays.asList(args));
            files.remove(0); //Remove regex argument

            FileCount filecount = new FileCount(files.size());
            collectionActor.tell(filecount);

            for (String file : files) {
                ActorRef scanActor = Actors.actorOf(ScanActor.class).start();
                scanActor.tell(new Configure(file, pattern, collectionActor));
            }
        }
    }
}
