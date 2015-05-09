/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

/**
 * 
 * This class manages all starting connection and all new match
 */
public abstract class RoomServer {
	private static ControllerMatch controllerMatch;
	
	public static ControllerMatch getControllerMatch() {
		return controllerMatch;
	}

	public RoomServer()
	{
		if (controllerMatch==null)
			controllerMatch = new ControllerMatch();
	}
	
	
	
}
