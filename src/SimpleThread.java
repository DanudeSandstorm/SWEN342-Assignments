package src;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.*;

public class SimpleThread implements Runnable {
	
	private String name;

	public SimpleThread(String name) {
		this.name = name;
	}

	public void run() {

		for (int i = 1; i <= 10; i++) {
			ThreadLocalRandom random = ThreadLocalRandom.current();
			try {
				Thread.sleep((long) random.nextInt(0, 1001));
			}
			catch(InterruptedException e) {}

			if (i < 10) {
				System.out.println(i + " " + name);
			}
			else {
				System.out.println("DONE! " + name);
			}
		}
	}
}
