package src;
import java.util.concurrent.locks.ReentrantLock;

public class Bridge {

	private final ReentrantLock troll = new ReentrantLock();

	public Bridge() {}

	public void enterBridge() {
 		troll.lock();
	}

	public void leaveBridge() {
		troll.unlock();
	}
}
