/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;
import it.polimi.ingsw.beribinaghi.serverSetup.SetupRMISession;

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
	private ArrayList<Player> killed;
	private ArrayList<Player> survived;
	private Boolean isEscaped;
	private Coordinates shallopCoordinates;
	private Registry registry;
	private String myName;
	private SetupRMISession setupRMISession;
	private ArrayList<Player> winnersList;

	public ServerRMISession(Player player,Registry registry, String myName, SetupRMISession setupRMISession) {
		this.player = player;
		notificableMap = false;
		notificableCharacter = false;
		notificableNewTurn = false;
		this.registry = registry;
		this.myName = myName;
		this.setupRMISession = setupRMISession;
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
	public int getTurnNumber() throws RemoteException {
		return match.getTurnNumber();
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
	public String getCurrentPlayer() throws RemoteException {
		this.notificableNewTurn = false;
		this.update.remove("end");
		return this.playerTurn;
	}

	@Override
	public ArrayList<Card> move(Coordinates coordinates) throws RemoteException {
		ArrayList<Card> pickedCards =  match.move(coordinates);
		if(match.isEscapeSuccessful() != null){
			match.getMatchDataUpdate().setEscaped();
			match.setSuccessfulEscape(null);
		}
		return pickedCards;
	}

	@Override
	public void noise(Coordinates coordinates) throws RemoteException {
		match.setNoiseCoordinates(coordinates);
		match.getMatchDataUpdate().setNoiseCoordinates();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeCardAction(ObjectCard card,Coordinates coordinates) throws RemoteException {
		String cardType = card.toString();
		switch(cardType){
		case "teleport" : 
			match.teleport();
			break;
		case "attack" :
			match.attack();
			if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
				update.add("attack");
				this.killed = (ArrayList<Player>) match.getKilled().clone();
				this.survived = (ArrayList<Player>) match.getSurvived().clone();
			}
			break;
		case "sedatives" :
			match.sedatives();
			break;
		case "adrenalin" :
			match.adrenalin();
			break;
		case "spotlight" :
			match.spotlight(coordinates);
			break;
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	protected void notifyEndMatch() {
		update.add("endMatch");
		winnersList = (ArrayList<Player>) match.getWinners().clone();
	}
	
	@Override
	protected void notifyDiscardedObject() {
		update.add("discard");
	}

	@Override
	protected void notifyCard() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
			update.add("card");
		}
	}

	@Override
	protected void notifySpotted() {
		update.add("spotlight");
	}

	@Override
	protected void notifyNoise() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().getUser().equals(this.player.getUser())){
			update.add("noise");
		}
	}

	@Override
	protected void notifyEscape() {
		update.add("escaped");
		this.isEscaped = match.isEscapeSuccessful();
		this.shallopCoordinates = match.getUsedShallopCoordinates();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void notifyAttackResult() {
		update.add("attack");
		this.killed = (ArrayList<Player>) match.getKilled().clone();
		this.survived = (ArrayList<Player>) match.getSurvived().clone();
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
		boolean finished = match.isFinished();
		if(finished){
			update.add("endMatch");
		}
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
	public String[] getSpottedPlayer() throws RemoteException {
		if (update.remove("spotlight")){
			ArrayList<Player> spotted = match.getSpotted();
			String result = "spotlight=";
			for(Player player : spotted){
				Coordinates playerCoordinates = player.getCharacter().getCurrentPosition();
				result += player.getUser() + "&" + playerCoordinates + "=";
			}
			return result.split("=");
		}
		return null;
	}

	@Override
	public ArrayList<String> getEscapeResult() throws RemoteException {
		ArrayList<String> escapeResult = new ArrayList<String>();
		if (update.remove("escape")){
			Coordinates shallopPosition = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getCurrentPosition();
			escapeResult.add(""+match.isEscapeSuccessful());
			escapeResult.add(shallopPosition.toString());
		}
		return escapeResult;
	}

	@Override
	public String[] getAttackResult() throws RemoteException {
		String attackResult = null;
		update.remove("attack");
		Coordinates attackPosition = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getCurrentPosition();
		attackResult = "attack=" + attackPosition+ "=";
		attackResult += "killed=";		
		for(Player player : killed)
			attackResult += player.getUser() + "&" + player.getCharacter() + "&" +player.getCharacter().getSide()+"=";
		attackResult += "survived=";
		for(Player player : survived)
			attackResult += player.getUser() + "="; 
		return attackResult.split("=");
	}


	public Coordinates getSpottedCoordinates() throws RemoteException {
		if (spotted){
			spotted = false;
			return match.getSpotlightCoordinates();
		}
		return null;
	}

	@Override
	public Coordinates getUsedShallopCoordinates() throws RemoteException {
		this.update.remove("escaped");
		return this.shallopCoordinates;
	}

	@Override
	public boolean isEscapeSuccessful() throws RemoteException {
		return this.isEscaped;
	}

	@Override
	public String[] getWinner() throws RemoteException{
		update.remove("endMatch");
		String []winners = new String[winnersList.size()+1];
		winners[0] = "wins";
		int i =1;
		for(Player player : winnersList){
			winners[i] = "Il giocatore "+ player.getUser() + ", nel ruolo di " + player.getCharacter().toString();
			i++;
		}
		return winners;
	}

	protected void notifyPlayerExit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void myTurn() {
		
	}

	@Override
	public void discardReceived() throws RemoteException {
		update.remove("discard");
	}

	@Override
	public void discard(ObjectCard discarded) {
		match.discard(discarded.toString());
	}

	@Override
	public boolean isMatchFinisched() {
		return match.isFinished();
	}

	@Override
	public boolean imescaped() {
		return this.isEscaped;
	}

	@Override
	public Coordinates escapeCoordinates() {
		update.remove("escaped");
		return  this.shallopCoordinates;
	}

	@Override
	public void close() throws RemoteException {
		try {
			registry.unbind(myName);
			this.setupRMISession.end();
		} catch (NotBoundException e) {
		}
	}

}
