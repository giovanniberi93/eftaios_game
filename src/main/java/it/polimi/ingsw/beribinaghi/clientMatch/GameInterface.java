/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;

/**
 * manages all communications during the game with user a graphic interface
 * receive command that will be presented to user
 */
public interface GameInterface {
	
	void setController(MatchController matchController);

	void printCharacter(String name, String role, SideName side);

	void managesMyTurn();

	void notifyOthersTurn(String playerTurn);

	void printMap(Map map, Coordinates myCoordinates);

	void showPickedCard(ArrayList<Card> pickedCards);

	void manageNewObjectCard(ObjectCard objectCard);

	void showUsedCard(ObjectCard usedCard);

	void manageUsedObjectCard(ArrayList<String> command);

	void chooseObjectCard();

	Coordinates chooseAnyCoordinates();

	void showEscapeResult(boolean result, Coordinates coord);

	void showNoise(Coordinates noiseCoord);

	void showAttackResult(ArrayList<String> killed, ArrayList<String> survived);

	void showSpottedPlayer(String username, Coordinates position);

}
