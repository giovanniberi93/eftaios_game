/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.Deck;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;

/**
 * @author damianobinaghi
 * Class rapresenting a cordinate. It is identificate by a letter and a number
 */
public abstract class Sector {
	private Coordinates coordinates;
	private Deck associatedDeck;
	
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
	
	/**
	 * Pick a Card type picked from the associated Deck: ShallopCard for ShallopSector, 
	 * SectorCard from DangerousSector, null for SafeSector
	 * @return the picked Card
	 */
	public Card pickFromAssociatedDeck(){
		return associatedDeck.pickCard();
	}
	
}
