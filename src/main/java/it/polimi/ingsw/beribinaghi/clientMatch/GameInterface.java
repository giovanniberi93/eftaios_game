/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.gameNames.SideName;

/**
 * manages all communications during the game with user a graphic interface
 * receive command that will be presented to user
 */
public interface GameInterface {

	void setController(matchController matchController);

	void printCharacter(String name, String role, SideName side);

	void managesTurn();

	void notifyOthersTurn(String playerTurn);

}
