/**
 * 
 */
package it.polimi.ingsw.beribinaghi.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * RMI interface that manages the pre match communication
 */
public interface RemoteSetupSession extends Remote  {
	/**
	 * @return the names of all inactive match
	 */
	public ArrayList<String> printMatchName() throws RemoteException;
	
	/**
	 * @param user
	 * login to server
	 */
	public void login(String user)  throws RemoteException;
		
	/**
	 * @param matchName
	 * @return true if match is created
	 * create new match with matchName
	 */
	public Boolean createNewMatch(String matchName) throws RemoteException;
	
	/**
	 * @param matchName
	 * @return result of operation
	 * lets player enter in the match
	 * return 0 if the operation is successful
	 * return 1 if name not exists
	 * return 2 if there are too many player in room
	 * return 3 match is just started
	 */
	public int enterMatch(String matchName) throws RemoteException;
	
	/**
	 * @return the names of all player in room
	 */
	public ArrayList<String> playerInRoom() throws RemoteException;
	
	/**
	 * @return true if match is started
	 */
	public Boolean isStarted() throws RemoteException;
	
	/**
	 * @return true if match is started
	 */
	public Boolean thereIsNewPlayer() throws RemoteException;
	
	/**
	 * @return true if match is started
	 */
	public ArrayList<String> playerNotNotify() throws RemoteException;
	
	/**
	 * @return the name of game in stub
	 */
	public String getStringBind() throws RemoteException;
	
	/**
	 * close session with client
	 */
	public void closeSession() throws RemoteException;

	/**
	 * close the match after error
	 */
	public void stop() throws RemoteException;
	
}
