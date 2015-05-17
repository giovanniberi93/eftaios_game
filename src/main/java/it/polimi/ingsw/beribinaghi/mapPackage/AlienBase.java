/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing the alien base sector
 */
public class AlienBase extends Sector{

	public AlienBase() {
		super();
	}
	
	public void acceptDeck (DeckAssigner deckAssigner){
		deckAssigner.visit(this);
	}
	
	@Override
	public Card pickFromAssociatedDeck(){
		return null;
	}

}
