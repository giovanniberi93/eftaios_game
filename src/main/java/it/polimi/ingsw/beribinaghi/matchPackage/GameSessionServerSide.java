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

	
	@Override
	public  void update(Observable o, Object arg) {
		String line = (String) arg;
		String command[] = line.split(":");
		if (command[0].equals("turn"))
			this.notifyBeginTurn(command[1]);
		else if (command[0].equals("killed")){
			this.notifyAttackResult(command);
		}
		else if (command[0].equals("escape")){
			this.notifyEscape();
		}
		else if (command[0].equals("noise")){
			this.notifyNoise(command[1]);
		}
		else if (command[0].equals("spotted")){
			this.notifySpotted(command);
		}
		else if (command[0].equals("card")){
			this.notifyCard(command[1]);
		}
		else if (command[0].equals("endMatch")){
			this.notifyEndMatch();
		}
	}

	/**
	 * Notify to all players the end of the running match
	 */
	protected void notifyEndMatch() {}

	/**
	 * Notify to all players an object used by current player
	 * @param string is usedCard.toString(); represent the used card 
	 */
	protected void notifyCard(String string) {}
	
	/**
	 * Notify to all players the position of the players spotted by a spotlight
	 * @param command is an Array containing the usernames of the spotted players
	 */
	protected void notifySpotted(String[] command){}

	/**
	 * Notify to all players the position of the noise signaled by the current player
	 * @param string
	 */
	protected void notifyNoise(String string){}

	/**
	 * Notify to all players the escape from the alien of a human player
	 */
	protected void notifyEscape(){}
	
	protected void notifyAttackResult(String[] command){}

	/**
	 * @param playerName
	 * notify client that is turn of playerName
	 */
	protected abstract void notifyBeginTurn(String playerName);

	/**
	 * notify client about his character
	 */
	public abstract void notifyCharacter();

	/**
	 * @param map
	 * sends all map to client
	 */
	public abstract void sendMap(Map map);
}