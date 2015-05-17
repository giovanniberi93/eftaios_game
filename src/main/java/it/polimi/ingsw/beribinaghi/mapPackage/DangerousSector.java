/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing a dangerous sector
 */
public class DangerousSector extends Sector {

	public DangerousSector() {
		super();
	}
	
	public void acceptDeck (DeckAssigner deckAssigner){
		deckAssigner.visit(this);
	}
}
