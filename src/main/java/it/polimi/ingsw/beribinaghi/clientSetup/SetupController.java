/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

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
		manageMatches();
	}
	
	/**
	 * prints name match using graphic interface
	 */
	public void printMatch()
	{
		graphicInterface.printMatchesName(setupSession.getMatchesName());
	}

	private void manageMatches() {
		printMatch();
		graphicInterface.receiveCommand();
		
	}

	private void login() {
		setupSession.login(graphicInterface.getUserName());
	}

	public boolean create(String name) {
		return setupSession.createNewMatch(name);
	}

	public boolean enter(String matchName) {
		return setupSession.enterGame(matchName);
	}

}
