/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing the alien base sector
 */
public class AlienBase extends Sector{
	private static final long serialVersionUID = 1L;

	public AlienBase() {
		super();
	}
	
	public void acceptDeck (DeckAssigner deckAssigner){
		deckAssigner.visit(this);
	}
	
	@Override
	public SectorCard pickFromAssociatedDeck(){
		return new NothingToPick();
	}

}
