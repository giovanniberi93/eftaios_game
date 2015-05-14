package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.VisitableObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.gameNames.*;


public abstract class Character {
	
	private String name;
	private String role;
	private SideName side;
	private Coordinates currentPosition;
	protected VisitableObjectCard[] bag;
	protected boolean isAlive;

	protected int percorrableDistance;
	

	/**
	 * Construct a character from a characterName, and assign to the character the CharacterName attributes
	 * @param character
	 */
	public Character (CharacterName character){
		this.name = character.getPersonalName();
		this.role = character.getRoleName();
		this.setSide(character.getSide());
		bag = new VisitableObjectCard[3];		//TODO add constant; where?
		isAlive = true;
	}

	public boolean isAlive() {
		return isAlive;
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

	