/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

/**
 * @author damianobinaghi
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
	
	public String getMatchName();

	public GameSessionServerSide getGameSession();
	
}
