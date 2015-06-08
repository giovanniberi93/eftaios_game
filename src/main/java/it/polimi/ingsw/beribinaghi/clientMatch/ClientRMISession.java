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
public class ClientRMISession implements GameSessionClientSide {
	private RemoteGameSession session;
	private MatchController controller;
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
	public String listenTurn() {
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
			if(command.get(0).equals("attack")){
				analyzeAndShowAttack(session.getAttackResult());
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
		try {
		Boolean end = false;
		update = session.getUpdate();
		while (!end){
			while (update.size()==0){
				try {
					this.wait(100);
				} catch (InterruptedException e) {
				}
				update = session.getUpdate();
			}
			for (String single:update){
				if (!single.equals("end")){
					switch(single){
					case "noise":
						Coordinates noiseCoord = session.getNoiseCoordinates();
						if (noiseCoord!=null)
							controller.getGraphicInterface().showNoise(noiseCoord);
						break;
					case "escaped":
						boolean result;
						ArrayList<String> passed = session.getAttackResult();
						if(passed.get(0).equals("true"))
							result = true;
						else
							result = false;
						Coordinates coord = Coordinates.stringToCoordinates(passed.get(1));
						controller.getGraphicInterface().showEscapeResult(result, coord);
						break;
					case "card":
						ObjectCard usedCard = session.getUsedCard();
						Coordinates destinationCoord = null;
						if(usedCard.toString().equals("spotlight"))
							destinationCoord = session.getSpottedCoordinates();
						controller.getGraphicInterface().showUsedCard(usedCard, destinationCoord);
						break;
					case "attack":
							analyzeAndShowAttack(session.getAttackResult());
						break;
					case "endMatch":
						// TODO boh.
						break;
					case "spotlight":
							analyzeAndShowSpotlight(session.getSpottedPlayer());
						break;
					}
				update = session.getUpdate();
				} else
					end = true;
			}
		}
		} catch (RemoteException e1) {
		}
		controller.turn(true);
	}

	private void analyzeAndShowSpotlight(ArrayList<String> spottedPlayer) {
		for(int i=1; i<spottedPlayer.size()-1; i+=2){
			String username = spottedPlayer.get(i);
			Coordinates position = Coordinates.stringToCoordinates(spottedPlayer.get(i+1));
			controller.getGraphicInterface().showSpottedPlayer(username, position);
		}	
	}

	private void analyzeAndShowAttack(ArrayList<String> attackResult) {
		/*ArrayList<String> killed;
		ArrayList<String> survived;
		Coordinates attackCoordinates = Coordinates.stringToCoordinates(attackResult.get(0));
		killed = this.selectKilled(attackResult);
		survived = this.selectSurvived(attackResult);
		controller.getGraphicInterface().showAttackResult(attackCoordinates, killed, survived);*/
	}
	
	private ArrayList<String> selectSurvived(ArrayList<String> result) {
		ArrayList<String> survived = new ArrayList<String>();
		int i = 0;
		String string = result.get(i);
		while(!string.equals("survived")){
			i++;
			string = result.get(i);
		}
		i++;
		while (i < result.size()){
			survived.add(result.get(i));
			i++;
		}
		return survived;
	}

	private ArrayList<String> selectKilled(ArrayList<String> result) {
		ArrayList<String> killed = new ArrayList<String>();
		int i = 2;
		while(!result.get(i).equals("survived")){
			killed.add(result.get(i));
			i++;
		}
		return killed;		
	}

	@Override
	public void signalDiscardedObjectCard(ObjectCard discarded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMatchFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void listenMatchResult() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listenEscapeResult() {
		// TODO Auto-generated method stub
		
	}


	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
}
