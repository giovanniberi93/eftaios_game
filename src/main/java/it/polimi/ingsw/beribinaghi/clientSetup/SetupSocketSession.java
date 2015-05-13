/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

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
	
	@Override
	public Boolean connect() {
		try {
			socket = new Socket(EscapeFromTheAliensInOuterSpace.address,EscapeFromTheAliensInOuterSpace.port);
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
			out.println(userName);
			out.flush();
		}
	}

	@Override
	public ArrayList<String> getMatchesName() {
		ArrayList<String> matchesName = new ArrayList<String>();
		if (in.nextLine().equals("print match name"))
		{
			int numberMatches = in.nextInt();
			for (int i = 0;i<numberMatches;i++)
				matchesName.add(in.nextLine());
			return matchesName;
		}
		return null;
	}

	@Override
	public void createNewMatch(String name) {
		out.println("new");
		out.println(name);
		out.flush();
	}



}
