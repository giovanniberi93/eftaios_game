/**
 * 
 */
package it.polimi.ingsw.beribinaghi.serverSetup;

import java.util.TimerTask;

/**
 * This Class manages a timer task
 *
 */
public class TimerManager extends TimerTask {
	private MatchController matchController;
	private PreMatch match;
	
	public TimerManager(MatchController matchController, PreMatch match) {
		this.matchController = matchController;
		this.match = match;
	}

	@Override
	public void run() {
		matchController.start(match);

	}

}
