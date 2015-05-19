package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing a safe sector
 */
public class SafeSector extends Sector{

	public SafeSector() {
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
