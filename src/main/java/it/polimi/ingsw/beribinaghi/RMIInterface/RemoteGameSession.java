/**
 * 
 */
package it.polimi.ingsw.beribinaghi.RMIInterface;

import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface that manages the game match communication
 */
public interface RemoteGameSession extends Remote {

	/**
	 * @return player's character
	 * @throws RemoteException
	 * 
	 */
	public Character getCharacter() throws RemoteException;
	/**
	 * @return the match's map
	 * @throws RemoteException
	 */
	public Map getMap() throws RemoteException;
	/**
	 * @return the name of player that is in turn
	 * @throws RemoteException
	 */
	public String getPlayerTurn() throws RemoteException;
	/**
	 * @return true if map is ready to be notify
	 * @throws RemoteException
	 */
	public Boolean isMapNotificable() throws RemoteException;
	/**
	 * @return true if character is ready to be notify
	 * @throws RemoteException
	 */
	public Boolean isCharacterNotificable() throws RemoteException;
	/**
	 * @return true if turn is ready to be notify
	 * @throws RemoteException
	 */
	public Boolean isTurnNotificable() throws RemoteException;

}
