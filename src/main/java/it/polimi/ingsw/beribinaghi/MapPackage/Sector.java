/**
 * 
 */
package it.polimi.ingsw.beribinaghi.MapPackage;

/**
 * @author damianobinaghi
 *
 */
public abstract class Sector {
	private Cordinate cordinate;
	
	public Sector(Cordinate cordinate)
	{
		this.cordinate = cordinate;
	}

	public Cordinate getCordinate() {
		return cordinate;
	}
	
}
