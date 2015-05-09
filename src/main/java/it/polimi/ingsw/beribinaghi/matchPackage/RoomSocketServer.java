/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Room server with socket
 *
 */
public class RoomSocketServer extends RoomServer {
	private ServerSocket server;
	private Boolean active;
	
	public RoomSocketServer(int port) throws IOException
	{
		server = new ServerSocket(port);
		active = true;
		this.start();
	}
	
	/**
	 * start to accept connection manage the comunication;
	 * @throws IOException 
	 */
	public void start() throws IOException
	{
		while (active)
		{
			Socket socket = server.accept();
			(new SetupSession(socket,RoomServer.getControllerMatch())).start();
		}
	}
}
