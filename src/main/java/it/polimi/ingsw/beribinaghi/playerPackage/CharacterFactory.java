package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.gameNames.*;

/**
 * @author Giovanni Beri
 * Factory class to implement FactoryMethod for assignment of characters to players
 *
 */
public class CharacterFactory {
	
	/**
	 * factory method for alien or human characters
	 * @param characterName is the name of the character to assign
	 * @return HumanCharacter or AlienCharacter, according to the characterName
	 */
	public Character getNewCharacter(CharacterName characterName){
		Character character;
		if(characterName.getSide() == SideName.ALIEN)
			character = new AlienCharacter(characterName);
		else
			character = new HumanCharacter(characterName);
		
		return character;
	}
}
