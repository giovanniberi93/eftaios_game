/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.GameInterface;

import java.util.ArrayList;

/**
 *  interface for graphics command
 *
 */
public interface GraphicInterface {

	/**
	 * @return the result of connection
	 */
	public boolean signalConnessionError();

	/**
	 * @return the user name
	 * asks to user his user name
	 */
	public String getUserName();

	/**
	 * @param matchesName
	 * print match name
	 */
	public void printMatchesName(ArrayList<String> matchesName);

	/**
	 * @return true if the command is not exit
	 * Use functions of matchController
	 * needs to set matchController before
	 */
	public Boolean receiveCommand();

	/**
	 * @param setupController
	 * Set setupController
	 */
	void setSetupController(SetupController setupController);

	/**
	 * receive command when you are in romm
	 */
	public void receiveCommandInRoom();

	/**
	 * communicates to user that match in began
	 * @return 
	 */
	public GameInterface beginMatch();

	/**
	 * notify that a new player is in the room
	 */
	public void notifyNewPlayer(String namePlayer);


}
