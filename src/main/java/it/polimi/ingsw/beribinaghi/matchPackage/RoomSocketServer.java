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
	}
	
	/**
	 * start to accept connection manage the comunication;
	 * @throws IOException 
	 */
	public void run()
	{
		while (active)
		{
				Socket socket;
				try {
					socket = server.accept();
					(new SetupSession(socket,RoomServer.getControllerMatch())).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
