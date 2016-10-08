package src;
import java.util.HashMap;
import java.lang.*;

public class Banker {

	private int nResources, nOnHand; //units of resources to manage
	//int[] : claim, allocated
	private HashMap<String, int[]> clients = new HashMap<String, int[]>();

	public Banker(int nUnits) {
		this.nResources = nUnits;
		this.nOnHand = nUnits;
	}

	public synchronized void setClaim(int nUnits) {
		String name = Thread.currentThread().getName();

		/*
		the thread already has a claim registered, or
		nUnits is not strictly positive, or
		nUnits exceeds the number of resources in the system.
		*/
		if (clients.get(name) != null 
			|| nUnits < 0
			|| nUnits > nResources
			) {
			System.exit(1);
		}

		/*
		Associate the thread with a current claim equal to nUnits 
		and a current allocation of 0.
		*/
		clients.put(name, new int[] {nUnits, 0} );

		System.out.println("Thread " + name + 
			" sets a claim for " + nUnits + " units.");
		
		return;
	}

	public synchronized boolean request(int nUnits) {
		String name = Thread.currentThread().getName();
		int[] values = clients.get(name);
		if (values == null
			|| nUnits < 0
			//exceeds the invoking thread's remaining claim
			|| nUnits > values[0] - values[1]
			) {
			System.exit(1);
		}

		System.out.println("Thread " + name + " requests " +
			nUnits + " units.");

		//TODO
		boolean dank = true;
		while(dank) {
			System.out.println("Thread " + name + " waits.");
			// Waits for notification of a change.
			try {
				wait();
			}
			catch(InterruptedException e) {}

			System.out.println("Thread " + name + " awakened.");
		}

		System.out.println("Thread " + name + " has " +
			nUnits + " units allocated.");

		return true;
	}

	public synchronized void release(int nUnits)  {
		String name = Thread.currentThread().getName();
		int[] values = clients.get(name);
		if (values == null
			|| nUnits < 0
			//exceeds the number of units currently allocated
			|| nUnits > values[1]
			) {
			System.exit(1);
		}

		System.out.println("Thread " + name + " releases " + 
			nUnits + " units.");

		//Release units allocated
		values[1] -= nUnits;
		nOnHand -= nUnits;

		notifyAll();

		return;
	}

	public int allocated() {
		//total resources minus remaining
		return nResources - nOnHand;
	}

	public int remaining() {
		return nOnHand;
	}
}
