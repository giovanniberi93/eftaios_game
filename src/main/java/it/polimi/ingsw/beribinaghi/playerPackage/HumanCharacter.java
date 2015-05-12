package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;

/**
 * @author Giovanni Beri
 *
 * represent the human character; it has no difference in methods or attributes from AlienCharacter, it will 
 * be useful in the turn management
 */
public class HumanCharacter extends Character {

	public HumanCharacter(CharacterName character) {
		super(character);
		super.numberOfSteps = 1;		//TODO add constant; where?
	}

}
