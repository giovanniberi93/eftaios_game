/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.GameInterface;


/**
 *  interface for graphics command
 *
 */
public interface GraphicInterface {

	/**
	 * @return the result of connection
	 */
	public void signalConnessionError();

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

	/**
	 * signal to user that the connection is successful made
	 */
	public void signalConnessionSuccess();

	/**
	 * start with the presentation to user pre match. It can contains intro video or something else
	 */
	public void start();


}
