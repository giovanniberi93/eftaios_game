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
