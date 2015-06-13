/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * interface for managing the communication with server
 * it receives command that will be sent to server
 *
 */
public abstract class GameSessionClientSide {
	
	protected MatchController controller;

	public abstract void setController(MatchController matchController);

	public abstract Character getCharacter();

	/**
	 *	Listens from the ServerGameSession the username of the new Currentplayer
	 * @return username of currentPlayer
	 */
	public  abstract String listenTurn();

	/**
	 * Listens from the ServerGameSession name, height, width of the map and the abbreviations of all the sectors of the map
	 * @return the Map of the game
	 */
	public abstract Map getMap();
	
	/**
	 * Communicates to ServerSession the Move command 
	 * @param destinationCoord are the coordinates of the destination sector
	 * @return the cards picked after the movement; the first card is the sectorCard (noiseInAnySector, noiseInYourSector, Silencem NothingToPick, ShallopCard), the second is the ObjectCard (if exists)
	 * @throws WrongSyntaxException
	 */
	public abstract ArrayList<Card> move(Coordinates destinationCoord) throws WrongSyntaxException;

	/**
	 * Communicates to ServerSession the coordinates of the noise. It can be a Coordinate, SILENCE, or the signal of no noise (in socket, the string "nothing");  it's invoked only by currentPlayer.
	 * @param noiseCoordinates
	 */
	public abstract void noise(Coordinates noiseCoordinates);
	
	/**
	 * Communicates to ServerSession the card selected to use; the attack is not used as a card, but as a distinct choice because it can be done also by the aliens;  this method is invoked only by currentPlayer.
	 * @param command
	 */
	public abstract void useObjectcard(ArrayList<String> command);

	/**
	 * Communicates to ServerSession the signal of the end of the turn. It's invoked only by currentPlayer.
	 */
	public abstract boolean endTurn();

	/**
	 * Listens from ServerSession the updates of the match during other's turn, like: used cards, noise coordinates, attack or escape results
	 */
	public abstract void listenUpdate();

	/**
	 * Communicates to serverSession the objectCard that has been discarded
	 * @param discarded 
	 */
	public abstract void signalDiscardedObjectCard(ObjectCard discarded);

	/**
	 * check if the match is finished or not
	 * @return true if the match is finished
	 */
	public abstract boolean isMatchFinished();

	/**
	 * Listens from ServerSession the winners of the ended match
	 */
	public abstract void listenMatchResult();

	/**
	 * listens from ServerSession the result of an attempted escape; the server communicates if the escape has been successful and the coordinates of the attempted escape
	 */
	public abstract void listenEscapeResult();
	
	/**
	 * analyzes the spotlight and invokes the graphic interface methods to show the spotted players 
	 * @param spotlightResult is  an array of strings containing the coordinates of the spotlight and the spotted players
	 */
	protected void analyzeAndShowSpotlight(String[] spotlightResult) {
		if(spotlightResult.length == 1)
			controller.getGraphicInterface().showSpottedPlayer(null, null);
		else
			for(int i=1; i<spotlightResult.length; i++){
				String[] spottedPlayer = spotlightResult[i].split("&");
				String username = spottedPlayer[0];
				Coordinates position = Coordinates.stringToCoordinates(spottedPlayer[1]);
				controller.getGraphicInterface().showSpottedPlayer(username, position);
		}
	}
	
		/**
	 * analyzes the s result and invokes the graphic interface methods to show username of the survived players 
	 * @param command is an array of strings containing the coordinates of the attack, the killed players' username and character, the survived players' username
	 */
	protected void showSurvived (String[] command) {
		String[] survived;
		int i = 0;
		while(!command[i].equals("survived"))
			i++;
		i++;
		int start = i;
		int end = command.length;
		survived = Arrays.copyOfRange(command, start, end);
		if(survived.length != 0){
			for(String singleSurvived : survived){
				String[] survivedCharacter = singleSurvived.split("&");
				controller.getGraphicInterface().showSurvived(survivedCharacter[0]);
				if(survivedCharacter[0].equals(controller.getMyPlayerName()))
					controller.getMyCharacter().removeCardFromBag(new Defense());
			}
		}	
	}
	
	/**
	 * analyzes the attack result and invokes the graphic interface methods to show username and character of the killed players 
	 * @param command is an array of strings containing the coordinates of the attack, the killed players' username and character, the survived players' username
	 * @param attackCoordinates are the coordinates of the attack
	 * @return true if an human was killed by an alien
	 */
	protected boolean showKilledAndAttackPosition(String[] command, Coordinates attackCoordinates) {
		String[] killed;
		boolean humanKilled = false;
		int i = 0;
		while(!command[i].equals("killed"))
			i++;
		i++;
		int start = i;
		while(!command[i].equals("survived"))
			i++;
		int end = i;
		killed = Arrays.copyOfRange(command, start, end);
		controller.getGraphicInterface().showAttackCoordinates(attackCoordinates);
		if(killed.length == 0){
			controller.getGraphicInterface().showKill(null, null);
			return false;
		}	
		for(String singleKilled : killed){
			String[] killedCharacter = singleKilled.split("&");
			controller.getGraphicInterface().showKill(killedCharacter[0], killedCharacter[1]);
			if(killedCharacter[2].equals(SideName.HUMAN.toString()))
				humanKilled = true;

		}
		return humanKilled;		
	}
	
	/**
	 * analyzes the attack result, and if an alien has killed an human in his attack, the boolean isStrong in the AlienCharacter is setted true
	 * @param attackResult is an array of strings containing the coordinates of the attack, the killed players' username and character, the survived players' username
	 */
	protected void analyzeAndShowAttack(String[] attackResult) {

		Coordinates attackCoordinates = Coordinates.stringToCoordinates(attackResult[1]);
		boolean humanKilled = this.showKilledAndAttackPosition(attackResult, attackCoordinates);
		showSurvived(attackResult);
		if(controller.isMyTurn() && humanKilled && controller.getMyCharacter().getSide().equals(SideName.ALIEN)){
			AlienCharacter myCharacter = (AlienCharacter) controller.getMyCharacter();
			myCharacter.setStrong(true);
		}
	}
	
	/**
	 * analyzes the exit from room by a user. It show that to user.
	 * @param exitedPlayer
	 */
	protected void analyzeAndShowExitedPlayer(String[] exitedPlayer){
		controller.getGraphicInterface().showPlayerExit(exitedPlayer[1], exitedPlayer[2]);
	}
	
}
