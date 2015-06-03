/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.RMIInterface.RemoteGameSession;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

/**
 * @author damianobinaghi
 *
 */
public class ClientRMISession implements GameSessionClientSide {
	private RemoteGameSession session;
	private MatchController controller;
	private long whaitNewNotify = 100;

	public ClientRMISession(RemoteGameSession remoteGameSession) {
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
		try {
			ArrayList<Card> cardsPicked = session.move(destinationCoord);
			if (cardsPicked.isEmpty())
				cardsPicked.add(new NothingToPick());
			return cardsPicked;
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void endTurn() {
		try {
			session.finishTurn();
		} catch (RemoteException e) {
		}
	}

	@Override
	public void noise(Coordinates noiseCoordinates) {
		try {
			session.noise(noiseCoordinates);
		} catch (RemoteException e) {
		}
	}

	@Override
	public void useObjectcard(ArrayList<String> command) {
		try {
			if (command.size()>1)
				session.executeCardAction(ObjectCard.stringToCard(command.get(0)), Coordinates.stringToCoordinates(command.get(1)));
			else
				session.executeCardAction(ObjectCard.stringToCard(command.get(0)),null);
		} catch (RemoteException e) {
		}
	}

	@Override
	public void listenUpdate() {
		// TODO Auto-generated method stub
		
	}

}
