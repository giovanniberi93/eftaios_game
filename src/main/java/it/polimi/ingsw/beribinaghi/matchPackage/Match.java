/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.CharactersDeck;
import it.polimi.ingsw.beribinaghi.gameNames.*;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.WrongCardTypeException;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.CharacterCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.DangerousSectorCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.AlienBase;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.HumanBase;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.CharacterFactory;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages a match
 */
public class Match {

	private String matchName;
	private ArrayList<Player> players;
	private Player currentPlayer;
	private int currentPlayerIndex;
	private int turnNumber;
	
	private ThreadLocal<DangerousSectorsDeck> dangerousSectorsDeck1 = new ThreadLocal<DangerousSectorsDeck>();
	private ObjectsDeck objectsDeck;
	private ShallopsDeck shallopsDeck;
	//private DangerousSectorsDeck dangerousSectorsDeck;
	private CharactersDeck playersDeck;
	
	private Map map;
	
	
	/**
	 * initialize the match: distribute roles, set initial positions, initialize decks
	 * @param players arrayList of connected players
	 * @param matchName name of the starting match
	 * @throws WrongCardTypeException 
	 */
	public Match(ArrayList<Player> players, String matchName, Map map) throws WrongCardTypeException{
		this.players = players;
		this.matchName = matchName;
		this.map = map;
		this.turnNumber = 0;
		this.currentPlayerIndex = 0;
		setupDecks(players.size());
		assignCharacter(players);
		setInitialPositions(players);
	}
	
	
	/**
	 * setup of the decks, according to playerNumber
	 * @param playerNumber
	 */
	private void setupDecks(int playerNumber){
		
		dangerousSectorsDeck1.set(DangerousSectorsDeck.getInstance());	//se funziona sono un toro
		dangerousSectorsDeck1.get();
		
		//dangerousSectorsDeck = DangerousSectorsDeck.getInstance();
		objectsDeck = new ObjectsDeck();
		//shallopsDeck = ShallopsDeck.getInstance()
		//playersDeck = CharactersDeck.getInstance(playerNumber)
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
	
	/**
	 * set the initial position of all the existing players; in AlienBase or HumanBase according 
	 * to the side of the character
	 * @param players is the arrayList of players
	 */
	private void setInitialPositions(ArrayList<Player> players){
		
		Coordinates alienBaseCoordinates;
		Coordinates humanBaseCoordinates;
		
		alienBaseCoordinates = map.searchSectorType(SectorName.ALIENBASE);
		humanBaseCoordinates = map.searchSectorType(SectorName.HUMANBASE);
		
		for(Player player: players){
			if(player.getCharacter().getSide() == SideName.ALIEN)
				player.setCurrentPosition(alienBaseCoordinates);
			else
				player.setCurrentPosition(humanBaseCoordinates);
			}
		}

	public void start(){
		Card pickedCard;
		//SectorCardVisitor sectorBrowser = new SectorCardVisitor();		//TODO il visitor
		//ObjectCardVisitor objectCardBrowser;		//probabilemnte lo tolgo, forse no
		int currentPlayerIndex = 0;
		Collections.shuffle(players);
		currentPlayer = players.get(0);
		
		while(!isFinished()){
			
			pickedCard = moveCharacter(currentPlayer);
			//TODO cose del turno; 
			/*
			 * devo comunicare al currentPlayer che tocca a lui; aspettare che mandi:
			 * 		carta oggetto + posizione (facolt); se usa attacco devo segnare i morti, 
			 * 			se usa spotlight devo rispondergli, se usa teleport lo sposto
			 * 		nuova posiz e gli mando la carta relativa (scialuppa, rumore, nessuna)
			 * 			
			 * 		
			 */
			currentPlayer = players.get(changePlayerIndex(currentPlayerIndex));
			turnNumber++;
			}
		
	}

	/**
	 * determines if the current match is either finished or not
	 * @return true if the match is finished, false if it is not
	 */
	public boolean isFinished(){
		int remainingHumans = 0;
		HumanCharacter human;
		
		if(this.turnNumber >= 39)		//TODO add constant. Where?
			return false;
		for(Player player: players)
			if(player.getCharacter().getSide() == SideName.HUMAN){
				human = (HumanCharacter) player.getCharacter();
				if(human.isAlive() == true && human.isEscaped() == false)
					remainingHumans++;
			}
		return (remainingHumans == 0);
	}

	/**
	 * ask the player the sector in which he wants to move; effectuate the move and return the picked card from the right Deck
	 * @param currentPlayer
	 * @return the card picked from the deck associated to the destination sector
	 */
	public Card moveCharacter(Player currentPlayer){
		/*Coordinates nextCoordinates = askNextCoordinates(currentPlayer);	//deve parlare con GameSession credo
		currentPlayer.setCurrentPosition(nextCoordinates);
		return map.getSector(nextCoordinates).pickFromAssociatedDeck();*/
		return null;
	}
	
	private Coordinates askNextCoordinates(Player currentPlayer2) {
		// TODO Auto-generated method stub
		return null;
	}


	private int changePlayerIndex (int currentPlayerIndex){
		if(currentPlayerIndex == players.size())
			return 0;
		return currentPlayerIndex++;
	}

}











