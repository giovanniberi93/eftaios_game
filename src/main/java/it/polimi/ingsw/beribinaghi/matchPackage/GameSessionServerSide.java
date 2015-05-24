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
	public  void update(Observable o, Object arg) {
		String line = (String) arg;
		String command[] = line.split("=");
		if (command[0].equals("turn"))
			this.notifyBeginTurn(line);
		else if (command[0].equals("killed")){
			this.notifyAttackResult(line);
		}
		else if (command[0].equals("escaped")){
			this.notifyEscape(line);
		}
		else if (command[0].equals("noise")){
			this.notifyNoise(line);
		}
		else if (command[0].equals("spotted")){
			this.notifySpotted(line);
		}
		else if (command[0].equals("card")){
			this.notifyCard(line);
		}
		else if (command[0].equals("endMatch")){
			this.notifyEndMatch();
		}
	}

	/**
	 * Notifies to all players the end of the running match
	 */
	protected void notifyEndMatch() {}

	/**
	 * Notifies to all players an object used by current player
	 * @param string is usedCard.toString(); represent the used card 
	 */
	protected void notifyCard(String string) {}
	
	/**
	 * Notifies to all players the position of the players spotted by a spotlight
	 * @param spottedPlayers contains the usernames of spotted players, divided by the character "="
	 */
	protected void notifySpotted(String spottedPlayers){}

	/**
	 * Notifies to all players the position of the noise signaled by the current player
	 * @param string contains a string representing the coordinates in which the noise is declared
	 */
	protected void notifyNoise(String noisePosition){}

	/**
	 * Notifies to all players the escape from the aliens of a the current player
	 * @param escapeResult 
	 */
	protected void notifyEscape(String escapeResult){}
	
	/**
	 * Notifies to all players the result of an attack of the current player
	 * @param attackResult the "survived" string divides the killed characters usernames from the survived characters usernames; all useranames are divided by the character "="
	 */
	protected void notifyAttackResult(String attackResult){}

	/**
	 * Notifies to all players the username of the new current player
	 * @param playerName
	 */
	protected abstract void notifyBeginTurn(String playerName);

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