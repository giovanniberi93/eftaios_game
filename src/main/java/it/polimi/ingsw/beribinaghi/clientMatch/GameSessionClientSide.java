/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.util.ArrayList;

/**
 * interface for managing the communication with server
 * it receives command that will be sent to server
 *
 */
public interface GameSessionClientSide {

	public void setController(MatchController matchController);

	public Character getCharacter();

	/**
	 *	Listens from the ServerGameSession the username of the new Currentplayer
	 * @return username of currentPlayer
	 */
	public String listenTurn();

	/**
	 * Listens from the ServerGameSession name, height, width of the map and the abbreviations of all the sectors of the map
	 * @return the Map of the game
	 */
	public Map getMap();
	
	/**
	 * Communicates to ServerSession the Move command 
	 * @param destinationCoord are the coordinates of the destination sector
	 * @return the cards picked after the movement; the first card is the sectorCard (noiseInAnySector, noiseInYourSector, Silencem NothingToPick, ShallopCard), the second is the ObjectCard (if exists)
	 * @throws WrongSyntaxException
	 */
	public ArrayList<Card> move(Coordinates destinationCoord) throws WrongSyntaxException;

	/**
	 * Communicates to ServerSession the coordinates of the noise. It can be a Coordinate, SILENCE, or the signal of no noise (in socket, the string "nothing");  it's invoked only by currentPlayer.
	 * @param noiseCoordinates
	 */
	public void noise(Coordinates noiseCoordinates);
	
	/**
	 * Communicates to ServerSession the card selected to use; the attack is not used as a card, but as a distinct choice because it can be done also by the aliens;  this method is invoked only by currentPlayer.
	 * @param command
	 */
	public void useObjectcard(ArrayList<String> command);

	/**
	 * Communicates to ServerSession the signal of the end of the turn. It's invoked only by currentPlayer.
	 */
	public void endTurn();

	/**
	 * Listens from ServerSession the updates of the match during other's turn, like: used cards, noise coordinates, attack or escape results
	 */
	public void listenUpdate();

	/**
	 * Communicates to serverSession the objectCard that has been discarded
	 * @param discarded 
	 */
	public void signalDiscardedObjectCard(ObjectCard discarded);

	public boolean isMatchFinished();

	public void listenMatchResult();

	public void listenEscapeResult();
}
