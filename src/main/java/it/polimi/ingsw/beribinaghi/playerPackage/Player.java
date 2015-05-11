/**
 * 
 */
package it.polimi.ingsw.beribinaghi.playerPackage;

import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.gameNames.*;


/**
 * Class that manage a physical player
 *
 */
public class Player {
	
	private String userName;
	private Coordinates currentPosition; 
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


	/**
	 * @return current position
	 */
	public Coordinates getCurrentPosition() {
		return currentPosition;
	}


	/**
	 * sets the parameter as the new currentPosition
	 * @param currentPosition
	 */
	public void setCurrentPosition(Coordinates currentPosition) {
		this.currentPosition = currentPosition;
	}


	public Character getCharacter() {
		return character;
	}


	public void setCharacter(Character character) {
		this.character = character;
	}

}
