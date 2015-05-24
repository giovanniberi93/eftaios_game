/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import java.rmi.RemoteException;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

/**
 * @author damianobinaghi
 *
 */
public class RMISession implements GameSessionClientSide {
	private RemoteGameSession session;
	private MatchController controller;
	private long whaitNewNotify = 100;

	public RMISession(RemoteGameSession remoteGameSession) {
		this.session = remoteGameSession;
	}

	@Override
	public void setController(MatchController matchController) {
		this.controller = matchController;
	}

	@Override
	public synchronized Character getCharacter() {
		try {
			while (!session.isCharacterNotificable())
				this.wait(this.whaitNewNotify);
			return session.getCharacter();
		} catch (RemoteException e) {
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	public String listenTurn() {
		try {
			while (!session.isTurnNotificable())
				this.wait(this.whaitNewNotify);
			return session.getPlayerTurn();
		} catch (RemoteException e) {
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	public synchronized Map getMap() {
		try {
			while (!session.isMapNotificable())
				this.wait(this.whaitNewNotify);
			return session.getMap();
		} catch (RemoteException e) {
		} catch (InterruptedException e) {
		}
		return null;
	}

}
