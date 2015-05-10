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
		if (socketServer==null)
		{
			try {
				socketServer = new RoomSocketServer(port);
				System.out.println("Server successfully created at port: " + port);
				startServer();
			} catch (IOException e) {
				System.out.println("Error occurred, impossible to create the server");
			}
		}
		else
			System.out.println("Server just existing");
		controllerInterface();
	}
	
	private void startServer(){
		if (!socketServer.isAlive()){
			socketServer.start();
			System.out.println("Server successfully started");
		}
		else 
			System.out.println("Server alredy started");
	}
	
	private void stopServer() {
		if (socketServer.isAlive()){
			try {
				socketServer.wait();
				System.out.println("Server successfully stoped");
			} catch (InterruptedException e) {
				System.out.println("Error occurred, impossible to stop the server");
			}
		}
		else 
			System.out.println("Server alredy stoped");
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
		}while (command.equals("exit"));
	}


	private static String correct(String string) {
		return string.trim().toLowerCase();
	}
	
}
