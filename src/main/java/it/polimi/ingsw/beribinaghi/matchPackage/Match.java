/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.App;
import it.polimi.ingsw.beribinaghi.decksPackage.CharactersDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.WrongCardTypeException;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Attack;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.CharacterCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;
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
	private ArrayList<GameSessionServerSide> sessions;
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
	 * @param sessions 
	 * @param players arrayList of connected players
	 * @param matchName name of the starting match 
	 */
	public Match(ArrayList<GameSessionServerSide> sessions, ArrayList<Player> players, String matchName, String mapName, SectorName[][] graphicMap){
		this.players = players;
		setupDecks(players.size());
		this.matchName = matchName;
		this.sessions = sessions;
		this.map = new Map (mapName, graphicMap, dangerousSectorsDeck, shallopsDeck);
		Collections.shuffle(players);
		currentPlayerIndex = 0;
		
		matchDataUpdate = new MatchDataUpdate(players.get(currentPlayerIndex), 1);
		for (GameSessionServerSide gameSession: sessions)
			matchDataUpdate.addObserver(gameSession);
		sendMap();
		assignCharacter(players);
		setInitialPositions(players);
		for(GameSessionServerSide gameSession: sessions)
			gameSession.notifyCharacter();
	}
	
	
	private void sendMap() {
		for(GameSessionServerSide gameSession: sessions){
			gameSession.sendMap(map);
		}
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
		playersDeck.addToDiscardPile(characterCard);
	}

	/**
	 * set the initial position of all the existing players; in AlienBase or HumanBase according 
	 * to the side of the character
	 * @param players is the arrayList of players
	 */
	private void setInitialPositions(ArrayList<Player> players){
		
		for(Player player: players){
			if(player.getCharacter().getSide() == SideName.ALIEN)
				player.getCharacter().setCurrentPosition(map.getAlienBaseCoordinates());
			else
				player.getCharacter().setCurrentPosition(map.getHumanBaseCoordinates());
			}
	}
	
	/**
	 * start match
	 */
	public void start(){
		matchDataUpdate.start();
	}

	/**
	 * Move currentPlayer in the sector with coordinates destinationCoordinates
	 * @param destinationCoordinates are the coordinates of the destination sector
	 * @return an arrayList with the card picked from the deck associated to the destination sector and the eventually found objectCard
	 */
	public ArrayList<Card> move(Coordinates destinationCoordinates){
		Player currentPlayer = matchDataUpdate.getCurrentPlayer();
		ArrayList<Card> allCards = new ArrayList<Card>();
		
		currentPlayer.getCharacter().setCurrentPosition(destinationCoordinates);
		if(matchDataUpdate.getUsedObjectCard().contains(new Sedatives()))
			return null;
		
		SectorCard pickedCard = map.getSector(destinationCoordinates).pickFromAssociatedDeck();
		allCards.add(pickedCard);
		if(pickedCard.containsObject()){
			ObjectCard objectCard = this.objectsDeck.pickCard();
			currentPlayer.getCharacter().addCardToBag(objectCard);
			allCards.add(objectCard);
		}
		if(pickedCard instanceof ShallopCard){
			ShallopCard shallopCard = (ShallopCard) pickedCard;
			if(!(shallopCard.isDamaged())){
				matchDataUpdate.getCurrentPlayer().getCharacter().setCurrentPosition(null);
				matchDataUpdate.setEscaped(true);
				this.finishTurn();
			}
			else
				matchDataUpdate.setEscaped(false);
		}
		return allCards;
	}
		
	/**
	 * Determines if the current match is either finished or not
	 * @return true if the match is finished, false if it is not
	 */
	public boolean isFinished(){
		int remainingHumans = 0;
		HumanCharacter human;
		
		if(matchDataUpdate.getTurnNumber() >= App.NUMBEROFTURNS)
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
		if(!isFinished())
			matchDataUpdate.clear(players.get(currentPlayerIndex));
		else
			matchDataUpdate.setMatchFinished();
	}

	public Map getMap() {
		return map;
	}


	public void noise(Coordinates noiseCoordinates){
		matchDataUpdate.setNoiseCoordinates(noiseCoordinates);
	}
	
	public void spotlight(Coordinates selectedCoordinates){
		ArrayList<Player> spottedPlayers = new ArrayList<Player>();
		ArrayList<Coordinates> lightedCoordinates = map.adiacentCoordinates(selectedCoordinates);
		for(Coordinates analyzedCoordinates : lightedCoordinates){
			for(Player analyzedPlayer : players)
				if(analyzedPlayer.getCharacter().getCurrentPosition().equals(analyzedCoordinates))
					spottedPlayers.add(analyzedPlayer);
		}
		matchDataUpdate.setSpottedPlayers(spottedPlayers);
	}
	
	public void discard(ObjectCard discardedCard){
		// TODO boh? devo anche aggiustare addCardToBag del character, non sto gestendo la carta in più
	}
	
	public void attack(){
		Player analyzedPlayer;
		ArrayList<Player> killed = new ArrayList<Player>();
		ArrayList<Player> survived = new ArrayList<Player>();

		Player currentPlayer = matchDataUpdate.getCurrentPlayer();
		for(int i = 0; i < players.size(); i++){
			if(i != this.currentPlayerIndex){
				analyzedPlayer = players.get(i);
				if(analyzedPlayer.getCharacter().getCurrentPosition().equals(currentPlayer.getCharacter().getCurrentPosition())){
					if(analyzedPlayer.getCharacter().getSide() == SideName.HUMAN && (analyzedPlayer.getCharacter().removeCardFromBag(new Defense()) != null))
						survived.add(analyzedPlayer);
					else{
						killed.add(analyzedPlayer);
						analyzedPlayer.getCharacter().setAlive(false);
						analyzedPlayer.getCharacter().setCurrentPosition(null);
					}	
				}
			}
		}
		if(currentPlayer.getCharacter().getSide() == SideName.HUMAN){
			ObjectCard usedCard = new Attack();
			currentPlayer.getCharacter().removeCardFromBag(usedCard);
		}
		matchDataUpdate.setAttackOutcome(killed, survived);
	}
	
	public void teleport(){
		Coordinates baseCoordinates;
		ObjectCard usedCard = new Teleport();		//crea carta dello stesso tipo usato		
		Player currentPlayer = players.get(currentPlayerIndex);
		
		matchDataUpdate.setUsedObjectCard(usedCard);		//update MatchDataUpdate con la carta usata
		baseCoordinates = map.getHumanBaseCoordinates();
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


	public String getMatchName() {
		return matchName;
	}
	
	
	
}











