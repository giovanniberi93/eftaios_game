package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;

/**
 * @author Giovanni Beri
 * 
 * represent the alien character; it has no difference in methods or attributes from HumanCharacter, it will 
 * be useful in the turn management
 */
public class AlienCharacter extends Character {
	
	public AlienCharacter(CharacterName character) {
		super(character);
		super.percorrableDistance = 2;	//TODO add constant; where?
	}

}
