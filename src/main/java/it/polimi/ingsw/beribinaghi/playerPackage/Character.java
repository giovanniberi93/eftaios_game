package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.gameNames.*;


public abstract class Character {
	
	private String name;
	private String role;
	private Coordinates currentPosition;
	protected ObjectCard[] bag;
	protected boolean isAlive;
	protected int numberOfSteps;
	

	/**
	 * Construct a character starting from 
	 * @param character
	 */
	public Character (CharacterName character){
		this.name = character.getPersonalName();
		this.role = character.getRoleName();
		bag = new ObjectCard[3];		//TODO add constant; where?
		isAlive = true;
	}


	public Coordinates getCurrentPosition() {
		return currentPosition;
	}


	public void setCurrentPosition(Coordinates currentPosition) {
		this.currentPosition = currentPosition;
	}
}

	