/**
 * 
 */
package clientMatch;

import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * class that manages all comunication with server during the game
 *
 */
public class SocketSession implements GameSessionServerSide {
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	private ObjectInputStream objin;
	private matchController controller;
	
	public SocketSession(Socket socket) {
		try {
			in = new Scanner(socket.getInputStream());
		    out = new PrintWriter(socket.getOutputStream());
		    objin = new ObjectInputStream(socket.getInputStream()); 
		} catch (IOException e) {

		}
	}

	@Override
	public void setController(matchController matchController) {
		this.controller = matchController;
		
	}

	@Override
	public Character getCharacter() {
		if (in.nextLine().equals("sending character"))
			try {
				return  (Character) objin.readObject();
			} catch (ClassNotFoundException e) {
			} catch (IOException e) {
			}
		return null;
	}

	@Override
	public String listenTurn() {
		String line = in.nextLine();
		String command[] = line.split("=");
		if (command[0].equals("turn"))
		{
			return line.substring(command[0].length()+1, line.length());
		}
		return null;
	}

}
