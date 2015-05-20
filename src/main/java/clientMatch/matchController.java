/**
 * 
 */
package clientMatch;

import it.polimi.ingsw.beribinaghi.playerPackage.Character;


/**
 * this class manages all match, it manages all communications with network and graphics and it manages the model
 *
 */
public class matchController {
	private GameInterface graphicInterface;
	private GameSession session;
	private String myPlayerName;
	private Character myCharacter;

	public matchController(String playerName, GameInterface graphicInterface, GameSession session) {
		this.graphicInterface = graphicInterface;
		this.session = session;
		this.graphicInterface.setController(this);
		this.session.setController(this);
		this.myPlayerName = playerName;
		myCharacter = session.getCharacter();
		graphicInterface.printCharacter(myCharacter.getName(),myCharacter.getRole(),myCharacter.getSide());
		turn();
	}

	private void turn() {
		String playerTurn = session.listenTurn();
		if (playerTurn.equals(myPlayerName))
			graphicInterface.managesTurn();
			
	}

}
