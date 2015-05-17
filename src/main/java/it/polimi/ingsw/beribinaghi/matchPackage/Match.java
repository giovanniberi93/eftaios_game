/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.CharactersDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.WrongCardTypeException;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.CharacterCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
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
	private int currentPlayerIndex;
	private int turnNumber;
	
	private ObjectsDeck objectsDeck;
	private ShallopsDeck shallopsDeck;
	private DangerousSectorsDeck dangerousSectorsDeck;
	private CharactersDeck playersDeck;
	
	private Map map;
	
	public MatchDataUpdate matchDataUpdate;
	
	
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
		Collections.shuffle(players);
		currentPlayerIndex = 0;
		
		matchDataUpdate = new MatchDataUpdate(players.get(currentPlayerIndex), 1);
		setupDecks(players.size());
		assignCharacter(players);
		setInitialPositions(players);
	}
	
	
	/**
	 * setup of the decks, according to playerNumber
	 * @param playerNumber
	 */
	private void setupDecks(int playerNumber){
		
		dangerousSectorsDeck = new DangerousSectorsDeck();
		objectsDeck = new ObjectsDeck();
		shallopsDeck = new ShallopsDeck();
		playersDeck =  new CharactersDeck(playerNumber);
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
			player.setCharacter(factory.getNewCharacter(characterCard.getCharacterName()));	//factory method
			
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

	/**
	 * Move currentPLayer in the sector with coordinates destinationCoordinates
	 * @param destinationCoordinates are the coordinates of the destination sector
	 * @return the card picked from the deck associated to the destination sector
	 */
	public Card move(Coordinates destinationCoordinates){
		matchDataUpdate.getCurrentPlayer().setCurrentPosition(destinationCoordinates);
		return map.getSector(destinationCoordinates).pickFromAssociatedDeck();
	}
		
	/**
	 * Determines if the current match is either finished or not
	 * @return true if the match is finished, false if it is not
	 */
	public boolean isFinished(){
		int remainingHumans = 0;
		HumanCharacter human;
		
		if(matchDataUpdate.getTurnNumber() >= 39)		//TODO add constant. Where?
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
	 * Is called from currentPlayer when he finishes his turn; sets the new currentPlayer
	 */
	public void finishTurn(){
		if(currentPlayerIndex == players.size())
			currentPlayerIndex = 0;
		else currentPlayerIndex++;
		
		turnNumber++;
		if(!isFinished())		//TODO sennò?
			matchDataUpdate = new MatchDataUpdate(players.get(currentPlayerIndex), (matchDataUpdate.getTurnNumber())+1);
		}

	public void noise(Coordinates noiseCoordinates){
		matchDataUpdate.setNoiseCoordinates(noiseCoordinates);
	}
	
	public void teleport(){
		Coordinates baseCoordinates;
		
		if(players.get(currentPlayerIndex).getCharacter().getSide() == SideName.ALIEN)
			baseCoordinates = map.searchSectorType(SectorName.ALIENBASE);
		else
			baseCoordinates = map.searchSectorType(SectorName.HUMANBASE);
		move(baseCoordinates);
	}
	
	private boolean discardObjectCard (Player player, ObjectCard usedObject)
	{
		for(int i = 0; i<3; i++);
			return true;
	}
}











