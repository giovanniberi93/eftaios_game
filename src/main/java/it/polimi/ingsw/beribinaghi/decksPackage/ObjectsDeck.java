package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;

/**
 * Class representing the objects deck
 *
 */
public class ObjectsDeck extends Deck {
	
	@Override
	public ObjectCard pickCard(){
		return (ObjectCard) super.pickCard();
	}

}
