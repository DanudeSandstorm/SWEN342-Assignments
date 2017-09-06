package src;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    static int bucketSize = 3;
    static int mapSize = 5;
    static long maxSleepMillis = 1000;
    static ConcurrentBucketHashMap<String, String> map;

	public static void main(String[] args) {
        map = new ConcurrentBucketHashMap<>(bucketSize);

        final ArrayList<Thread> tasks = new ArrayList<>();

		for (int i = 0; i < bucketSize; i++) {
            tasks.add(new Thread(new Task("Thread " + i)));
		}

		for (Thread task: tasks) {
			task.start();
		}


		for (Thread task: tasks) {
			try {
			    task.join();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		}

	}
    
	private static class Task implements Runnable {
		private final String name;

		Task(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			ThreadLocalRandom random = ThreadLocalRandom.current();

			for (int i = 1; i <= mapSize; i++) {
				map.put(name + i, name + " " + i);

				//Random sleep
				try {
					Thread.sleep(
						random.nextLong(0, maxSleepMillis + 1)
					);
				}
				catch (InterruptedException ignored) {}

				// Randomly remove the element we just put in
				if ( random.nextBoolean() ){
					map.remove(name + i);
				}

				//Randomly check size of thread
				if ( random.nextBoolean() ){
					System.out.printf( "The size of bucket is %s%n", map.size() );
					
					try{
						Thread.sleep(1000);
					} catch ( InterruptedException ie ) {
						System.err.println( "Error: " + ie.getMessage() );
					}
				}
			}
		}
	}
}
