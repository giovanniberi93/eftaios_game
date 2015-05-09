package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.nameGame.CharacterName;

/**
 * class representing the players deck
 *
 */

public class PlayersDeck extends Deck {	

	/**
	 * initialize the PlayerDeck with the exact number and type of necessary PlayerCards
	 * @param playerNumber is the number of players in the room
	 */
	public PlayersDeck(int playerNumber){
		
		super.validCards.add(new PlayerCard(CharacterName.FIRSTALIEN));
		super.validCards.add(new PlayerCard(CharacterName.CAPTAIN));
		super.validCards.add(new PlayerCard(CharacterName.SECONDALIEN));
		super.validCards.add(new PlayerCard(CharacterName.PILOT));
		super.validCards.add(new PlayerCard(CharacterName.THIRDALIEN));
		super.validCards.add(new PlayerCard(CharacterName.PSYCHOLOGIST));
		super.validCards.add(new PlayerCard(CharacterName.FOURTHALIEN));
		super.validCards.add(new PlayerCard(CharacterName.SOLDIER));
		
	/*	for(int i = MAXPLAYERS-1; i >= playerNumber; i--)
			//TODO declare MAXPLAYERS in MatchPackage
			super.validCards.remove(i);*/
		}
}
