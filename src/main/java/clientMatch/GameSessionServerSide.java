/**
 * 
 */
package clientMatch;

import it.polimi.ingsw.beribinaghi.playerPackage.Character;

/**
 * interface for managing the comunication with server
 * it recives command that will be sent to server
 *
 */
public interface GameSessionServerSide {

	void setController(matchController matchController);

	Character getCharacter();

	String listenTurn();


}
