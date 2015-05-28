/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

/**
 * interface for managing the communication with server
 * it receives command that will be sent to server
 *
 */
public interface GameSessionClientSide {

	public void setController(MatchController matchController);

	public Character getCharacter();

	public String listenTurn();

	public Map getMap();
	
	public ArrayList<Card> move(Coordinates destinationCoord) throws WrongSyntaxException;

	public void noise(Coordinates noiseCoordinates);
	
	public void useObjectcard(ArrayList<String> command);

	public void endTurn();

	public void listenUpdate();
}
