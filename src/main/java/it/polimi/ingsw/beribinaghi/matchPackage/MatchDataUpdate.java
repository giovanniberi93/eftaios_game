/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Contains all information about the global situation of the match. It is observed from all the players in the match
 * Informations about: killed players, used cards, players survived from an attack, current player, noise coordinates, escape try outcome
 */
public class MatchDataUpdate extends Observable {
	private ArrayList<ObjectCard> usedObjectCard = new ArrayList<ObjectCard>();
	
	private int turnNumber;
	private Player currentPlayer;
	private boolean isMatchFinished;
	
	
	public MatchDataUpdate (Player successiveCurrentPlayer, int turnNumber){
		this.turnNumber = turnNumber;
		this.currentPlayer = successiveCurrentPlayer;
	}
	
	public void setUsedObjectCard(ObjectCard objectCard) {
		String usedCard = new String(objectCard.toString());
		this.setChanged();
		this.notifyObservers(usedCard);
	}

	public void setAttackOutcome(ArrayList<Player> killed, ArrayList<Player> survived) {
		String attackResult = new String("killed:");
		for(Player player : killed)
			attackResult = attackResult+player.getUser()+":";
		attackResult += "survived:";
		for(Player player : survived)
			attackResult += player.getUser()+":";
		this.setChanged();
		this.notifyObservers(attackResult);
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setEscaped(Boolean escaped) {
		String escape = new String("escape:"+escaped);
		this.setChanged();
		this.notifyObservers(escape);
	}

	public void setNoiseCoordinates(Coordinates noiseCoordinates) {
		String noise = new String("noise:"+noiseCoordinates.toString());
		this.setChanged();
		this.notifyObservers(noise);
	}

	public int getTurnNumber() {
		return turnNumber;
	}
	
	public ArrayList<ObjectCard> getUsedObjectCard() {
		return usedObjectCard;
	}

	public boolean isMatchFinished() {
		return isMatchFinished;
	}

	public void setMatchFinished(boolean isMatchFinished) {
		String end = new String("endMatch");
		this.setChanged();
		this.notifyObservers(end);
	}

	public void setSpottedPlayers(ArrayList<Player> caughtPlayer) {
		String spotted = new String("spotted:");
		for(Player player : caughtPlayer){
			spotted = spotted+player.getUser()+":";
		}
		this.setChanged();
		this.notifyObservers(spotted);
	}
	
	public void start()
	{
		this.setChanged();
		this.notifyObservers("turn:" + currentPlayer.getUser() + ":" + getTurnNumber());
	}

	public void clear(Player player) {
		this.turnNumber++;
		this.currentPlayer = player;
		usedObjectCard.clear();
		this.setChanged();
		this.notifyObservers("turn:" + player.getUser()+":"+getTurnNumber());
	}

}
