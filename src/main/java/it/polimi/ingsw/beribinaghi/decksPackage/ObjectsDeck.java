package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Attack;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Spotlight;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;

/**
 * Class representing the objects deck
 *
 */
/**
 * Constructs the objects deck with the standard number of adrenalin, attack, defense, spotlight, teleport and sedatives cards
 *
 */
public class ObjectsDeck extends Deck {
	
	private final static int ADRENALINNUMBER = 2;			//2,2,3,1,2,2
	private final static int ATTACKNUMBER = 0;
	private final static int SEDATIVESNUMBER = 0;
	private final static int DEFENSENUMBER = 1;
	private final static int SPOTLIGHTNUMBER = 0;
	private final static int TELEPORTNUMBER = 0;
	
	public ObjectsDeck(){
		for(int i = 0;i < ADRENALINNUMBER; i++)
			super.validCards.add(new Adrenalin());
		for(int i = 0;i < ATTACKNUMBER; i++)
			super.validCards.add(new Attack());
		for(int i = 0;i < SEDATIVESNUMBER; i++)
			super.validCards.add(new Sedatives());
		for(int i = 0;i < DEFENSENUMBER; i++)
			super.validCards.add(new Defense());
		for(int i = 0;i < SPOTLIGHTNUMBER; i++)
			super.validCards.add(new Spotlight());
		for(int i = 0;i < TELEPORTNUMBER; i++)
			super.validCards.add(new Teleport());			
	}
	
	@Override
	public ObjectCard pickCard(){
		return (ObjectCard) super.pickCard();
	}
	
	public boolean isEmpty(){
		//if (super.validCards.size() == 0)
		if (super.validCards.size() == 0 && super.getDiscardPileSize() == 0)
			return true;
		return false;
	}

}
