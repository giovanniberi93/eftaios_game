/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

/**
 * Thread that wait begin match in GUI
 *
 */
public class inRoomThread extends Thread {
	private SetupController setupController;
	
	public inRoomThread(SetupController setupController) {
		this.setupController = setupController;
	}
	
	public void run(){
		setupController.inRoom();
	}
}
