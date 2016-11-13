package src;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import java.util.List;

class CollectionActor extends UntypedActor {
    private int filesScanned;
    private int filesToScan;
    private ActorSystem actorSystem;

    public CollectionActor() {
        filesScanned = 0;
        filesToScan = 0;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FileCount) {
            if (filesToScan != 0) {
                throw new RuntimeException("File count has already been received");
            }
            setFileCount((FileCount) message);
        }

        if (message instanceof  Found) {
            onFound((Found) message);
        }
    }

    /**
     * The first, of class FileCount (which should be received exactly once),
     * contains a count of the number of files being scanned.
     */
    private void setFileCount(FileCount message) {
        filesToScan = message.getCount();
        actorSystem = message.getActorSystem();
    }

    /**
     * The remaining messages are Found objects, which, upon receipt,
     * are printed by the CollectionActor.
     * Printout consists of the file name (or "-" for standard input)
     * and the list of matching lines.
     */
    private void onFound(Found found) {
        List<String> matches = found.getMatches();
        if (matches.size() == 0 ) {
            System.out.println("No matches for " + found.getName());
        }
        else {
            System.out.println("Matches for: " + found.getName());
            for (String match : matches) {
                System.out.println(match);
            }
        }

        filesScanned++;

        if (filesScanned == filesToScan) {
            actorSystem.shutdown();
        }
    }


}
