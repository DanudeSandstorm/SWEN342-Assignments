package src;
import java.util.*;
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

		while(algorithm() == false) {
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
		nOnHand -= nUnits;
		values[1] += nUnits;
		return true;
	}

	private boolean algorithm() {
		int i = 0; //variable for iterating
		//Create copies of "units on hand" and clients map
		int nOnHandVirtual = nOnHand;
		HashMap<String, int[]> clientsVirtual = clients;


		//Create an array holding the allocation/remaining
		//claim pairs (the identity of the threads is irrelevant).
		int[][] claims = new int[clientsVirtual.size()][2];
		
		for (String key : clientsVirtual.keySet()) {
			claims[i] = clientsVirtual.get(key);	
			i++;
		}
		
		//Sort the array by increasing order of remaining claim.
		Arrays.sort(claims, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return Integer.compare(a[1], b[1]);
			}
		});

		/*
			If:
			There are not enough units for the ith thread's claim,
			then it cannot be guaranteed to complete. 
			Because the array is sorted, no thread after i can be 
			guaranteed to complete, so we have possible deadlock.
			
			Else:
			There are enough resources on hand so that we could 
			run this thread until it releases all its resources, 
			in which case we'd reclaim them and advance to the array
			entry for the next thread.
		*/
		for (i = 0; i < claims.length; i++) {
			if ((claims[i][0] - claims[i][1]) > nOnHandVirtual) {
				return false;
			} 
			else {
				nOnHandVirtual += claims[i][0];
			}
		}
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
		nOnHand += nUnits;

		notifyAll();

		return;
	}

	//Returns the number of units allocated to the current thread.
	public int allocated() {
		String name = Thread.currentThread().getName();
		int[] values = clients.get(name);
		//If thread hasn't made claim, can't have allocated
		if (values == null) {
			return 0;
		}

		return values[1];
	}

	//Returns the number of units remaining in the current thread's claim.
	public int remaining() {
		return nOnHand;
	}
}
