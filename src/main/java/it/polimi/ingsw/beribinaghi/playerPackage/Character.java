package it.polimi.ingsw.beribinaghi.playerPackage;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.gameNames.*;


public abstract class Character implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String role;
	private SideName side;
	private Coordinates currentPosition;
	private ArrayList<ObjectCard> bag = new ArrayList<ObjectCard>();
	private boolean isAlive;

	protected int percorrableDistance;

	/**
	 * Construct a character from a characterName, and assign to the character the CharacterName attributes
	 * @param character
	 */
	public Character (CharacterName character){
		this.name = character.getPersonalName();
		this.setRole(character.getRoleName());
		this.setSide(character.getSide());
		this.setAlive(true);
		isAlive = true;
	}

	private void setRole(String roleName) {
		this.role = roleName;
	}

	public String getName() {
		return name;
		}

	
	public void setSide(SideName side) {
		this.side = side;
	}
	
	/**
	 * @return current position
	 */
	public Coordinates getCurrentPosition() {
		return currentPosition;
	}
	
	public boolean addCardToBag(ObjectCard pickedCard){
		if(bag.size() >= 3)
			return false;
		bag.add(pickedCard);
		return true;
	}

	public ObjectCard getCardFromBag (int index){
		return bag.get(index);
	}
	
	public ObjectCard removeCardFromBag(ObjectCard searchedCard){
		ObjectCard temporaryCard;
		for(int i = 0; i<3; i++){		//TODO constant
			temporaryCard = getCardFromBag(i);
			if(temporaryCard.getClass() == searchedCard.getClass()){
				searchedCard = temporaryCard;
				bag.remove(i);
				return searchedCard;
			}
		}
		return null;
	}

	/**
	 * sets the parameter as the new currentPosition
	 * @param currentPosition
	 */
	public void setCurrentPosition(Coordinates currentPosition) {
		this.currentPosition = currentPosition;
	}


	public SideName getSide() {
		return side;
	}

	public boolean isAlive() {
		return isAlive;
	}


	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
}

	
