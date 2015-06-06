package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;

/**
 * @author Giovanni Beri
 * 
 * represent the alien character; it has no difference in methods or attributes from HumanCharacter, it will 
 * be useful in the turn management
 */
public class AlienCharacter extends Character {
	private static final long serialVersionUID = 1L;
	private boolean isStrong;

	public AlienCharacter(CharacterName character) {
		super(character);
		super.percorrableDistance = 2;	//TODO add constant; where?
	}
	
	public int getPercorrableDistance() {
		if(isStrong)
			return percorrableDistance+1;
		return percorrableDistance;
	}

	public boolean isStrong() {
		return isStrong;
	}

	public void setStrong(boolean isStrong) {
		this.isStrong = isStrong;
	}

}
