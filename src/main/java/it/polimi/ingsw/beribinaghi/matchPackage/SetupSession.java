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
public class SetupSession extends Thread {

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Player player;
	private ControllerMatch controllerMatch;
	
	public SetupSession(Socket socket,ControllerMatch controllerMatch) throws IOException{
		this.controllerMatch = controllerMatch;
		this.socket = socket;
		in = new Scanner(socket.getInputStream());
	    out = new PrintWriter(socket.getOutputStream());
	}
	
	public void run(){
		login();
		printMatchName();
	}

	private void printMatchName() {
		ArrayList<String> nameList = controllerMatch.getMatchesName();
		
	}

	private void login() {
		String user = in.nextLine();
		player = new Player(user);
	}
	
}
