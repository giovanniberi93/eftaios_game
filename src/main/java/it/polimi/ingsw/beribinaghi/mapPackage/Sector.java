/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

/**
 * @author damianobinaghi
 * Class rapresenting a cordinate. It is identificate by a letter and a number
 */
public abstract class Sector {
	private Coordinates coordinates;
	
	public Sector(Coordinates coordinates)
	{
		this.coordinates = coordinates;
	}
	
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
	
}
