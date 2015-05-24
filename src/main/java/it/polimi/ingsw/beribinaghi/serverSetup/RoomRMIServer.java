/**
 * 
 */
package it.polimi.ingsw.beribinaghi.serverSetup;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteConnectionManager;
import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteSetupSession;
/**
 * Room server with RMI
 * this class manages all new communication with server using RMI
 */
public class RoomRMIServer extends RoomServer implements RemoteConnectionManager{
	private Registry registry;
	private String name = "Connection";	
	private int connectionNumber = 0;
	
	public RoomRMIServer(int portRMI) throws RemoteException {	                  
        registry = LocateRegistry.createRegistry(portRMI);  
        super.setActive();
	}
	
	public void setInactive() {
		super.setInactive();
		unlock();
	}

	private void unlock() {
		try {
			registry.unbind(name);
		} catch (AccessException e) {
		} catch (RemoteException e) {
		} catch (NotBoundException e) {
		}
	}
	
	/**
	 * Set the server active
	 */
	public synchronized void setActive() {
		super.setActive(); 
		try {
			RemoteConnectionManager stub = (RemoteConnectionManager) UnicastRemoteObject.exportObject(this, 0); 
			registry.bind(name, stub);
		} catch (AccessException e) {
		} catch (RemoteException e) {
		} catch (AlreadyBoundException e) {
		}
	}
	
	/**
	 * end server
	 */
	public void exit(){
		end = true;
		if (this.isActive())
			this.setInactive();
		unlock();
	}

	@Override
	public void run() {
		if (this.isActive())
			try {
			 	RemoteConnectionManager stub = (RemoteConnectionManager) UnicastRemoteObject.exportObject(this, 0); 
				registry.bind(name, stub);
			} catch (AccessException e) {
			} catch (RemoteException e) {
			} catch (AlreadyBoundException e) {
			}
	}

	@Override
	public String connect() {
		String nameBind = "session" + connectionNumber;
		connectionNumber++;
		try {
			RemoteSetupSession stub = (RemoteSetupSession) UnicastRemoteObject.exportObject(new SetupRMISession(RoomServer.getControllerMatch(),nameBind,registry), 0); 
			registry.bind(nameBind, stub);
		} catch (AccessException e) {
		} catch (RemoteException e) {
		} catch (AlreadyBoundException e) {
		}
		return nameBind;
	}

}
