/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;

/**
 * manages all communications during the game with user a graphic interface
 * receive command that will be presented to user
 */
public interface GameInterface {
	
	/**
	 * @param matchController
	 * it sets the match controller
	 * the interface use the controller for something operation
	 */
	void setController(MatchController matchController);

	/**
	 * communicate to user that is his turn and it gives to him something action
	 */
	void managesMyTurn();

	/**
	 * @param playerTurn
	 * it notifies to user that is turn of playerTurn 
	 */
	void notifyOthersTurn(String playerTurn);

	void printMap(Map map, Coordinates myCoordinates);

	void showPickedCards(ArrayList<Card> pickedCards);

	void manageUsedObjectCard(ArrayList<String> command);

	void chooseObjectCard();

	Coordinates chooseAnyCoordinates();

	void showEscapeResult(boolean result, Coordinates coord);

	void showNoise(Coordinates noiseCoord);

	void showAttackResult(Coordinates attackCoordinates, ArrayList<String> killed, ArrayList<String> survived);

	void showSpottedPlayer(String username, Coordinates position);

	void manageSectorCard(Card card);

	void start();

	void showUsedCard(ObjectCard card, Coordinates coord);

}
