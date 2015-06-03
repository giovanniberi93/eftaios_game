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
	 * @param playerTurn
	 * it notifies to user that is turn of playerTurn 
	 */
	void notifyOthersTurn(String playerTurn);

	/**
	 * shows in the graphicInterface the map of the match, showing also the position of the player who invokes it 
	 * @param map is the map of the match
	 * @param myCoordinates are the coordinates of the player who invokes the method
>>>>>>> branch 'master' of https://dam930@bitbucket.org/dam930/beribinaghi.git
	 */
	void printMap(Map map, Coordinates myCoordinates);

	/**
	 * Shows the cards picked after a movement in another sector
	 * @param pickedCards is the arrayList of the picked cards; it contains a sector card and possibly an object card
	 */
	void showPickedCards(ArrayList<Card> pickedCards);


	/**
	 * Shows to currentPlayer the cards contained in his bag; the player select the card and is called the method executeObjectCard with the correspondent arguments
	 * 
	 */
	void chooseObjectCard();

	/**
	 * Asks the user to choose a valid coordinate
	 * @return the selected coordinates
	 */
	Coordinates chooseAnyCoordinates();

	/**
	 * Shows the result of the attempted escape
	 * @param result is true if the the escape has been successful, false otherwise 
	 * @param coord are the coordinates of the tried shallop 
	 */
	void showEscapeResult(boolean result, Coordinates coord);

	/**
	 * Shows the position of the noise coming from currentPlayer
	 * @param noiseCoord
	 */
	void showNoise(Coordinates noiseCoord);

	/**
	 * Shows the result of an attack attempted by currentPlayer
	 * @param attackCoordinates are the coordinates of the attack
	 * @param killed is the arrayList containing the usernames of killed players
	 * @param survived is the arrayList containing the usernames of survived players
	 */
	void showAttackResult(Coordinates attackCoordinates, ArrayList<String> killed, ArrayList<String> survived);

	/**
	 * Shows position and username of a player caught by a spotligh
	 * @param username is the username of the spotted player
	 * @param position is the position of the spotted player
	 */
	void showSpottedPlayer(String username, Coordinates position);
	
	/**
	 * start with showing to user character, map and first turn's player
	 */
	void start();

	/**
	 * Shows a card used by currentPlayer
	 * @param card is the used card
	 * @param coord are the coordinates in which the card is applied (not null for spotlight only)
	 */
	void showUsedCard(ObjectCard card, Coordinates coord);

	/**
	 * Prints the number of the turn of the match
	 * @param turnNumber is the turn number
	 */
	void printTurnNumber(int turnNumber);

}
