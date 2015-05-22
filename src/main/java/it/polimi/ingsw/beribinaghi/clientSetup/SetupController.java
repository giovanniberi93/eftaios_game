/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.matchController;

import java.util.ArrayList;

/**
 * This class manages the setup of game
 *
 */
public class SetupController {
	private SetupSession setupSession;
	private GraphicInterface graphicInterface;
	private String playerName;
	
	/**
	 * @param setupSession
	 * create a Setup Controller that communicates with SetupSession and GraphicsInterface
	 */
	public SetupController(GraphicInterface graphicInterface,SetupSession setupSession) {
		this.graphicInterface = graphicInterface;
		this.setupSession = setupSession;
		graphicInterface.setSetupController(this);
		start();
	}

	private void start() {
		boolean retry = true;
		while (retry && !setupSession.connect())
			retry = graphicInterface.signalConnessionError();
		if (!retry)
			return ;
		login();
		while (true)
		{
			if (manageMatches())
				inRoom();
			else
				break;
		}
	}	
	
	private void inRoom() {
		while (!setupSession.isStarted(this))
		{
		}
		new matchController(playerName,graphicInterface.beginMatch(),setupSession.startGameComunication());
	}

	/**
	 * prints name match using graphic interface
	 */
	public void printMatch()
	{
		graphicInterface.printMatchesName(setupSession.getMatchesName());
	}

	private Boolean manageMatches() {
		printMatch();
		if (!graphicInterface.receiveCommand())
		{
			setupSession.close();
			return false;
		}
		return true;
	}

	private void login() {
		playerName = graphicInterface.getUserName();
		setupSession.login(playerName);
	}

	public boolean create(String name) {
		return setupSession.createNewMatch(name);
	}

	public int enter(String matchName) {
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

	public void notifyNewPlayer(String nextLine) {
		graphicInterface.notifyNewPlayer(nextLine);
	}

}
