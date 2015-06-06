package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;

/**
 * class representing the shallops deck
 *
 */
public class ShallopsDeck extends Deck {
		
	/**
	 * Construct the DangerousSectorsDeck with the right number and type of cards
	 */
	public ShallopsDeck(){
		for(int i = 0; i<3; i++)
			super.validCards.add(new ShallopCard(true));	
		for(int i = 0; i<3; i++)
			super.validCards.add(new ShallopCard(true));		//TODO aggiusta
	}

}
