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
 * Class that manage a session with a new client
 *
 */
public class SetupSocketSession extends Thread {

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Player player;
	private MatchController matchController;
	private String matchName;
	
	public SetupSocketSession(Socket socket,MatchController matchController) throws IOException{
		this.matchController = matchController;
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
			if (choose.equals("update"))
				printMatchName();
			else if (choose.equals("new"))
				createNewMatch(in.nextLine());
		}while (true);//Da cambiare
	}


	private void createNewMatch(String matchName) {
		try {
			matchController.createNewMatch(matchName,player);
			playerInRoom();
		} catch (AlreadyExistingNameException e) {
			out.println("name already existed");
		}
	}


	private void playerInRoom() {
		out.println("match create, you are in room");
		String command = in.nextLine();
		if (command.equals("exit"))
			try {
				matchController.exitPlayer(matchName,player);
			} catch (NotExistingNameException e) {
				
			}
	}


	private void printMatchName() {
		ArrayList<String> nameList = matchController.getMatchesName();
		out.println("print match name");
		out.println(nameList.size());
		out.flush();
		for (String nameMatch : nameList)
			out.println(nameMatch);
		out.flush();
	}

	private void login() {
		out.println("login");
		out.flush();
		String user = in.nextLine();
		player = new Player(user);
	}
	
}
