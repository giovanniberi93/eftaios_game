package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;
import it.polimi.ingsw.beribinaghi.gameNames.*;

/**
 * class representing a player card
 *
 */
public class CharacterCard implements Card{

	private CharacterName characterName;
	
	public CharacterCard (CharacterName characterName){
		this.setCharacterName(characterName);
	}
	
	public void setCharacterName(CharacterName characterName) {
		this.characterName = characterName;
	}


	public CharacterName getCharacterName() {
		return characterName;
	}	
}
