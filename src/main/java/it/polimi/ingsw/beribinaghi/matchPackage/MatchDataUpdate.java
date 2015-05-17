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
	private ArrayList<Player> recentKills = new ArrayList<Player>();
	private ArrayList<ObjectCard> usedObjectCard = new ArrayList<ObjectCard>();
	private ArrayList<Player> survivedPlayers = new ArrayList<Player>();
	
	private int turnNumber;
	private Player currentPlayer;
	private Boolean escaped = null;
	private Coordinates noiseCoordinates;
	
	public MatchDataUpdate (Player successiveCurrentPlayer, int turnNumber){
		this.turnNumber = turnNumber;
		this.currentPlayer = successiveCurrentPlayer;
	}
	
	public ArrayList<Player> getRecentKills() {
		return recentKills;
	}
	public void setRecentKills(ArrayList<Player> recentKills) {
		this.recentKills = recentKills;
	}
	public ArrayList<ObjectCard> getUsedObjectCard() {
		return usedObjectCard;
	}
	public void setUsedObjectCard(ArrayList<ObjectCard> usedObjectCard) {
		this.usedObjectCard = usedObjectCard;
	}
	public ArrayList<Player> getSurvivedPlayers() {
		return survivedPlayers;
	}
	public void setSurvivedPlayers(ArrayList<Player> survivedPlayers) {
		this.survivedPlayers = survivedPlayers;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Boolean getEscaped() {
		return escaped;
	}
	public void setEscaped(Boolean escaped) {
		this.escaped = escaped;
	}
	public Coordinates getNoiseCoordinates() {
		return noiseCoordinates;
	}
	public void setNoiseCoordinates(Coordinates noiseCoordinates) {
		this.noiseCoordinates = noiseCoordinates;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

}
