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
	
	/**
	 * Set the server inactive
	 */
	public void setInactive() {
		super.setInactive();
		unlock();
	}
	
	/**
	 * Set the server active
	 */
	public synchronized void setActive() {
		super.setActive();
		this.notify();
	}

	/**
	 * end server
	 */
	public void exit(){
		end = true;
		this.setInactive();
		unlock();
	}
	
	private void unlock(){
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public RoomSocketServer(int port) throws IOException
	{
		server = new ServerSocket(port);
		end = false;
		this.setActive();
	}
	
	/**
	 * start to accept connection manage the communication;
	 * @throws IOException 
	 */
	public void run()
	{
		Socket socket;
		while (!end)
		{
			while (this.isActive())
			{
				try {
					socket = server.accept();
					(new SetupSession(socket,RoomServer.getControllerMatch())).start();
				} catch (IOException e) {
					
				}
			}
			if (!end)
				serverWait();
		}
	}

	public synchronized void serverWait(){
		try {
			while (!this.isActive())
				this.wait();
		} catch (InterruptedException e) {
			
		}
	}
}
