/**
 * 
 */
package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;


/**
 * Class that manage a physical player
 *
 */
public class Player {
	
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
