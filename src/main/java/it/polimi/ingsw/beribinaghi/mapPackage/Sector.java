/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.Deck;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing a coordinate. It is identified by a letter and a number
 */
public abstract class Sector implements Cloneable{
	private Deck associatedDeck;
	
	
	public void setAssociatedDeck(Deck associatedDeck) {
		this.associatedDeck = associatedDeck;
	}

	
	public void acceptDeck (DeckAssigner deckAssigner){}
	
	public Object clone() { 
		try { 
			return super.clone(); 
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); return null; 
		}
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
