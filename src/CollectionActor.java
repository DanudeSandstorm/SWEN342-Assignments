package src;

import akka.actor.Actors;
import akka.actor.UntypedActor;
import java.util.List;

@SuppressWarnings("ALL")
class CollectionActor extends UntypedActor {
    private int filesScanned;
    private int filesToScan;

    public CollectionActor() {
        filesScanned = 0;
        filesToScan = 0;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FileCount) {
            if (filesScanned == 0 && filesToScan == 0) {
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

        // When all the Found messages have been processed, the CollectionActor
        // shuts down all actors using Actors.registry().shutdown()
        if (filesScanned == filesToScan) {
            Actors.registry().shutdownAll();
        }
    }


}
