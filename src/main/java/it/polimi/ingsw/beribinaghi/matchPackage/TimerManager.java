/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Class that manage the timer of turn
 *
 */
public class TimerManager extends TimerTask {
	private Match match;

	
	public TimerManager(Match match) {
		this.match = match;
	}


	@Override
	public void run() {
		match.getMatchDataUpdate().setPlayerExit();
		//la parte fuori dai commenti funziona tutta
		//
		Player currentPlayer = match.getMatchDataUpdate().getCurrentPlayer();
		GameSessionServerSide currentSession = null;
		ArrayList<GameSessionServerSide> sessions = match.getSessions();
		for(GameSessionServerSide s : sessions)
			if(s.player.equals(currentPlayer))
				currentSession = s;
		match.getMatchDataUpdate().deleteObserver(currentSession);
		currentSession.disconnect();
		//
		System.out.println("pene");
	}

}
