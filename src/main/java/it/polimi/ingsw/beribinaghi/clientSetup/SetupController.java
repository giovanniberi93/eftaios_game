/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import java.util.ArrayList;

/**
 * This class manages the setup of game
 *
 */
public class SetupController {
	private SetupSession setupSession;
	private GraphicInterface graphicInterface;
	
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
		setupSession.isStarted();
		graphicInterface.beginMatch();
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
		setupSession.login(graphicInterface.getUserName());
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

}
