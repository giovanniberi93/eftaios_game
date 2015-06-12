
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.MatchController;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class manages the setup of game
 *
 */
public class SetupController {
	private SetupSession setupSession;
	private GraphicInterface graphicInterface;
	private String playerName;
	private String matchName;
	private boolean firstTime = true;
	
	public String getMatchName() {
		return matchName;
	}
	
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param setupSession
	 * create a Setup Controller that communicates with SetupSession and GraphicsInterface
	 */
	public SetupController(GraphicInterface graphicInterface,SetupSession setupSession) {
		this.graphicInterface = graphicInterface;
		this.setupSession = setupSession;
		graphicInterface.setSetupController(this);
		graphicInterface.start();
		connect();
	}
	
	/**
	 * Connect to the server
	 */
	public void connect(){
		if (!setupSession.connect())
			graphicInterface.signalConnessionError();
		else
			graphicInterface.signalConnessionSuccess();
	}
	
	/**
	 * Signal to network that player is in room
	 */
	public void inRoom() {
		while (!setupSession.isStarted(this))
		{
		}
		try{
			new MatchController(this,playerName,graphicInterface.beginMatch(),setupSession.startGameComunication());
		}
		catch(NoSuchElementException e){
			this.handleConnectionProblem();
		}
		catch(NullPointerException e){
			this.handleConnectionProblem();
		}
	}

	/**
	 * prints name match using graphic interface
	 */
	public ArrayList<String> getMatchesName()
	{
		return setupSession.getMatchesName();
	}
	
	/**
	 * Close connection with server
	 */
	public void closeConnection(){
		setupSession.close();
	}

	/**
	 * send user to the session
	 */
	public void login(String user) {
		playerName = user;
		setupSession.login(user);
	}

	public boolean create(String name) {
		this.matchName = name;
		return setupSession.createNewMatch(name);
	}

	public int enter(String matchName) {
		this.matchName = matchName;
		return setupSession.enterGame(matchName);
	}

	/**
	 * request the others player name in the match
	 * @return 
	 */
	public ArrayList<String> getPlayersName() {
		return setupSession.getPlayer();
		
	}

	/**
	 * exits room
	 */
	public void exitRoom() {
		setupSession.exitRoom();
	}

	/**
	 * @param nextLine
	 * notify to setup controller and to interface that there is a new player in room
	 */
	public void notifyNewPlayer(String nextLine) {
		graphicInterface.notifyNewPlayer(nextLine);
	}

	public boolean isFirstTime() {
		return firstTime;
	}

	public void reconnect() {
		if (!this.setupSession.closeAfterError()){
			graphicInterface.signalConnessionError();
		}
	}

	public void handleConnectionProblem() {
		graphicInterface.signalConnectionDown();
		if (!this.setupSession.closeAfterError()){
			firstTime = false;
			graphicInterface.signalConnessionError();
		}
		graphicInterface.printMatchesName();
	}


}
