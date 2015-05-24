/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
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

	void manageSectorCard(Card card);

	void manageNewObjectCard(Card card);

}
