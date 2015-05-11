/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

/**
 * 
 * This class manages all starting connection and all new match
 */
public abstract class RoomServer extends Thread {
	private static MatchController controllerMatch;
	private Boolean active;
	protected Boolean end;	
	
	/**
	 * @return the status of the server
	 */
	public Boolean isActive() {
		return active;
	}
	
	/**
	 * Set the server inactive
	 */
	public void setInactive() {
		this.active = false;
	}

	/**
	 * Set the server active
	 */
	public void setActive() {
		this.active = true;
	}
	
	public static MatchController getControllerMatch() {
		return controllerMatch;
	}

	public RoomServer()
	{
		if (controllerMatch==null)
			controllerMatch = new MatchController();
	}
	
	public abstract void run();
	
}
