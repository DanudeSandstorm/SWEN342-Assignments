package src;

public class Test {

	public static void main(String args[]) {
		Bridge bridge = new Bridge();
		(new Thread(new Woolie("Sonic", 1, "Merctan", bridge))).start();
		(new Thread(new Woolie("Walle", 10, "Sicstine", bridge))).start();
		(new Thread(new Woolie("Hi", 5, "Merctan", bridge))).start();
		(new Thread(new Woolie("Ho", 8, "Sicstine", bridge))).start();
	}

}
