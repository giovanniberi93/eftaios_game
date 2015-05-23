/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

/**
 * interface for managing the comunication with server
 * it recives command that will be sent to server
 *
 */
public interface GameSessionClientSide {

	void setController(MatchController matchController);

	public Character getCharacter();

	public String listenTurn();

	public Map getMap();


}
