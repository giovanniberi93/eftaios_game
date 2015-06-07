package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInAnySector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInYourSector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Silence;

/**
 * class representing the sectors deck
 *
 */
public class DangerousSectorsDeck extends Deck {
	
	private final static int NUMBERANYSECTORCARDS = 10;
	private final static int NUMBERYOURSECTORCARDS = 10;
	private final static int NUMBERSILENCECARDS = 10;
	private final static int NUMBEROBJECTSFORTYPE = 0;


	/**
	 * Construct the DangerousSectorsDeck with the right number and type of cards
	 */
	public DangerousSectorsDeck(){
		boolean containsObject;		//TODO add constants


		for(int i = 0; i<DangerousSectorsDeck.NUMBERYOURSECTORCARDS; i++){			
			if(i < DangerousSectorsDeck.NUMBEROBJECTSFORTYPE)
				containsObject = true;		//aggiungo carte rumore nel mio settore
			else
				containsObject = false;
			super.validCards.add(new NoiseInYourSector(containsObject));
		}
		for(int i = 0; i<DangerousSectorsDeck.NUMBERANYSECTORCARDS; i++){			
			if(i < DangerousSectorsDeck.NUMBEROBJECTSFORTYPE)
				containsObject = true;		//aggiungo carte rumore in ogni settore
			else
				containsObject = false;
			super.validCards.add(new NoiseInAnySector(containsObject));
		}
		for(int i = 0; i<DangerousSectorsDeck.NUMBERSILENCECARDS; i++){			
			containsObject = false;		//aggiungo carte silenzio
			super.validCards.add(new Silence(containsObject));
		}
	}
	
	@Override
	public Card pickCard(){
		Card pickedCard = super.pickCard();
		super.addToDiscardPile(pickedCard);
		return pickedCard;
	}
}
