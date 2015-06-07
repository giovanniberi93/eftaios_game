
package it.polimi.ingsw.beribinaghi.serverSetup;

import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.ServerSocketSession;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that manage a session with a new client using Socket
 *
 */
public class SetupSocketSession extends Thread implements SetupSession {

	private static final long WAITCHECKSTART = 1000;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Player player;
	private MatchController matchController;
	private String matchName;
	private Boolean active;
	private ServerSocketSession socketSession;
	private Boolean inRoom = false;
	
	public SetupSocketSession(Socket socket,MatchController matchController) throws IOException{
		this.matchController = matchController;
		this.socket = socket;
		in = new Scanner(socket.getInputStream());
	    out = new PrintWriter(socket.getOutputStream());
	    this.matchController.registerSession(this);
	    active = false;
	}
	
	
	public void run(){
		try{
			login();
			if (in.nextLine().equals("update"))
				printMatchName();
			choose();
		} catch (NoSuchElementException e){
			this.closeSession();
		}
	}

	private void choose() throws NoSuchElementException {
		Object choose;
		Boolean exitPre = false;
		do{
			do{
				choose = in.nextLine();
				if (choose.equals("update"))
					printMatchName();
				else if (choose.equals("new"))
				{
					if (createNewMatch(in.nextLine()))
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
			inRoom = false;
		}while (!exitPre);
	}


	/**
	 *  close session with client
	 */
	public void closeSession() throws NoSuchElementException {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}


	private boolean enterMatch(String matchName) throws NoSuchElementException {
		try {
			matchController.addPlayer(matchName, player);
			out.println("player enter in room");
			out.flush();
			this.matchName = matchName;
			printPlayerInRoom(this.matchName);
			return true;
		} catch (NotExistingNameException e) {
			out.println("name not exists");
			out.flush();
		} catch (TooManyPlayerException e) {
			out.println("too many player");
			out.flush();
		} catch (MatchJustStartingException e) {
			out.println("match just started");
			out.flush();
		}
		return false;
	}


	private void printPlayerInRoom(String matchName) throws NotExistingNameException {
		in.nextLine();
		ArrayList<String> playerName = matchController.getPlayer(matchName);
		out.println("print name players");
		out.println(playerName.size());
		for (String player: playerName)
			out.println(player);
		out.flush();
	}


	private boolean createNewMatch(String matchName) throws NoSuchElementException {
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


	private synchronized void playerInRoom() {
		while (!active)
		{
			try {
				this.wait(WAITCHECKSTART);
			} catch (InterruptedException e) {
			}
		}
		while (active)
			try {
				this.wait();
			} catch (InterruptedException e) {

			}
	}




	private void printMatchName() throws NoSuchElementException {
		ArrayList<String> nameList = matchController.getMatchesName();
		out.println("print match name");
		out.println(nameList.size());
		out.flush();
		for (String nameMatch : nameList)
			out.println(nameMatch);
		out.flush();
	}

	private void login() throws NoSuchElementException {
		out.println("login");
		out.flush();
		String user = in.nextLine();
		player = new Player(user);
	}


	@Override
	public void startMatch() {
		active = true;
		try {
			socketSession.setMatch((matchController.getMatch(this.matchName)).getMatch());
		} catch (NotExistingNameException e) {
		}
	}


	@Override
	public void notifyNewPlayer(String namePlayer) {
		if (socket.isConnected()){
		out.println("new player");
		out.println(namePlayer);
		out.flush();
		} else {
			try {
				matchController.exitPlayer(namePlayer, player);
			} catch (NotExistingNameException e) {
			}
			this.closeSession();
		}
	}

	@Override
	public GameSessionServerSide getGameSession() {
		try {
		    out.println("started match");
		    out.flush();
			socketSession = new ServerSocketSession(socket,in,out,player);
			return socketSession;
		} catch (IOException e) {
		}
		return null;
	}
	
	@Override
	public String getMatchName() {
		return matchName;
	}


	@Override
	public Boolean isConnected() {
		if (!socket.isConnected())
		{
			try {
				matchController.exitPlayer(matchName, player);
			} catch (NotExistingNameException e) {
			}
			this.closeSession();
			return false;
		}
		return true;
	}


	@Override
	public synchronized void setInactive() {
		this.active = false;
		this.matchName = null;
		this.notify();
	}
}
