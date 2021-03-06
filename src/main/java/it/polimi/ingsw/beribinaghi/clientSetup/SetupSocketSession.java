/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.ClientSocketSession;
import it.polimi.ingsw.beribinaghi.clientMatch.GameSessionClientSide;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  interface for communication with server via socket
 *
 */
public class SetupSocketSession implements SetupSession {

	Socket socket;
	private PrintWriter out;
	private Scanner in;
	private String myName;
	
	@Override
	public Boolean connect() {
		try {
			socket = new Socket(EscapeFromTheAliensInOuterSpace.ADDRESS,EscapeFromTheAliensInOuterSpace.SOCKETPORT);
			in = new Scanner(socket.getInputStream());
		    out = new PrintWriter(socket.getOutputStream());
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void login(String userName) {
		if (in.nextLine().equals("login"))
		{
			myName = userName;
			out.println(userName);
			out.flush();
		}
	}

	@Override
	public ArrayList<String> getMatchesName() {
		ArrayList<String> matchesName = new ArrayList<String>();
		out.println("update");
		out.flush();
		String p = in.nextLine();
		if (p.equals("print match name"))
		{
			int numberMatches = Integer.parseInt((in.nextLine()));
			for (int i = 0;i<numberMatches;i++)
				matchesName.add(in.nextLine());
			return matchesName;
		}
		return null;
	}

	@Override
	public boolean createNewMatch(String matchName) {
		out.println("new");
		out.println(matchName);
		out.flush();
		return in.nextLine().equals("player enter in room");
	}

	@Override
	public int enterGame(String matchName) {
		out.println("enter");
		out.println(matchName);
		out.flush();
		String answer = in.nextLine();
		if (answer.equals("player enter in room"))
			return 0;
		if (answer.equals("name not exists"))
			return 1;
		if (answer.equals("too many player"))
			return 2;
		else return 3;
	}

	@Override
	public void close() {
		out.println("exit");
		out.flush();
		try {
			
			socket.close();
		} catch (IOException e) {
			
		}
	}

	@Override
	public synchronized ArrayList<String> getPlayer() {
		ArrayList<String> playersName = new ArrayList<String>();
		out.println("player");
		out.flush();
		if (in.nextLine().equals("print name players")){
			int numberPlayer = Integer.parseInt((in.nextLine()));
			for (int i = 0;i<numberPlayer;i++)
				playersName.add(in.nextLine());
			return playersName;
		}
		return null;
	}

	@Override
	public boolean isStarted(SetupController setupController) {
			String inLine = in.nextLine();
			if (inLine.equals("started match"))
			{
				return true;
			} else if (inLine.equals("new player"))
				setupController.notifyNewPlayer(in.nextLine());
		return false;
	}

	@Override
	public void exitRoom() {
		out.println("exit");
		out.flush();
	}

	@Override
	public GameSessionClientSide startGameComunication() {
		return new ClientSocketSession(socket,in,out);
	}

	@Override
	public Boolean closeAfterError() {
		try {
			socket.close();
		} catch (IOException e) {}
		return reconnect();
	}

	@Override
	public Boolean reconnect() {
		try {
			
			socket = new Socket(EscapeFromTheAliensInOuterSpace.ADDRESS,EscapeFromTheAliensInOuterSpace.SOCKETPORT);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());	
			login(myName);
			return true;
		} catch (IOException e) {}
		return false;
			
	}



}
