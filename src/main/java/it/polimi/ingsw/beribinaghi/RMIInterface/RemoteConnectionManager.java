/**
 * 
 */
package it.polimi.ingsw.beribinaghi.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI interface that manages the connection
 *
 */
public interface RemoteConnectionManager extends Remote {
	public String connect() throws RemoteException;
}
