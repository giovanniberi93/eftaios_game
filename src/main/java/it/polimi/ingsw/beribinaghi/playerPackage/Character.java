package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.gameNames.*;


public abstract class Character {
	
	private String name;
	private String role;
	private SideName side;
	private Coordinates currentPosition;
	protected ObjectCard[] bag;
	protected boolean isAlive;
	protected int numberOfSteps;
	

	/**
	 * Construct a character starting from a characterName
	 * @param character
	 */
	public Character (CharacterName character){
		this.name = character.getPersonalName();
		this.role = character.getRoleName();
		this.setSide(character.getSide());
		bag = new ObjectCard[3];		//TODO add constant; where?
		isAlive = true;
	}


	public void setSide(SideName side) {
		this.side = side;
	}


	public Coordinates getCurrentPosition() {
		return currentPosition;
	}


	public void setCurrentPosition(Coordinates currentPosition) {
		this.currentPosition = currentPosition;
	}


	public SideName getSide() {
		return side;
	}

}

	