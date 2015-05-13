/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

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

	public void printMatchesName(ArrayList<String> matchesName);

	public void receiveCommand();

	void setSetupController(SetupController setupController);

}
