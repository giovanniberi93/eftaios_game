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
 * class that manages all communication with server during the game using RMI
 *
 */
public class ClientRMISession extends GameSessionClientSide {
	private RemoteGameSession session;
	private long waitNewNotify = 100;

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
				this.wait(this.waitNewNotify);
			return session.getCharacter();
		} catch (RemoteException e) {
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	public synchronized String listenTurn() {
		try {
			while (!session.isTurnNotificable())
				this.wait(this.waitNewNotify);
			int turnNumber = session.getTurnNumber();
			controller.setTurnNumber(turnNumber);
			return session.getCurrentPlayer();
		} catch (RemoteException e) {
		} catch (InterruptedException e) {
		}
		return null;
	}

	@Override
	public synchronized Map getMap() {
		try {
			while (!session.isMapNotificable())
				this.wait(this.waitNewNotify);
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
			else if (cardsPicked.size() == 2 && cardsPicked.get(1) == null)
				cardsPicked.remove(1);
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
			if (noiseCoordinates!=null)
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
			if(command.get(0).equals("attack")){
				super.analyzeAndShowAttack(session.getAttackResult());
			}
			else 
				if (command.get(0).equals("spotlight")){
					analyzeAndShowSpotlight(session.getSpottedPlayer());
				}
		} catch (RemoteException e) {
		}
	}

	@Override
	public synchronized void listenUpdate() {
		ArrayList<String> update;
		Boolean end = false;
		Boolean endMatch = false;
		try {
			update = session.getUpdate();
		while (!end && !endMatch){
			while (update.size()==0){
				try {
					this.wait(100);
				} catch (InterruptedException e) {
				}
				update = session.getUpdate();
			}
			for (String single:update){
				if (!single.equals("end") && !endMatch){
					switch(single){
					case "noise":
						Coordinates noiseCoord = session.getNoiseCoordinates();
						if (noiseCoord!=null)
							controller.getGraphicInterface().showNoise(noiseCoord);
						break;
					case "escaped":
						boolean result = session.isEscapeSuccessful();
						Coordinates coord = session.getUsedShallopCoordinates();
						controller.getGraphicInterface().showEscapeResult(result, coord);
						break;
					case "discard":
						session.discardReceived();
						controller.getGraphicInterface().notifyDiscardedObject();
						break;
					case "card":
						ObjectCard usedCard = session.getUsedCard();
						Coordinates destinationCoord = null;
						if(usedCard.toString().equals("spotlight"))
							destinationCoord = session.getSpottedCoordinates();
						controller.getGraphicInterface().showUsedCard(usedCard, destinationCoord);
						break;
					case "attack":
							super.analyzeAndShowAttack(session.getAttackResult());
						break;
					case "endMatch":
						controller.getGraphicInterface().showMatchResults(session.getWinner());
						controller.setMatchFinished(true);
						endMatch = true;
						break;
					case "spotlight":
						super.analyzeAndShowSpotlight(session.getSpottedPlayer());
						break;
					case "exit":
						super.analyzeAndShowExitedPlayer(session.getExitedPlayer());
						break;
					}
				if(!endMatch)
					update = session.getUpdate();
				} else
					end = true;
			}
			if (endMatch)
				session.close();
			}
		} catch (RemoteException e1) {}
		if(!endMatch)
			controller.turn(true);
	}

	
	@Override
	public void signalDiscardedObjectCard(ObjectCard discarded) {
		try {
			session.discard(discarded);
		} catch (RemoteException e) {
		}
	}

	@Override
	public boolean isMatchFinished() {
		try {
			return session.isMatchFinisched();
		} catch (RemoteException e) {
		}
		return false;
	}

	@Override
	public void listenMatchResult() {
		try {
			controller.getGraphicInterface().showMatchResults(session.getWinner());
			session.close();
		} catch (RemoteException e) {
		}
	}

	@Override
	public void listenEscapeResult() {
		boolean result;
		try {
			result = session.imescaped();
			Coordinates shallopCoord = session.escapeCoordinates();
			controller.setAttemptedEscape(true);
			controller.getMap().addUsedShallop(shallopCoord);
			controller.getGraphicInterface().showEscapeResult(result, shallopCoord);
		} catch (RemoteException e) {
		}	
	}


	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
}
