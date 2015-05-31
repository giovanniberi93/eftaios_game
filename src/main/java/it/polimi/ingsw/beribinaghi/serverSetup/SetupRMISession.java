/**
 * 
 */
package it.polimi.ingsw.beribinaghi.serverSetup;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteSetupSession;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.ServerRMISession;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

/**
 * Class that manage a session with a new client using Socket
 * 
 */
public class SetupRMISession implements SetupSession,RemoteSetupSession {

	private MatchController matchController;
	private Boolean active;
	private Boolean newPlayer;
	private Player player;
	private String matchName;
	private String myName;
	private String nameStubGame;
	private ArrayList<String> playerNotNotify;
	private Registry registry;

	public SetupRMISession(MatchController matchController, String myName,Registry registry) {
		this.matchController = matchController;
	    this.matchController.registerSession(this);
	    this.myName = myName;
	    this.registry = registry;
	    active = false;
	    newPlayer = false;
	    playerNotNotify = new ArrayList<String>();
	}
	
	@Override
	public void startMatch() {
		active = true;
	}

	@Override
	public void notifyNewPlayer(String namePlayer) {
		newPlayer = true;
		playerNotNotify.add(namePlayer);
	}

	@Override
	public String getMatchName() {
		return matchName;
	}

	@Override
	public GameSessionServerSide getGameSession() {
		try {
			nameStubGame = "game" + myName.substring(7, myName.length()); //game e il numero di stub
			ServerRMISession rmiSession = new ServerRMISession((matchController.getMatch(this.matchName)).getMatch(),player);
			RemoteGameSession stub = (RemoteGameSession) UnicastRemoteObject.exportObject(rmiSession, 0); 
			registry.bind(nameStubGame, stub);
			return rmiSession;
		} catch (NotExistingNameException e) {
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		} catch (AlreadyBoundException e) {
		}
		return null;
	}

	@Override
	public void login(String user) {
		player = new Player(user);
		
	}

	@Override
	public ArrayList<String> printMatchName() {
		return matchController.getMatchesName();
	}

	@Override
	public Boolean createNewMatch(String matchName) {
		try {
			matchController.createNewMatch(matchName,player);
			this.matchName = matchName;
			return true;
		} catch (AlreadyExistingNameException e) {
		}
		return false;
	}

	@Override
	public int enterMatch(String matchName) {
		try {
			matchController.addPlayer(matchName, player);
			this.matchName = matchName;
			return 0;
		} catch (NotExistingNameException e) {
			return 1;
		} catch (TooManyPlayerException e) {
			return 2;
		} catch (MatchJustStartingException e) {
		}
		return 3;
	}

	@Override
	public ArrayList<String> playerInRoom() {
		try {
			return matchController.getPlayer(matchName);
		} catch (NotExistingNameException e) {
		}
		return null;
	}

	@Override
	public void closeSession() {
		try {
			Registry registry = LocateRegistry.getRegistry();
			registry.unbind(myName);
		} catch (RemoteException e) {
		} catch (NotBoundException e) {
		}
	}

	@Override
	public Boolean isStarted() {
		return active;
	}

	@Override
	public Boolean thereIsNewPlayer() {
		return newPlayer;
	}

	@Override
	public ArrayList<String> playerNotNotify() {
		newPlayer = false;
		ArrayList<String> playerToSend = playerNotNotify;
		playerNotNotify = new ArrayList<String>();
		return playerToSend;
	}

	@Override
	public String getStringBind(){
		return nameStubGame;
	}

	@Override
	public Boolean isConnected() {
		// TODO Auto-generated method stub
		return null;
	}


}
