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
public class SetupSocketSession extends Thread implements SetupSession {

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
	    matchController.registerSession(this);
	}
	
	
	public void run(){
		login();
		if (in.nextLine().equals("update"))
			printMatchName();
		choose();
	}

	private void choose() {
		Object choose;
		Boolean inRoom = false;
		Boolean exitPre = false;
		do{
			choose = in.nextLine();
			if (choose.equals("update"))
				printMatchName();
			else if (choose.equals("new"))
			{
				if (createNewMatch(in.nextLine()));
					inRoom = true;
			}
			else if (choose.equals("enter"))
			{
				if (enterMatch(in.nextLine()))
					inRoom= true;
			}
			else if (choose.equals("exit"))
					exitPre = true;
		}while (!inRoom && !exitPre);
		if (exitPre)
			closeSession();
		else 
			playerInRoom();
	}


	public void closeSession() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}


	private boolean enterMatch(String matchName) {
		try {
			matchController.addPlayer(matchName, player);
			out.println("player enter in room");
			out.flush();
			this.matchName = matchName;
			return true;
		} catch (NotExistingNameException e) {
			out.println("name not exists");
			out.flush();
		} catch (TooManyPlayerException e) {
			out.println("too many player");
			out.flush();
		}
		return false;
	}


	private void printPlayerInRoom(String matchName) throws NotExistingNameException {
		ArrayList<String> playerName = matchController.getPlayer(matchName);
		out.println("print name players");
		out.println(playerName.size());
		for (String player: playerName)
			out.println(player);
		out.flush();
	}


	private boolean createNewMatch(String matchName) {
		try {
			matchController.createNewMatch(matchName,player);
			out.println("player enter in room");
			out.flush();
			this.matchName = matchName;
			return true;
		} catch (AlreadyExistingNameException e) {
			out.println("name already existed");
			out.flush();
		}
		return false;
	}


	private void playerInRoom() {
		String command = in.nextLine();
		if (command.equals("player"))
			try {
				printPlayerInRoom(matchName);
			} catch (NotExistingNameException e1) {

			}
		else if (command.equals("exit"))
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


	@Override
	public void startMatch() {
		// TODO Auto-generated method stub
		
	}
	
}
