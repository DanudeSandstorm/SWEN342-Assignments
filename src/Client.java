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

		while (nRequests > 0) {
			nRequests--;

			//request some (more) units between 1 and
			//the remaining needed units
			banker.request(
				random.nextInt(1, (nUnits - banker.allocated()) + 1)
			);

			if (banker.remaining() == 0) {
				banker.release(banker.allocated());
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
