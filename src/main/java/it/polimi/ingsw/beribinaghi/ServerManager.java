package it.polimi.ingsw.beribinaghi;

import java.io.IOException;
import java.util.Scanner;

import it.polimi.ingsw.beribinaghi.matchPackage.RoomSocketServer;

/**
 * This class manages the server
 * It generate a Socket listener and a RMI object
 * This class manage instructions come from CLI
 */
public class ServerManager {
	private static RoomSocketServer socketServer;
	private Scanner inCommand;
	
	/**
	 * This procedure create, if not existing, the server at port indicate and start receiving command from CLI
	 */
	public ServerManager(int port){
		Boolean error = false;
		if (socketServer==null)
		{
			try {
				socketServer = new RoomSocketServer(port);
				System.out.println("Server successfully created at port: " + port);
				startServer();
			} catch (IOException e) {
				System.out.println("Error occurred, impossible to create the server");
				error = true;
			}
		}
		else
			System.out.println("Server just existing");
		if (!error)
			controllerInterface();
	}
	
	private void startServer(){
		if (!socketServer.isAlive()){
			socketServer.start();
			System.out.println("Server successfully started");
		}
		else 
			System.out.println("Server already started");
	}
	
	private void stopServer() {
		if (socketServer.isActive()){
			socketServer.setInactive();
			System.out.println("Server uccessfully stoped");
		}
		else 
			System.out.println("Server alredy stoped");
	}
	
	private void notifyServer() {
		if (!socketServer.isActive())
		{
			socketServer.setActive();
			System.out.println("Server successfully awake");
		}
		else
			System.out.println("Server alredy awake");
	}
	
	private void exitServer() {
		socketServer.exit();
	}

	private void controllerInterface() {
		inCommand = new Scanner(System.in);
		String command;
		System.out.println("Start reciving command");
		do{
			command = correct(inCommand.nextLine());
			if (command.equals("start"))
				startServer();
			else if (command.equals("stop"))
				stopServer();
			else if (command.equals("wake"))
				notifyServer();
			else if (!command.equals("exit"))
				System.out.println("command not recognized");
		}while (!command.equals("exit"));
		exitServer();
		System.out.println("Server closed");
	}

	private static String correct(String string) {
		return string.trim().toLowerCase();
	}
	
}
