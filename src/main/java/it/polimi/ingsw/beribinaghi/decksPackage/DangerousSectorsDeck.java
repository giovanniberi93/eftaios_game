package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInAnySector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInYourSector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Silence;

/**
 * class representing the sectors deck
 *
 */
public class DangerousSectorsDeck extends Deck {

	private static DangerousSectorsDeck instance = null;
	/**
	 * Construct the DangerousSectorsDeck with the right number and type of cards
	 */
	
	public static DangerousSectorsDeck getInstance() {
		if(instance == null)
			instance = new DangerousSectorsDeck();
		return instance;
	}
	
	public DangerousSectorsDeck(){
		boolean containsObject;		//TODO add constants
		
		for(int i = 0; i<10; i++){			
			if(i < 4)
				containsObject = true;		//aggiungo carte rumore nel mio settore
			else
				containsObject = false;
			super.validCards.add(new NoiseInYourSector(containsObject));
		}
			
		for(int i = 0; i<10; i++){			
			if(i < 4)
				containsObject = true;		//aggiungo carte rumore in ogni settore
			else
				containsObject = false;
			super.validCards.add(new NoiseInAnySector(containsObject));
		}
		
		for(int i = 0; i<10; i++){			
			containsObject = false;		//aggiungo carte silenzio
			super.validCards.add(new Silence(containsObject));
		}
		
	}

}
