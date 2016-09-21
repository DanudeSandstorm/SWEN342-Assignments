package src;
import java.lang.*;

public class Philosopher extends Thread {

	private int id, nTimes;
	private Fork left, right;
	private boolean rHanded;
	private long thinkMillis, eatMillis;


	public Philosopher(int id, Fork left, Fork right, boolean rHanded,
	                   int nTimes, long thinkMillis, long eatMillis) {
		this.id = id;
		this.left = left;
		this.right = right;
		this.rHanded = rHanded;
		this.nTimes = nTimes;
		this.thinkMillis = thinkMillis;
		this.eatMillis = eatMillis;
	}

	public void run() {

	}

}
