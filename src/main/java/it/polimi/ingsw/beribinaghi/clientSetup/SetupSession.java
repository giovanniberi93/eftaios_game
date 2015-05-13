/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import java.util.ArrayList;

/**
 * interface for communication with server
 *
 */
public interface SetupSession {

	/**
	 * connects to the server
	 * @return 
	 */
	public Boolean connect();

	/**
	 * @param userName
	 * sends user name to server
	 */
	public void login(String userName);

	/**
	 * @return the list of inactive matches name 
	 */
	public ArrayList<String> getMatchesName();

	/**
	 * @param matchName
	 * @return false if matchName already exists
	 * creates a new match and associates the player to the match
	 */
	public boolean createNewMatch(String matchName);

	/**
	 * @param matchName
	 * @return true if match exists
	 * lets player enter in the match
	 */
	public boolean enterGame(String matchName);


}
