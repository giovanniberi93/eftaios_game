/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.CharactersDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.WrongCardTypeException;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Attack;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.CharacterCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;
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
	 */
	public Match(ArrayList<Player> players, String matchName, Map map){	//TODO riceve graphicMap non map
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
	private void assignCharacter(ArrayList<Player> players){
		CharacterCard characterCard = null;
		CharacterFactory factory = new CharacterFactory();

		for(Player player: players){
			characterCard = (CharacterCard) playersDeck.pickCard();
			player.setCharacter(factory.getNewCharacter(characterCard.getCharacterName()));	//factory method
			
		}
		try {
			playersDeck.addToDiscardPile(characterCard);
		} catch (WrongCardTypeException e) {
			System.out.println("Aggiunta carta Character al mazzo sbagliato!");
		}
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
		
	}

	/**
	 * Move currentPLayer in the sector with coordinates destinationCoordinates
	 * @param destinationCoordinates are the coordinates of the destination sector
	 * @return the card picked from the deck associated to the destination sector
	 */
	public Card move(Coordinates destinationCoordinates){
		matchDataUpdate.getCurrentPlayer().setCurrentPosition(destinationCoordinates);
		if(matchDataUpdate.getUsedObjectCard().contains(new Sedatives()))
			return null;
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
				if(human.isAlive() == true && human.isEscaped() == false)		//TODO rimetti isalive
					remainingHumans++;
			}
		return (remainingHumans == 0);
	}

	/**
	 * Is called from currentPlayer when he finishes his turn; sets the new currentPlayer
	 */
	public void finishTurn(){
		if(currentPlayerIndex == players.size()-1)
			currentPlayerIndex = 0;
		else currentPlayerIndex++;
		
		if(!isFinished())		//TODO sennò?
			matchDataUpdate = new MatchDataUpdate(players.get(currentPlayerIndex), (matchDataUpdate.getTurnNumber())+1);
		}

	public void noise(Coordinates noiseCoordinates){
		matchDataUpdate.setNoiseCoordinates(noiseCoordinates);
	}
	
	public void discard(ObjectCard discardedCard){
		// TODO boh? devo anche aggiustare addCardToBag del character, non sto gestendo la carta in più
	}
	
	public void attack(){
		Player checkedPlayer;
		Player currentPlayer = matchDataUpdate.getCurrentPlayer();
		for(int i = 0; i < players.size(); i++){
			if(i != this.currentPlayerIndex){
				checkedPlayer = players.get(i);
				if(checkedPlayer.getCurrentPosition().equals(currentPlayer.getCurrentPosition())){
					matchDataUpdate.setRecentKills(checkedPlayer);
					checkedPlayer.getCharacter().setAlive(false);
					checkedPlayer.setCurrentPosition(null);
				}
			}
		}
		if(currentPlayer.getCharacter().getSide() == SideName.HUMAN){
			ObjectCard usedCard = new Attack();
			matchDataUpdate.setUsedObjectCard(usedCard);
			currentPlayer.getCharacter().removeCardFromBag(usedCard);
		
		}
	}
	
	public void teleport(){
		Coordinates baseCoordinates;
		ObjectCard usedCard = new Teleport();		//crea carta dello stesso tipo usato		
		Player currentPlayer = players.get(currentPlayerIndex);
		
		matchDataUpdate.setUsedObjectCard(usedCard);		//update MatchDataUpdate con la carta usata
		if(currentPlayer.getCharacter().getSide() == SideName.ALIEN)		//setta base giusta
			baseCoordinates = map.searchSectorType(SectorName.ALIENBASE);
		else
			baseCoordinates = map.searchSectorType(SectorName.HUMANBASE);
		move(baseCoordinates);
		
		currentPlayer.getCharacter().removeCardFromBag(usedCard);		//toglie la carta usata dal bag del currentPlayer
	}
	
	public void adrenalin(){
		ObjectCard usedCard = new Adrenalin();
		Player currentPlayer = players.get(currentPlayerIndex);
		
		matchDataUpdate.setUsedObjectCard(usedCard);
		currentPlayer.getCharacter().removeCardFromBag(usedCard);		//toglie la carta usata dal bag del currentPlayer
	}
	
	public void sedatives(){
		ObjectCard usedCard = new Sedatives();
		Player currentPlayer = matchDataUpdate.getCurrentPlayer();
		
		matchDataUpdate.setUsedObjectCard(usedCard);
		currentPlayer.getCharacter().removeCardFromBag(usedCard);		//toglie la carta usata dal bag del currentPlayer
	}
	
	
	
}











