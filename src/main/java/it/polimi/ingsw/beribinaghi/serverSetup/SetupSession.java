/**
 * 
 */
package it.polimi.ingsw.beribinaghi.serverSetup;

import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;

/**
 * interface for communication between matchController and Socket or RMI Session
 *
 */
public interface SetupSession {

	/**
	 * notify to client that match is starting
	 */
	public void startMatch();

	/**
	 * notify to client that there is a new player in room
	 * @param namePlayer 
	 */
	public void notifyNewPlayer(String namePlayer);
	
	/**
	 * @return all inactive match name 
	 */
	public String getMatchName();

	/**
	 * @return the gameSession
	 */
	public GameSessionServerSide getGameSession();
	
}
