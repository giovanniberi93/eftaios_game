package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;

/**
 * class representing the shallops deck
 *
 */
public class ShallopsDeck extends Deck {
		
	private final static int DAMAGEDSHALLOPSNUMBER = 3;
	private static final int NOTDAMAGEDSHALLOPSNUMBER = 3;
	
	/**
	 * Construcs a shallops deck with the right number of cards; an half of them are setted damaged, the other half is setted not damaged
	 */
	public ShallopsDeck(){
		for(int i = 0; i < DAMAGEDSHALLOPSNUMBER; i++)
			super.validCards.add(new ShallopCard(true));	
		for(int i = 0; i < NOTDAMAGEDSHALLOPSNUMBER; i++)
			super.validCards.add(new ShallopCard(false));
	}

}
