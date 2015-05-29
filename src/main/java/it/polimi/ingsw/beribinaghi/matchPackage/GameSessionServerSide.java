/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * manages all communication with client. It receives command from client and observe update object
 *
 */
public abstract class GameSessionServerSide implements Observer {
	protected Player player;
	protected Match match;

	
	@Override
	public void update(Observable o, Object arg) {
		String line = (String) arg;	
		if (line.equals("turn"))
			this.notifyBeginTurn();
		else if (line.equals("attack")){
			this.notifyAttackResult();
		}
		else if (line.equals("escape")){
			this.notifyEscape();
		}
		else if (line.equals("noise")){
			this.notifyNoise();
		}
		else if (line.equals("spotlight")){
			this.notifySpotted();
		}
		else if (line.equals("card")){
			this.notifyCard();
		}
		else if (line.equals("endMatch")){
			this.notifyEndMatch();
		}
	}

	protected void myTurn() {}
	
	/**
	 * Notifies to all players the end of the running match with the String "endMatch"
	 */
	protected void notifyEndMatch() {}

	/**
	 * Notifies to all players an object used by current player with the string "card=usedCard.toString()"
	 * @param string is usedCard.toString(); represent the used card 
	 */
	protected void notifyCard() {}
	
	/**
	 * Notifies to all players the position of the players spotted by a spotlight with the string "spotted=username&coordinatesLetter&coordinatesNumber="
	 * @param spottedPlayers contains the usernames of the spotted players and the coordinates of their position;
	 */
	protected void notifySpotted(){}

	/**
	 * Notifies to all players the position of the noise signaled by the current player with the string "noise=coordinatesLetter=coordinatesNumber"
	 * @param string contains a string representing the coordinates in which the noise is declared
	 */
	protected void notifyNoise(){}

	/**
	 * Notifies to all players the escape from the aliens of a the current player with the string "escaped=booleanValue"
	 * @param escapeResult 
	 */
	protected void notifyEscape(){}
	
	/**
	 * Notifies to all players the result of an attack of the current player with the string "killed=killedCharacter1=killedCharacter2=Survived=survivedCharacter1=survivedCharacter2"
	 * @param attackResult the "survived" string divides the killed characters usernames from the survived characters usernames; all useranames are divided by the character "="
	 */
	protected void notifyAttackResult(){}

	/**
	 * Notifies to all players the username of the new current player with the string "turn=newCurrentPlayerUsername=newTurnNumber"
	 */
	protected abstract void notifyBeginTurn();

	/**
	 * Notifies to every player the received character
	 */
	public abstract void notifyCharacter();

	/**
	 * Send the map of the game to all the players connected at the match
	 * @param map is a Map object representing the match	 
	 * */
	public abstract void sendMap(Map map);
}
