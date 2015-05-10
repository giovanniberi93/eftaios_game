/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

/**
 * 
 * This class manages all starting connection and all new match
 */
public abstract class RoomServer extends Thread {
	private static ControllerMatch controllerMatch;
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
	
	public static ControllerMatch getControllerMatch() {
		return controllerMatch;
	}

	public RoomServer()
	{
		if (controllerMatch==null)
			controllerMatch = new ControllerMatch();
	}
	
	public abstract void run();
	
}
