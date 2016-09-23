package src;
import java.lang.*;
import java.util.*;

public class Fork {

	private boolean isheld;

	public Fork() {
		isheld = false;
	}

    /*
     * A philosopher (attempts to) acquire the fork.
     */
    public synchronized void acquire() {
    	while (isheld) {
    		try {
    			wait();
    		}
    		catch(InterruptedException e) {}
    	}
    	isheld = true;
    }

    /*
     * A philosopher releases the fork.
     */
    public synchronized void release() {
    	isheld = false;
		notifyAll();
    }
}

