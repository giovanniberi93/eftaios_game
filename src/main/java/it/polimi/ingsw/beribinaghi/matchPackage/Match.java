/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.decksPackage.*;
import it.polimi.ingsw.beribinaghi.playerPackage.CharacterFactory;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

/**
 * Manages a match
 */
public class Match {

	private String matchName;
	private ArrayList<Player> players;
	private Player currentPlayer;
	private int turnNumber;
	
	private ObjectsDeck objectsDeck;
	private ShallopsDeck shallopsDeck;
	private SectorsDeck sectorsDeck;
	private CharactersDeck playersDeck;
	
	
	/**
	 * initialize the match: distribute roles, set initial positions, initialize decks
	 * @param players arrayList of connected players
	 * @param matchName name of the starting match
	 * @throws WrongCardTypeException 
	 */
	public Match(ArrayList<Player> players, String matchName) throws WrongCardTypeException{
		this.players = players;
		this.matchName = matchName;
		this.turnNumber = 0;
		setupDecks(players.size());
		assignCharacter(players);
		setInitialPositions(players);
	}
	
	
	/**
	 * setup of the decks, according to playerNumber
	 * @param playerNumber
	 */
	private void setupDecks(int playerNumber){
		sectorsDeck = new SectorsDeck();
		objectsDeck = new ObjectsDeck();
		shallopsDeck = new ShallopsDeck();
		playersDeck = new CharactersDeck(playerNumber);
	}
	
	
	/**
	 * randomly assign a character to each player
	 * @param players arrayList of players
	 * @throws WrongCardTypeException 
	 */
	private void assignCharacter(ArrayList<Player> players) throws WrongCardTypeException{
		CharacterCard characterCard = null;
		CharacterFactory factory = new CharacterFactory();
		
		for(Player player: players){
			characterCard = (CharacterCard) playersDeck.pickCard();
			player.setCharacter(factory.getNewCharacter(characterCard.getCharacterName()));	//factory methos
			}
		playersDeck.addToDiscardPile(characterCard);
		}
	
	private void setInitialPositions(ArrayList<Player> players){
		
		//TODO è troppo tardi e non c'ho sbatti
	}
}














