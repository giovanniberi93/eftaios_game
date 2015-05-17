package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

/**
 * Class representing a shallop sector
 */
public class ShallopSector extends Sector{
	
	public ShallopSector() {
		super();
	}
	
	public void acceptDeck (DeckAssigner deckAssigner){
		deckAssigner.visit(this);
	}
}
