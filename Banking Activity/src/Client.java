package src;
import java.lang.*;
import java.util.concurrent.ThreadLocalRandom;

public class Client extends Thread {

	private Banker banker;
	private int nUnits, nRequests;
	private long minSleepMillis, maxSleepMillis;

	public Client(String name, Banker banker, int nUnits, int nRequests,
		long minSleepMillis, long maxSleepMillis) 
	{
		super(name);
		this.banker 		= banker;
		this.nUnits 		= nUnits;
		this.nRequests 		= nRequests;
		this.minSleepMillis = minSleepMillis;
		this.maxSleepMillis = maxSleepMillis;
	}

	public void run() {
		ThreadLocalRandom random = ThreadLocalRandom.current();

		// Register a claim for up to nUnits
		nUnits = random.nextInt (1, nUnits + 1);

		//Set claim
		banker.setClaim(nUnits);

		while (nRequests > 0) {
			nRequests--;

			// If the banker.remaining() == 0, then the client will release 
			// all of its units, else the client will request some units.
			if (banker.remaining() == 0) {
				banker.release(banker.allocated());
			}
			else if (nUnits - banker.allocated() >= 1) {
				//request some (more) units between 1 and
				//the remaining needed units
				banker.request(
					random.nextInt((nUnits - banker.allocated()) + 1) + 1
				);
			}

			try {
				Thread.sleep(
					random.nextLong(minSleepMillis, maxSleepMillis + 1)
				);
			}
			catch(InterruptedException e) {}
		}

		banker.release(banker.allocated());
		return;
	}

}
