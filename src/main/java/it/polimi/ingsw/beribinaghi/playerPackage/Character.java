package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Attack;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Spotlight;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;
import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class Character implements Serializable{ 
	private static final long serialVersionUID = 1L;
	private String name;
	private String role;
	private SideName side;
	private Coordinates currentPosition;
	private ArrayList<ObjectCard> bag = new ArrayList<ObjectCard>();
	private boolean isAlive;
	private boolean hasAdrenalin;

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
		bag.add(new Spotlight());
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bag == null) ? 0 : bag.hashCode());
		result = prime * result
				+ ((currentPosition == null) ? 0 : currentPosition.hashCode());
		result = prime * result + (hasAdrenalin ? 1231 : 1237);
		result = prime * result + (isAlive ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + percorrableDistance;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((side == null) ? 0 : side.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (bag == null) {
			if (other.bag != null)
				return false;
		} else if (!bag.equals(other.bag))
			return false;
		if (currentPosition == null) {
			if (other.currentPosition != null)
				return false;
		} else if (!currentPosition.equals(other.currentPosition))
			return false;
		if (hasAdrenalin != other.hasAdrenalin)
			return false;
		if (isAlive != other.isAlive)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (percorrableDistance != other.percorrableDistance)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (side != other.side)
			return false;
		return true;
	}


	public String toString(){
		String character = new String(name + ", " + role);
		return character;
	}
	
	public void setHasAdrenalin(boolean hasAdrenalin) {
		this.hasAdrenalin = hasAdrenalin;
	}

	

	public int getPercorrableDistance() {
		if(hasAdrenalin == true){
			hasAdrenalin = false;
			return percorrableDistance+1;
		}
		return percorrableDistance;
	}


	private void setRole(String roleName) {
		this.role = roleName;
	}

	public String getName() {
		return name;
		}

	
	public void setPercorrableDistance(int percorrableDistance) {
		this.percorrableDistance = percorrableDistance;
	}


	public void setSide(SideName side) {
		this.side = side;
	}
	
	
	public ArrayList<ObjectCard> getBag() {
		return bag;
	}

	public int getBagSize(){
		return bag.size();
	}
	/**
	 * @return current position
	 */
	public Coordinates getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * add the passed card to the character bag
	 * @param pickedCard is the object card added
	 * @return true if the bag is overloaded
	 */
	public boolean addCardToBag(ObjectCard pickedCard){
		bag.add(pickedCard);
		if(bag.size() > 3)		//TODO costante
			return true;
		return false;
	}

	public ObjectCard getCardFromBag (int index){
		return bag.get(index);
	}
	
	/**
	 * Search a passed object card in the character's bag
	 * @param searchedCard is the searched Object card
	 * @return true if the card is found in the bag
	 */
	public boolean removeCardFromBag(ObjectCard searchedCard){
		ObjectCard temporaryCard;
		for(int i = 0; i< bag.size() ; i++){		
			temporaryCard = getCardFromBag(i);
			if(temporaryCard.getClass().equals(searchedCard.getClass())){
				searchedCard = temporaryCard;
				bag.remove(i);
				return true;
			}
		}
		return false;
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

	
