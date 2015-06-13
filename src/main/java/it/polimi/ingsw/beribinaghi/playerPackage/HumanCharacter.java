package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;

/**
 * @author Giovanni Beri
 *
 * represent the human character; it has no difference in methods or attributes from AlienCharacter, it will 
 * be useful in the turn management
 */
public class HumanCharacter extends Character {

	private static final int HUMANPERCORRABLEDISTANCE = 1;
	private static final long serialVersionUID = 1L;
	private boolean isEscaped = false;
	
	public HumanCharacter(CharacterName character) {
		super(character);
		super.percorrableDistance = HUMANPERCORRABLEDISTANCE;
		
	}

	public boolean isEscaped() {
		return isEscaped;
	}

	public void setEscaped(boolean isEscaped) {
		this.isEscaped = isEscaped;
	}

}
