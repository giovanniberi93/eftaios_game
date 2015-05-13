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

	public void createNewMatch(String name);


}
