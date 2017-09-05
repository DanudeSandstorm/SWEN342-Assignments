package src;

public class Test {

	public static void main(String args[]) {
		Bridge bridge = new Bridge();
		(new Thread(new Woolie("Sonic", 1, "Merctan", bridge))).start();
		(new Thread(new Woolie("Walle", 10, "Sicstine", bridge))).start();
		(new Thread(new Woolie("Joe", 5, "Merctan", bridge))).start();
		(new Thread(new Woolie("Tina", 6, "Sicstine", bridge))).start();
		(new Thread(new Woolie("Eric", 7, "Merctan", bridge))).start();
	}

}
