package src;
import java.util.ArrayList;

public class Driver {

	public static void main(String args[]) {
		Arraylist<long> values = new Arraylist<long>();
		ArrayList<Fork> forks = new ArrayList<Fork>();
		int np, nTimes;
		long thinkMillis, eatMillis;
		boolean toggleLeft = false;

		//Parse command line arguments
		for (int i = 0; i < args.length; i++) {
			//If the left flag is provided, toggle
			if (args[i] == "-l") {
				toggleLeft = true;
				continue;
			}

			//Add the argument to the list
			try {
				values.add(Long.parseLong(args[i]));
			} catch (NumberFormatException nfe) {}
		}

		//If the command argument was given,
		//get its value; else set as default
		np = (values.size() > 1) ? (int) values.get(0) : 4;
		nTimes = (values.size() > 2) ? (int) values.get(1) : 10;
		thinkMillis = (values.size() > 3) ? values.get(2) : 0;
		eatMillis = (values.size() > 4) ? values.get(3) : 0;

		//Generate Forks
		for (int i = 1; i <= np; i++) {
			forks.add( new Fork() );
		}

		//Generate Philosophers
		for (int i = 1; i <= np; i++) {
			boolean rHanded = true;
			if (toggleLeft && i % 2 != 0) rHanded = false;

			(new Philosopher(i, forks.get((np + i - 1) % np), forks.get(i), 
								rHanded, nTimes, thinkMillis, eatMillis)
			).start();
		}
	}
}
