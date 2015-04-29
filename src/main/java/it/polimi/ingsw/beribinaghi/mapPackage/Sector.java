/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

/**
 * @author damianobinaghi
 *
 */
public abstract class Sector {
	private Coordinates cordinate;
	
	public Sector(Coordinates cordinate)
	{
		this.cordinate = cordinate;
	}

	public Coordinates getCordinate() {
		return cordinate;
	}
	
}
