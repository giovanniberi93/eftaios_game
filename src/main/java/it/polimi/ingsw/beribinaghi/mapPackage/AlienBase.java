/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
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
	public SectorCard pickFromAssociatedDeck(){
		return null;
	}

}
