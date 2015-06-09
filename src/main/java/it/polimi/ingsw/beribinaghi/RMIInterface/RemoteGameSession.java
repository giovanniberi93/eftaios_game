/**
 * 
 */
package it.polimi.ingsw.beribinaghi.RMIInterface;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * RMI interface that manages the game match communication
 */
public interface RemoteGameSession extends Remote {

	/**
	 * @return player's character
	 * @throws RemoteException
	 * 
	 */
	public Character getCharacter() throws RemoteException;
	/**
	 * @return the match's map
	 * @throws RemoteException
	 */
	public Map getMap() throws RemoteException;
	/**
	 * @return the name of player that is in turn
	 * @throws RemoteException
	 */
	public String getCurrentPlayer() throws RemoteException;
	/**
	 * @return true if map is ready to be notify
	 * @throws RemoteException
	 */
	public Boolean isMapNotificable() throws RemoteException;
	/**
	 * @return true if character is ready to be notify
	 * @throws RemoteException
	 */
	public Boolean isCharacterNotificable() throws RemoteException;
	/**
	 * @return true if turn is ready to be notify
	 * @throws RemoteException
	 */
	public Boolean isTurnNotificable() throws RemoteException;
	/**
	 * @return the cards picked 
	 * @throws RemoteException
	 * move the character in position coordinates
	 */
	public ArrayList<Card> move(Coordinates coordinates) throws RemoteException;
	/**
	 * @throws RemoteException
	 * make noise in coordinates if makeNoise
	 */
	public void noise(Coordinates coordinates) throws RemoteException;
	/**
	 * @throws RemoteException
	 * use card action, if is request, you must select the coordinates you want to execute action
	 */
	public void executeCardAction(ObjectCard card,Coordinates coordinates) throws RemoteException;
	/**
	 * @throws RemoteException
	 * finish turn
	 */
	public void finishTurn() throws RemoteException;
	/**
	 * @throws RemoteException
	 * get arrayList of update
	 */
	public ArrayList<String> getUpdate() throws RemoteException;
	
	/**
	 * @throws RemoteException
	 * get Coordinates of noise
	 */
	public Coordinates getNoiseCoordinates() throws RemoteException;
	
	/**
	 * get the number of the turn
	 * @return the turn number
	 * @throws RemoteException
	 */
	public int getTurnNumber() throws RemoteException;
	
	/**
	 * @throws RemoteException
	 * get the used card
	 */
	public ObjectCard getUsedCard() throws RemoteException;
	

	/**
	 * get spotted players
	 * @return array of strings containing usernames of players and their position
	 * @throws RemoteException
	 */
	public String[] getSpottedPlayer() throws RemoteException;
	
	/**
	 * @throws RemoteException
	 * get escaped player
	 * arrayList contains the result of attempt (true or false) and the coordinates
	 */
	public ArrayList<String> getEscapeResult() throws RemoteException;
	
	/**
	 * @throws RemoteException
	 * get attack result
	 * arrayList contains in fort position the coordinates of attack, after "killed=" follow by the user name
	 * and the character name of player killed
	 * after that contains "survived=" follow by the user name and the character name of player survived
	 */
	public String[] getAttackResult() throws RemoteException;

	/**
	 * @throws RemoteException
	 * if last used card is spotted, this function get the coordinates of spotted result
	 */
	public Coordinates getSpottedCoordinates() throws RemoteException;
	
	/**
	 * returns the coordinates of the last attempted escape
	 * @return the coordinates of the tried shallop
	 * @throws RemoteException
	 */
	public Coordinates getUsedShallopCoordinates() throws RemoteException;
	
	
	/**
	 * @return true if the last escape attempt has been successful
	 * @throws RemoteException
	 */
	public boolean isEscapeSuccessful() throws RemoteException;
	
	
	
	
	
	
	
}
