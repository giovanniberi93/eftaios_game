package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing a shallop sector
 */
public class ShallopSector extends Sector{
	private boolean isAlreadyUsed = false;
	
	public boolean isAlreadyUsed() {
		return isAlreadyUsed;
	}

	public void setAlreadyUsed(boolean isAlreadyUsed) {
		this.isAlreadyUsed = isAlreadyUsed;
	}

	public ShallopSector() {
		super();
	}
	
	public void acceptDeck (DeckAssigner deckAssigner){
		deckAssigner.visit(this);
	}
	
	@Override
	public SectorCard pickFromAssociatedDeck(){
		if(isAlreadyUsed)
			return null;
		return super.pickFromAssociatedDeck();
	}
}
