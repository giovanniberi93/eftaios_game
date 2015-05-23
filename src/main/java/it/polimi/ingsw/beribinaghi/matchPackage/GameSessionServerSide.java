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
		
	}

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