package src;

public class TwoThreadTest {

	public static void main(String args[]) {
		(new Thread(new SimpleThread("Hi"))).start();
		(new Thread(new SimpleThread("Ho"))).start();
	}
}
