package src;
import java.util.concurrent.Callable;

public class Task implements Callable<Found> {

    protected String fileName;

    public Task() {
        fileName = null;
    }

    public Task(String fileName) {
        this.fileName = fileName;
    }

    public Found call(){
        Found found = new Found(fileName);

        return found;
    }

}
