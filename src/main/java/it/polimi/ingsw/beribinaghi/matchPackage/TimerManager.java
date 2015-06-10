/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

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
		System.out.println("pene");
	}

}
