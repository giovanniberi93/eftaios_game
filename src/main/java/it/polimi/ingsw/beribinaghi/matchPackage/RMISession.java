/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import java.rmi.RemoteException;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

/**
 * @author damianobinaghi
 *
 */
public class RMISession extends GameSessionServerSide implements RemoteGameSession {
	private Map map;
	private Boolean notificableMap;
	private Boolean notificableCharacter;
	private Boolean notificableNewTurn;
	private String playerTurn;

	public RMISession(Match match, Player player) {
		this.match = match;
		this.player = player;
		notificableMap = false;
		notificableCharacter = false;
		notificableNewTurn = false;
	}

	@Override
	protected void notifyBeginTurn(String playerName) {
		String []singleWord = playerName.split("=");
		playerTurn = singleWord[1];
		notificableNewTurn = true;
	}

	@Override
	public void notifyCharacter() {
		notificableCharacter = true;
	}

	@Override
	public void sendMap(Map map) {
		notificableMap = true;
		this.map = new Map(map.getMapName(),map.getGraphicMap());
	}

	@Override
	public Character getCharacter() throws RemoteException {
		notificableCharacter = false;
		return player.getCharacter();
	}

	@Override
	public Boolean isMapNotificable() throws RemoteException {
		return this.notificableMap;
	}

	@Override
	public Boolean isCharacterNotificable() throws RemoteException {
		return this.notificableCharacter;
	}

	@Override
	public Boolean isTurnNotificable() throws RemoteException {
		return this.notificableNewTurn;
	}

	@Override
	public Map getMap() throws RemoteException {
		notificableMap = false;
		return this.map;
	}

	@Override
	public String getPlayerTurn() throws RemoteException {
		this.notificableNewTurn = false;
		return this.playerTurn;
	}

}
