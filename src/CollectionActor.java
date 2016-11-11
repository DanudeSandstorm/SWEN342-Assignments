package src;

import akka.actor.UntypedActor;

import java.io.File;

public class CollectionActor extends UntypedActor {
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

    private void setFileCount(FileCount message) {
        filesToScan = message.getCount();
    }

    private void onFound(Found found) {}

}
