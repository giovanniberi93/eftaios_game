package it.polimi.ingsw.beribinaghi.serverSetup;

import it.polimi.ingsw.beribinaghi.App;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;
import java.util.Timer;

/**
 * controller of the matches
 *
 */
public class MatchController {
	private ArrayList<PreMatch> matches;
	private ArrayList<SetupSession> sessions;
	
	public MatchController(){
		matches = new ArrayList<PreMatch>();
		sessions = new ArrayList<SetupSession>();
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
	 * @throws TooManyPlayerException
	 * add a player to a match
	 * @throws MatchJustStartingException 
	 */
	public void addPlayer(String matchName, Player player)  throws NotExistingNameException,TooManyPlayerException, MatchJustStartingException {
		PreMatch match = getMatch(matchName);
		if (!match.isActive())
		{
			match.addPlayer(player);
			if (match.countPlayer()==App.MAX_PLAYER)
				start(match);
			else if (match.countPlayer()>1){
				Timer timer = match.getTimer();
				timer.purge();
				timer.schedule(new TimerManager(this,match), App.WAITBEGINMATCH);
			}
			notifyAddPlayer(match,player.getUser());
		}
		else
			throw new MatchJustStartingException();
	}

	private void notifyAddPlayer(PreMatch match,String namePlayer) {
		for (SetupSession setupSession: sessions)
			if ((setupSession.getMatchName()!=null) && setupSession.getMatchName().equals(match.getMatchName()))
				setupSession.notifyNewPlayer(namePlayer);
	}

	/**
	 * @param preMatch
	 * start the preMatch
	 */
	public void start(PreMatch preMatch) {
		ArrayList<GameSessionServerSide> gameSessions = new ArrayList<GameSessionServerSide>();
		for (SetupSession setupSession: sessions)
			if ((setupSession.getMatchName()!=null) && setupSession.getMatchName().equals(preMatch.getMatchName()))
			{
				gameSessions.add(setupSession.getGameSession());
				setupSession.startMatch();
			}
		preMatch.start(gameSessions);
	}

	private boolean nameExists(String name) {
		for (PreMatch pre : matches)
			if(pre.getMatchName().equals(name))
				return true;
		return false;
	}


	public PreMatch getMatch(String name) throws NotExistingNameException{
		for (PreMatch pre : matches)
			if(pre.getMatchName().equals(name))
				return pre;
		throw new NotExistingNameException();
	}

	public ArrayList<String> getPlayer(String matchName) throws NotExistingNameException {
		PreMatch match = getMatch(matchName);
		return match.getPlayerName();		
	}

	/**
	 * @param setupSession
	 * register session to matchController observer
	 */
	public void registerSession(SetupSession setupSession) {
		sessions.add(setupSession);
	}

}
