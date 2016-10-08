package src;

public class Driver {

	final private static int 
		nResources = 0, 
		nClient = 0, 
		nUnits = 0,
		nRequests = 0;

	final private static long
		minSleepMillis = 0,
		maxSleepMillis = 0;

	public static void main(String args[]) {
		Banker banker = new Banker(nResources);

		for (int i = 0; i <= nClient; i++) {
			(new Client(Integer.toString(i), banker, nUnits, nRequests,
				minSleepMillis, maxSleepMillis)
			).start();
		}
	}
}
