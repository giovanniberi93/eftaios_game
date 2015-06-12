package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;

/**
 * class representing the shallops deck
 *
 */
public class ShallopsDeck extends Deck {
		
	private final static int DAMAGEDSHALLOPSNUMBER = 3;
	private static final int NOTDAMAGEDSHALLOPSNUMBER = 3;
	
	public ShallopsDeck(){
		for(int i = 0; i < DAMAGEDSHALLOPSNUMBER; i++)
			super.validCards.add(new ShallopCard(true));	
		for(int i = 0; i < NOTDAMAGEDSHALLOPSNUMBER; i++)
			super.validCards.add(new ShallopCard(false));
	}

}
