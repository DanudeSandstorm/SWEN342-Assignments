package src;

public class Test {

	public static void main(String args[]) {
		(new Thread(new Woolie("Sonic", 1, "Merctan"))).start();
		(new Thread(new Woolie("Walle", 10, "Sicstine"))).start();
		(new Thread(new Woolie("Hi", 5, "Merctan"))).start();
		(new Thread(new Woolie("Ho", 8, "Sicstine"))).start();
	}

}
