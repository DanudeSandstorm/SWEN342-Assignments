package src;
import java.lang.*;

public class Woolie implements Runnable {

	private String name;
	private String city;
	private int speed;
	private Bridge bridge;

	public Woolie(String name, int speed, String city, Bridge bridge) {
		this.name = name;
		this.speed = speed;
		this.city = city;
		this.bridge = bridge;
	}

	public void run() {

		System.out.println(name + " has arrived at the bridge.");

		bridge.enterBridge(this);
		cross();
		bridge.leaveBridge();
	}

	private void cross() {
		//Action at time zero
		System.out.println(name + " is starting to cross.");
		//Since print statement is after the sleep,
		//the for loop starts at 1
		for (int i = 1; i <= speed; i++) {

			try { Thread.sleep(1000); }
			catch(InterruptedException e) {}

			System.out.println("\t" + " " + name + " " + i + " seconds.");
		}

		System.out.println(name + " leaves at " + city);
	}

}

