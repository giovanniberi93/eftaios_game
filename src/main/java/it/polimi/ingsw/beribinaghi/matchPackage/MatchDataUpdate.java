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
	
	public MatchDataUpdate (Player successiveCurrentPlayer, int turnNumber){
		this.turnNumber = turnNumber;
		this.currentPlayer = successiveCurrentPlayer;
	}
	
	/**
	 * Notifies to all gamesession a card used by the current player; the syntax is "card=usedCard.toString()"
	 * @param objectCard
	 */
	public void setUsedObjectCard(ObjectCard objectCard) {
		String usedCard = new String(objectCard.toString());
		usedObjectCard.add(objectCard);
		this.setChanged();
		this.notifyObservers("card="+usedCard);
	}

	/**
	 * Notifies to all gamesession the result of an attack; the syntax is "killed=killedCharacter1=killedCharacter2=Survived=survivedCharacter1=survivedCharacter2"
	 * @param killed contains the killed character
	 * @param survived contains the survived character
	 */
	public void setAttackOutcome(ArrayList<Player> killed, ArrayList<Player> survived) {
		String attackResult = new String("killed=");
		for(Player player : killed)
			attackResult = attackResult+player.getUser()+"=";
		attackResult += "survived=";
		for(Player player : survived)
			attackResult += player.getUser()+"=";
		this.setChanged();
		this.notifyObservers(attackResult);
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Notifies to all gamesession the result of a tempted escape with the string "escaped=booleanValue"
	 * @param escaped is true if the escaped was successful, false if it has failed
	 */
	public void setEscaped(Boolean escaped) {
		String escapeResult = new String("escaped="+escaped);
		this.setChanged();
		this.notifyObservers(escapeResult);
	}

	/**
	 * Notifies to all gamessions the coordinates of the noise signaled by currentPlayer; the syntax is "noise=coordinatesLetter=coordinatesNumber"
	 * @param noiseCoordinates are the coordinates signaled for the noise
	 */
	public void setNoiseCoordinates(Coordinates noiseCoordinates) {
		String noise = new String("noise="+noiseCoordinates.getLetter()+"="+noiseCoordinates.getNumber());
		this.setChanged();
		this.notifyObservers(noise);
	}

	public int getTurnNumber() {
		return turnNumber;
	}
	
	public boolean searchUsedObjectCard(ObjectCard searchedCard) {
		for(ObjectCard card : usedObjectCard)
			if(searchedCard.getClass().equals(card.getClass()))
				return true;
		return false;
	}

	/**
	 * Notifies to all gamesessions the end of the game; the notified string is "endMatch"
	 * @param isMatchFinished
	 */
	public void setMatchFinished() {
		String end = new String("endMatch");
		this.setChanged();
		this.notifyObservers(end);
	}

	/**
	 * Notifies username and position of spotted players to every the gamesessions; the syntax of the notified string is spotted=username&coordinatesLetter&coordinatesNumber=
	 * @param caughtPlayer contains the players spotted by the spotllight
	 */
	public void setSpottedPlayers(ArrayList<Player> caughtPlayer) {
		String spotted = new String("spotted=");
		for(Player player : caughtPlayer){
			Coordinates position = player.getCharacter().getCurrentPosition();
			spotted = spotted+player.getUser()+"&"+position.getLetter()+"&"+position.getNumber()+"=";
		}
		this.setChanged();
		this.notifyObservers(spotted);
	}
	
	/**
	 * Notifies the first player username to all gamesessions with the string "turn=playerUsername=1"
	 */
	public void start()
	{
		this.setChanged();
		this.notifyObservers("turn=" + currentPlayer.getUser() + "=" + getTurnNumber());
	}

	/**
	 * Notifies all gamesessions the refreshed currentPlayer and turnNumber 
	 * @param player is the new currentPlayer
	 */
	public void clear(Player player) {
		this.turnNumber++;
		this.currentPlayer = player;
		usedObjectCard.clear();
		this.setChanged();
		this.notifyObservers("turn=" + player.getUser()+"="+getTurnNumber());
	}

}
