/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

/**
 * This class manage all communication with client during the game using RMI
 *
 */
public class ServerRMISession extends GameSessionServerSide implements RemoteGameSession {
	private Map map;
	private Boolean notificableMap;
	private Boolean notificableCharacter;
	private Boolean notificableNewTurn;
	private ArrayList<String> update = new ArrayList<String>();
	private String playerTurn;
	private boolean spotted = false;

	public ServerRMISession(Player player) {
		this.player = player;
		notificableMap = false;
		notificableCharacter = false;
		notificableNewTurn = false;
	}
	
	public void setMatch(Match match){
		this.match = match;
	}

	@Override
	protected void notifyBeginTurn() {
		notificableNewTurn = true;
		playerTurn = match.getMatchDataUpdate().getCurrentPlayer().getUser();
		Player oldCurrentPlayer = match.getMatchDataUpdate().getOldCurrentPlayer();
		if(!(oldCurrentPlayer == null) && !(this.player.getUser().equals(oldCurrentPlayer.getUser()))){
			update.add("end");
		}
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

	@Override
	public ArrayList<Card> move(Coordinates coordinates) throws RemoteException {
		return match.move(coordinates);
	}

	@Override
	public void noise(Coordinates coordinates) throws RemoteException {
		match.setNoiseCoordinates(coordinates);
		match.getMatchDataUpdate().setNoiseCoordinates();
	}

	@Override
	public void executeCardAction(ObjectCard card,Coordinates coordinates) throws RemoteException {
		String cardType = card.toString();
		switch(cardType){
		case "teleport" : 
			match.teleport();
			break;
		case "attack" :
			match.attack();
			update.add("attack");
			break;
		case "sedatives" :
			match.sedatives();
			break;
		case "adrenalin" :
			match.adrenalin();
			break;
		case "spotlight" :
			match.spotlight(coordinates);
			update.add("spotted");
			break;
		}
	}

	@Override
	protected void notifyEndMatch() {
		update.add("endMatch");
	}

	@Override
	protected void notifyCard() {
		update.add("card");
	}

	@Override
	protected void notifySpotted() {
		update.add("spotted");
	}

	@Override
	protected void notifyNoise() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
			update.add("noise");
		}
	}

	@Override
	protected void notifyEscape() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
			update.add("escape");
		}
	}

	@Override
	protected void notifyAttackResult() {
		update.add("attack");
	}

	@Override
	public ArrayList<String> getUpdate() throws RemoteException {
		return this.update;
	}

	@Override
	public Coordinates getNoiseCoordinates() throws RemoteException {
		if (update.remove("noise"))
			return match.getNoiseCoordinates();
		return null;
	}

	@Override
	public void finishTurn() throws RemoteException {
		match.getMatchDataUpdate().setOldCurrentPlayer(this.player);
		match.finishTurn(); 
	}

	@Override
	public ObjectCard getUsedCard() throws RemoteException {
		if (update.remove("card")){
			ObjectCard card = match.getLastUsedCard();
			if(card.toString().equals("spotlight"))
				spotted  = true;
			return card;
		}
		return null;
	}

	@Override
	public ArrayList<String> getSpottedPlayer() throws RemoteException {
		if (update.remove("spotted")){
			ArrayList<Player> spotted = match.getSpotted();
			ArrayList<String> result = new ArrayList<String>();
			for(Player player : spotted){
				Coordinates playerCoordinates = player.getCharacter().getCurrentPosition();
				result.add(player.getUser());
				result.add(playerCoordinates.toString());
			}
			return result;
		}
		return null;
	}

	@Override
	public ArrayList<String> getEscapeResult() throws RemoteException {
		ArrayList<String> escapeResult = new ArrayList<String>();
		if (update.remove("escape")){
			Coordinates shallopPosition = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getCurrentPosition();
			escapeResult.add(""+match.isSuccessfulEscape());
			escapeResult.add(shallopPosition.toString());
		}
		return escapeResult;
	}

	@Override
	public ArrayList<String> getAttackResult() throws RemoteException {
		ArrayList<String> attackResult = new ArrayList<String>();
		attackResult.add(match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getCurrentPosition().toString());
		if (update.remove("attack")){
			ArrayList<Player> killed = match.getKilled();
			attackResult.add("killed");
			for(Player player : killed){
				attackResult.add(player.getUser());
				attackResult.add(player.getCharacter().toString());
			}
			ArrayList<Player> survived = match.getSurvived();
			attackResult.add("survived");
			for(Player player : survived){
				attackResult.add(player.getUser());
				attackResult.add(player.getCharacter().toString());
			}
		}
		return attackResult;
	}

	@Override
	public Coordinates getSpottedCoordinates() throws RemoteException {
		if (spotted){
			spotted = false;
			return match.getSpotlightCoordinates();
		}
		return null;
	}

}
