package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.App;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

import java.util.Timer;

/**
 * controller of the matches
 *
 */
public class MatchController {
	private ArrayList<PreMatch> matches;
	
	public MatchController(){
		matches = new ArrayList<PreMatch>();
	}
	
	/**
	 * @return all match name inactive
	 */
	public ArrayList<String> getMatchesName()
	{
		ArrayList<String> nameList = new ArrayList<String>();
		for (PreMatch pre : matches)
			if (!pre.isActive())
				nameList.add(pre.getMatchName());
		return nameList;
	}

	/**
	 * @param name
	 * @param player
	 * Generate a new pre match, it receives the name and the administrator player
	 */
	public void createNewMatch(String name,Player player) throws AlreadyExistingNameException {
		if (nameExists(name))
			throw new AlreadyExistingNameException();
		PreMatch match = new PreMatch(name,player);
		matches.add(match);
	}
	

	/**
	 * @param matchName
	 * @param player
	 * @throws NotExistingNameException
	 * Exit player from a match
	 */
	public void exitPlayer(String matchName, Player player)  throws NotExistingNameException {
		PreMatch match = getMatch(matchName);
		match.removePlayer(player);
		if (match.countPlayer()==0)
			matches.remove(match);
	}
	
	/**
	 * @param matchName
	 * @param player
	 * @throws NotExistingNameException
	 * add a player to a match
	 */
	public void addPlayer(String matchName, Player player)  throws NotExistingNameException {
		PreMatch match = getMatch(matchName);
		try {
			match.addPlayer(player);
			if (match.countPlayer()==App.MAX_PLAYER)
				start(match);
			else {
				Timer timer = match.getTimer();
				timer.purge();
				timer.schedule(new TimerManager(this,match), App.WAIT);
			}
		} catch (TooManyPlayerException e) {
			
		}
	}

	/**
	 * @param match
	 * start the match
	 */
	public void start(PreMatch match) {
		match.start();
	}

	private boolean nameExists(String name) {
		for (PreMatch pre : matches)
			if(pre.getMatchName().equals(name))
				return true;
		return false;
	}


	private PreMatch getMatch(String name) throws NotExistingNameException{
		for (PreMatch pre : matches)
			if(pre.getMatchName().equals(name))
				return pre;
		throw new NotExistingNameException();
	}

}
