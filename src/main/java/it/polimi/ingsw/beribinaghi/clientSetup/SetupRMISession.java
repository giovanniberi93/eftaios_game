/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteConnectionManager;
import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteSetupSession;
import it.polimi.ingsw.beribinaghi.clientMatch.GameSessionClientSide;
import it.polimi.ingsw.beribinaghi.clientMatch.ClientRMISession;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * interface for communication with server via RMI
 *
 */
public class SetupRMISession implements SetupSession {
	private RemoteSetupSession session;
	private long timeWaiting = 100;
	private Registry registry;

	@Override
	public Boolean connect() {
		String name = "Connection";
		String assignedSession;
		try {
			registry = LocateRegistry.getRegistry(EscapeFromTheAliensInOuterSpace.RMIPORT);
			RemoteConnectionManager connection = (RemoteConnectionManager) registry.lookup(name);
			assignedSession = connection.connect();
			session = (RemoteSetupSession) registry.lookup(assignedSession);
			return true;
		} catch (RemoteException e) {
		} catch (NotBoundException e) {
		}
		return false;
	}

	@Override
	public void login(String userName) {
		try {
			session.login(userName);
		} catch (RemoteException e) {
		}
	}

	@Override
	public ArrayList<String> getMatchesName() {
		try {
			return session.printMatchName();
		} catch (RemoteException e) {
		}
		return null;
	}

	@Override
	public boolean createNewMatch(String matchName) {
		try {
			return session.createNewMatch(matchName);
		} catch (RemoteException e) {
		}
		return false;
	}

	@Override
	public int enterGame(String matchName) {
		try {
			return session.enterMatch(matchName);
		} catch (RemoteException e) {
		}
		return 3;
	}

	@Override
	public void close() {
		try {
			session.closeSession();
		} catch (RemoteException e) {
		}
	}

	@Override
	public ArrayList<String> getPlayer() {
		try {
			return session.playerInRoom();
		} catch (RemoteException e) {
		}
		return null;
	}

	@Override
	public synchronized boolean isStarted(SetupController setupController) {
		try {
			while (!session.isStarted() && !session.thereIsNewPlayer())
				try {
					this.wait(timeWaiting);
				} catch (InterruptedException e) {
				};
			if (session.isStarted())
				return true;
			ArrayList<String> newPlayers = session.playerNotNotify();
			for (String newPlayer:newPlayers)
				setupController.notifyNewPlayer(newPlayer);
		} catch (RemoteException e) {
		}
		return false;
	}

	@Override
	public void exitRoom() {
		try {
			session.closeSession();
		} catch (RemoteException e) {
		}
	}

	@Override
	public GameSessionClientSide startGameComunication() {
		try {
			RemoteGameSession remoteGameSession = (RemoteGameSession) registry.lookup(session.getStringBind());
			return new ClientRMISession(remoteGameSession);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		} catch (NotBoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Boolean closeAfterError() {
		try {
			session.stop();
			return true;
		} catch (RemoteException e) {
		}
		return false;
	}

	@Override
	public Boolean reconnect() {
		// TODO Auto-generated method stub
		return null;
	}

}
