package src;
import java.util.concurrent.ThreadLocalRandom;
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
		boolean infinite = (nTimes == 0);

		do {
			think();

			releaseForks();

			nTimes--;
		} while (infinite || nTimes > 0);
	}


	private void think() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		long t = random.nextLong(thinkMillis + 1);
		System.out.println("Philosopher " + id + " thinks for " +
			t + " milliseconds");
		try {
			Thread.sleep(t);
		}
		catch(InterruptedException e) {}
	}

	private void releaseForks() {
		right.release();
		System.out.println("Philosopher " + id + "releases right fork");
		left.release();
		System.out.println("Philosopher " + id + "releases left fork");
	}
}
