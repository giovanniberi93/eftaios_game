/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.GameSessionClientSide;

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
	 * @return 
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
	 * @return result of operation
	 * lets player enter in the match
	 * return 0 if the operation is successful
	 * return 1 if name not exists
	 * return 2 if there are too many player in room
	 * return 3 match is just started
	 */
	public int enterGame(String matchName);

	/**
	 * Close the session
	 */
	public void close();

	/**
	 * @return the names of all player in room
	 */
	public ArrayList<String> getPlayer();

	/**
	 * @return true if match is started
	 * This function create also the Session of match
	 */
	boolean isStarted(SetupController setupController);

	/**
	 * exit rooms
	 */
	public void exitRoom();

	/**
	 * @return the Game session for managing communication with server during the match
	 */
	public GameSessionClientSide startGameComunication();

	/**
	 * Close a connection after a connection error
	 * @return 
	 */
	public Boolean closeAfterError();

	public Boolean reconnect();


}
