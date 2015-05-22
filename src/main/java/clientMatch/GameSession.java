/**
 * 
 */
package clientMatch;

import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

/**
 * interface for managing the comunication with server
 * it recives command that will be sent to server
 *
 */
public interface GameSession {

	void setController(matchController matchController);

	public Character getCharacter();

	public String listenTurn();

	public Map getMap();


}
