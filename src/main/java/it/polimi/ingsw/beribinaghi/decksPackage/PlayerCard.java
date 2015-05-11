package it.polimi.ingsw.beribinaghi.decksPackage;
import it.polimi.ingsw.beribinaghi.gameNames.*;

/**
 * class representing a player card
 *
 */
public class PlayerCard implements Card{

	CharacterName characterName;
	
	public PlayerCard (CharacterName characterName){
		this.characterName = characterName;
	}
	
}
