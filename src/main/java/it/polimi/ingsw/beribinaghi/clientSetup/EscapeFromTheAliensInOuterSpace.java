/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import java.util.Scanner;

/**
 * main client class
 *
 */
public class EscapeFromTheAliensInOuterSpace {

	public static ConnectionType connectionType;
	public static InterfaceType interfaceType;
	public static String ADDRESS = "127.0.0.1";
	public final static int SOCKETPORT = 2767;
	public final static int RMIPORT = 2768;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Scegli la modalità di connessione (Socket/RMI)");
		String inLine = in.nextLine().toUpperCase();
		while (!inLine.equals("RMI") && !inLine.equals("SOCKET")){
			System.out.println("Scegli la modalità di connessione (Socket/RMI)");
			inLine = in.nextLine().toUpperCase();
		}
		if (inLine.equals("RMI"))
			connectionType = ConnectionType.RMI;
		else
			connectionType = ConnectionType.SOCKET;
		System.out.println("Scegli l'interfaccia grafica (CLI/GUI)");
		inLine = in.nextLine().toUpperCase();
		while (!inLine.equals("CLI") && !inLine.equals("GUI")){
			System.out.println("Scegli l'interfaccia grafica (CLI/GUI)");
			inLine = in.nextLine().toUpperCase();
		}
		if (inLine.equals("CLI"))
			interfaceType = InterfaceType.CLI;
		else
			interfaceType = InterfaceType.GUI;
		System.out.println("Inserisci l'indirizzo IP del server");
			ADDRESS = in.nextLine();
		GraphicInterface gi = interfaceType.getGraphicInterface();
		SetupSession ss = connectionType.getSetupSession();
		new SetupController(gi,ss);
	}

}
