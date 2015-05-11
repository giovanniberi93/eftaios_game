/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that manage a session with a new clien
 *
 */
public class SetupSocketSession extends Thread {

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Player player;
	private ControllerMatch controllerMatch;
	
	public SetupSocketSession(Socket socket,ControllerMatch controllerMatch) throws IOException{
		this.controllerMatch = controllerMatch;
		this.socket = socket;
		in = new Scanner(socket.getInputStream());
	    out = new PrintWriter(socket.getOutputStream());
	}
	
	
	public void run(){
		login();
		printMatchName();
		choose();
	}

	private void choose() {
		do{
			String choose = in.nextLine();
			if (choose.equals("ciao"));
		}while (true);//Da cambiare
	}


	private void printMatchName() {
		ArrayList<String> nameList = controllerMatch.getMatchesName();
		for (String nameMatch : nameList)
			out.println(nameMatch);
		out.flush();
	}

	private void login() {
		String user = in.nextLine();
		player = new Player(user);
	}
	
}
