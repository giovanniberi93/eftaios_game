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

	public Character getCharacter() throws RemoteException;
	public Map getMap() throws RemoteException;
	public String getPlayerTurn() throws RemoteException;
	public Boolean isMapNotificable() throws RemoteException;
	public Boolean isCharacterNotificable() throws RemoteException;
	public Boolean isTurnNotificable() throws RemoteException;

}
