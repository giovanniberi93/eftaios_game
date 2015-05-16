/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 *	Class representing the blank sector
 */
public class BlankSector extends Sector {

	public BlankSector() {
		super();
	}
	
	public void acceptDeck (DeckAssigner deckAssigner){
		deckAssigner.visit(this);
	}

}
