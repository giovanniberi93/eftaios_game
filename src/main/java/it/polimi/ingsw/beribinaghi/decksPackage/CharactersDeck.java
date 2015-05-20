package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.App;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.CharacterCard;
import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;

/**
 * class representing the players deck
 *
 */

public class CharactersDeck extends Deck {	
	
	/**
	 * initialize the PlayerDeck with the exact number and type of necessary PlayerCards
	 * @param playerNumber is the number of players in the room
	 */
	public CharactersDeck(int playerNumber){
		
		super.validCards.add(new CharacterCard(CharacterName.FIRSTALIEN));
		super.validCards.add(new CharacterCard(CharacterName.CAPTAIN));
		super.validCards.add(new CharacterCard(CharacterName.SECONDALIEN));
		super.validCards.add(new CharacterCard(CharacterName.PILOT));
		super.validCards.add(new CharacterCard(CharacterName.THIRDALIEN));
		super.validCards.add(new CharacterCard(CharacterName.PSYCHOLOGIST));
		super.validCards.add(new CharacterCard(CharacterName.FOURTHALIEN));
		super.validCards.add(new CharacterCard(CharacterName.SOLDIER));
		
		for(int i = App.MAX_PLAYER-1; i >= playerNumber; i--)	
			super.validCards.remove(i);
		}
}
