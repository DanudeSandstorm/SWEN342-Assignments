package src;
import java.lang.*;
import java.util.*;

public class Bridge {

	private int bridgeCount;
	private Queue<Woolie> queue;

	public Bridge() {
		bridgeCount = 0;
		queue = new LinkedList<Woolie>();
	}

	public void enterBridge(Woolie woolie) {
		synchronized(woolie) {
			bridgeCount++;

			if (bridgeCount > 3) {
				//add to queue
				queue.add(woolie);
				//make the woolie wait
				try { woolie.wait(); }
				catch(InterruptedException e) {}
			}
		}
	}

	public void leaveBridge() {
		bridgeCount--;
		if (queue.peek() != null) {
			//Allow next woolie in queue
			Woolie woolie = queue.remove();

			synchronized (woolie) {
				woolie.notify();
			}
		}
	}
}
