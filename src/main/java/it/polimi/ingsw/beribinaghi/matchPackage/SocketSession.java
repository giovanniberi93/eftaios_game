package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketSession extends GameSession implements Runnable{
	private final long TIMEWAITASSIGNCHARACTER = 100;
	private Scanner in;
	private PrintWriter out;
	ObjectOutputStream oos;
	
	public SocketSession(Socket socket,Player player) throws IOException {
		in = new Scanner(socket.getInputStream());
	    out = new PrintWriter(socket.getOutputStream());
	    oos = new ObjectOutputStream(socket.getOutputStream());
	    this.player = player;
	}

	
	
	private synchronized void notifyCharacter() {
		while (player.getCharacter()!=null)
			try {
				this.wait(TIMEWAITASSIGNCHARACTER);
			} catch (InterruptedException e) {
			}
		out.println("sending character");
		try {
			oos.writeObject(player.getCharacter());
		} catch (IOException e) {
		}
	}



	@Override
	public void run() {
	    notifyCharacter();
		
	}

}
