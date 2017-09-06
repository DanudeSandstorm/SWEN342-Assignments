package src;

import java.util.ArrayList;
import java.lang.*;

public class Driver {

	final private static int 
		nResources = 5, //banker's resources
		nClient = 3, 
		nUnits = 4, //number of resources
		nRequests = 2;

	final private static long
		minSleepMillis = 1000,
		maxSleepMillis = 2000;

	public static void main(String args[]) {
		Banker banker = new Banker(nResources);
		ArrayList<Client> clients = new ArrayList<Client>(); 

		for (int i = 0; i <= nClient; i++) {
			clients.add(
				new Client(Integer.toString(i), banker, nUnits, nRequests,
				minSleepMillis, maxSleepMillis)
			);
			clients.get(i).start();
		}

		try { 
			for (int i = 0; i <= clients.size(); i++) {
				clients.get(i).join();
			}

		} catch (InterruptedException e) {}
	}
}
