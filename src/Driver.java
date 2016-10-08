package src;
import java.util.ArrayList;

public class Driver {

	public static void main(String args[]) {
		//Secret game mode
		if (args.length > 0 && args[0].equals("-f")) {
			System.out.println("Fight mode!");
			System.out.println("Socrates punches Plato");
			return;
		}
		ArrayList<Long> values = new ArrayList<Long>();
		ArrayList<Fork> forks = new ArrayList<Fork>();
		int np, nTimes;
		long thinkMillis, eatMillis;
		boolean toggleLeft = false;

		//Parse command line arguments
		for (int i = 0; i < args.length; i++) {
			//If the left flag is provided, toggle
			if (args[i].equals("-l")) {
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
		np = (values.size() > 1) ? values.get(0).intValue() : 4;
		nTimes = (values.size() > 2) ? values.get(1).intValue() : 10;
		thinkMillis = (values.size() > 3) ? values.get(2) : 0;
		eatMillis = (values.size() > 4) ? values.get(3) : 0;

		//Generate Forks
		for (int i = 1; i <= np; i++) {
			forks.add( new Fork() );
		}

		//Generate Philosophers
		for (int i = 0; i < np; i++) {
			boolean rHanded = true;
			if (toggleLeft && i % 2 != 0) rHanded = false;

			(new Philosopher(i+1, forks.get((np + i - 1) % np), forks.get(i), 
								rHanded, nTimes, thinkMillis, eatMillis)
			).start();
		}
	}
}
