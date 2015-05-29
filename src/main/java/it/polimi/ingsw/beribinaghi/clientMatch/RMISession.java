/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
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

	@Override
	public ArrayList<Card> move(Coordinates destinationCoord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void noise(Coordinates noiseCoordinates) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useObjectcard(ArrayList<String> command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenUpdate() {
		// TODO Auto-generated method stub
		
	}

}
