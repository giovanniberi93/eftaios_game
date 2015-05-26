/**
 * 
 */
package it.polimi.ingsw.beribinaghi.serverSetup;

import java.util.ArrayList;
import java.util.Timer;

import it.polimi.ingsw.beribinaghi.App;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

/**
 * This class manages a pre match and a close match
 */
public class PreMatch{
	private String matchName;
	private Boolean active;
	private ArrayList<Player> players;
	private Timer timer;
	private Match match;

	public Match getMatch() {
		return match;
	}

	/**
	 * @return the status of the match
	 */
	public Boolean isActive() {
		return active;
	}

	public String getMatchName() {
		return matchName;
	}
	
	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	
	/**
	 * @param player
	 * Removes a player froma a match
	 */
	public synchronized void removePlayer(Player player) {
		players.remove(player);
	}
	
	/**
	 * @param player
	 * Count the player in the room
	 */
	public int countPlayer() {
		return players.size();
	}
	
	/**
	 * @param player
	 * adds a player to the match
	 */
	public synchronized void addPlayer(Player player) throws TooManyPlayerException {
		if (countPlayer()<App.MAX_PLAYER)
			players.add(player);
		else
			throw new TooManyPlayerException();
	}
	
	/**
	 * @param name
	 * @param administrator
	 * Creates a preMatch with name and a player administrator
	 */
	public PreMatch(String name,Player administrator){
		matchName = name;
		players = new ArrayList<Player>();
		this.players.add(administrator);
		timer = new Timer();
		active = false;
	}

	/**
	 *  Set the game sessions and create the match
	 */
	public void setGameSession(ArrayList<GameSessionServerSide> sessions){
		active = true;
		timer.cancel();
		match = new Match(sessions,players,matchName,"galilei",MapModel.GALILEI);
	}
	
	/**
	 *  Start the match
	 */
	public void start(){
		match.start();
	}

	public ArrayList<String> getPlayerName() {
		ArrayList<String> playerName = new ArrayList<String>();
		for (Player player: players)
			playerName.add(player.getUser());
		return playerName;
	}
}
