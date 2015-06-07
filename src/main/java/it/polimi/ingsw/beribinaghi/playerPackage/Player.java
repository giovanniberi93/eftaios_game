/**
 * 
 */
package it.polimi.ingsw.beribinaghi.playerPackage;

import java.io.Serializable;


/**
 * Class that manage a physical player
 *
 */
public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private Character character;
	
	public Player(String user){
		userName = user;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((character == null) ? 0 : character.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		Player other = (Player) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}



	/**
	 * @return the user name
	 */
	public String getUser() {
		return userName;
	}


	public Character getCharacter() {
		return character;
	}


	public void setCharacter(Character character) {
		this.character = character;
	}

}
