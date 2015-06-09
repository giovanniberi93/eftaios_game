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
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Spotlight;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.CharacterFactory;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;
import it.polimi.ingsw.beribinaghi.serverSetup.PreMatch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;


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
	private int firstPlayerIndex;
	private int turnNumber;
	
	private ArrayList<Player> killed = new ArrayList<Player>();
	private ArrayList<Player> survived = new ArrayList<Player>();
	private ArrayList<Player> spotted = new ArrayList<Player>();
	private Set<Player> winners = new HashSet<Player>();
	private ArrayList<ObjectCard> usedCards = new ArrayList<ObjectCard>();
	private Coordinates noiseCoordinates;
	private Boolean successfulEscape;
	private Coordinates spotlightCoordinates;
	private boolean lastHumanKilled = false;
	
	private MatchDataUpdate matchDataUpdate;
	private Coordinates usedShallopCoordinates;
	private PreMatch preMatch;
	private Timer timer;
	private TimerManager timerManager;
	
	
	/**
	 * initialize the match: distribute roles, set initial positions, initialize decks
	 * @param sessions 
	 * @param players arrayList of connected players
	 * @param matchName name of the starting match 
	 */
	public Match(ArrayList<GameSessionServerSide> sessions, ArrayList<Player> players, String matchName, String mapName, SectorName[][] graphicMap,PreMatch preMatch){
		this.players = players;
		this.preMatch = preMatch;
		setupDecks(players.size());
		this.matchName = matchName;
		this.sessions = sessions;
		this.map = new Map (mapName, graphicMap, dangerousSectorsDeck, shallopsDeck);
		Collections.shuffle(players);
		currentPlayerIndex = 0;
		firstPlayerIndex = 0;
		timer = new Timer();
		timerManager = new TimerManager(this);
		timer.schedule(timerManager, App.WAITFINISHTURN);
		turnNumber = 1;
		setMatchDataUpdate(new MatchDataUpdate(players.get(currentPlayerIndex)));
		for (GameSessionServerSide gameSession: sessions)
			getMatchDataUpdate().addObserver(gameSession);
		sendMap();
		assignCharacter(players);
		setInitialPositions(players);
		for(GameSessionServerSide gameSession: sessions)
			gameSession.notifyCharacter();
		timer = new Timer();
		timer.schedule(new TimerManager(this), App.WAITFINISHTURN);
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
	
	
	
	public DangerousSectorsDeck getDangerousSectorsDeck() {
		return dangerousSectorsDeck;
	}


	public CharactersDeck getCharactersDeck() {
		return playersDeck;
	}


	public ShallopsDeck getShallopsDeck() {
		return shallopsDeck;
	}
	
		
	public ObjectsDeck getObjectsDeck() {
		return objectsDeck;
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
	 * create an objectCard from its name, adds it to the discardPile of objectsDeck, and signals the discarding to all gamesessions
	 * @param discardedCardName is the name of the discarded card 
	 */
	public void discard(String discardedCardName) {
		ObjectCard discarded = ObjectCard.stringToCard(discardedCardName);
		getObjectsDeck().addToDiscardPile(discarded);
		getMatchDataUpdate().setDiscardedObject();
	}
	
	/**
	 * @return array list of player killed to attack
	 */
	public ArrayList<Player> getKilled() {
		return killed;
	}

	/**
	 * @return array list of player survived (using defense card) to attack
	 */
	public ArrayList<Player> getSurvived() {
		return survived;
	}

	

	/**
	 * @param successfulEscape
	 * set success full escape
	 */
	public void setSuccessfulEscape(Boolean successfulEscape) {
		this.successfulEscape = successfulEscape;
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
	
	
	public Boolean isEscapeSuccessful() {
		return successfulEscape;
	}


	public Coordinates getSpotlightCoordinates() {
		return spotlightCoordinates;
	}


	public Coordinates getUsedShallopCoordinates() {
		return usedShallopCoordinates;
	}

	/**
	 * start match
	 */
	public void start(){
		getMatchDataUpdate().start();
	}

	/**
	 * Move currentPlayer in the sector with coordinates destinationCoordinates
	 * @param destinationCoordinates are the coordinates of the destination sector
	 * @return an arrayList with the card picked from the deck associated to the destination sector and the eventually found objectCard
	 */
	public ArrayList<Card> move(Coordinates destinationCoordinates){
		Player currentPlayer = getMatchDataUpdate().getCurrentPlayer();
		ArrayList<Card> allCards = new ArrayList<Card>();
		
		currentPlayer.getCharacter().setCurrentPosition(destinationCoordinates);
		SectorCard pickedCard = map.getSector(destinationCoordinates).pickFromAssociatedDeck();
		if(pickedCard instanceof ShallopCard){
			ShallopCard shallopCard = (ShallopCard) pickedCard;
			usedShallopCoordinates = destinationCoordinates;
			allCards.add(new NothingToPick());
			if(!(shallopCard.isDamaged())){
				if(players.get(firstPlayerIndex).getUser().equals(currentPlayer.getUser())){
					firstPlayerIndex = getNextValidPlayerIndex();
					turnNumber--;
				}
				successfulEscape = true;
				HumanCharacter currentCharacter = (HumanCharacter) currentPlayer.getCharacter();
				currentCharacter.setCurrentPosition(null);
				currentCharacter.setEscaped(true);
			}
			else
				successfulEscape = false;
		}
		else{
			if(searchUsedObjectCard(new Sedatives())){
				allCards.add(new NothingToPick());
			}
			else{
				allCards.add(pickedCard);
				if(pickedCard.containsObject() && !objectsDeck.isEmpty()){
					ObjectCard objectCard = this.objectsDeck.pickCard();
					if(objectCard instanceof Defense)
						currentPlayer.getCharacter().addCardToBag(objectCard);
					allCards.add(objectCard);
				}
			}
		}
		return allCards;
	}
		
	/**
	 * Determines if the current match is either finished or not
	 * @return true if the match is finished, false if it is not
	 */
	public boolean isFinished(){
		int remainingHumans = 0;
		boolean isFinished = false;
		
		if(turnNumber > App.NUMBEROFTURNS)
			isFinished = true;
		else{
			for(Player player: players)
				if(player.getCharacter().getSide() == SideName.HUMAN &&
				   player.getCharacter().getCurrentPosition() != null)
						remainingHumans++;
			if (remainingHumans == 0)
				isFinished = true;
		}
		if(isFinished)
			calculateWinners();
		return isFinished;			
	}

	
	
	private void calculateWinners() {
		if(turnNumber > App.NUMBEROFTURNS || lastHumanKilled){
			for(Player player : players)
				if(player.getCharacter().getSide().equals(SideName.ALIEN) && player.getCharacter().getCurrentPosition() != null)
					winners.add(player);
		}
		for(Player player : players)
			if(player.getCharacter().getSide().equals(SideName.HUMAN)){
				HumanCharacter human = (HumanCharacter) player.getCharacter();
				if(human.isEscaped())
					winners.add(player);
			}
	}


	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	

	public ArrayList<Player> getPlayers() {
		return players;
	}


	/**
	 * Is called from currentPlayer when he finishes his turn; sets the new currentPlayer
	 */
	public void finishTurn(){
		if(!isFinished()){
			currentPlayerIndex = getNextValidPlayerIndex();
			if(currentPlayerIndex == firstPlayerIndex)
				turnNumber++;
			usedCards.clear();
			spotted.clear();
			getMatchDataUpdate().clear(players.get(currentPlayerIndex));
			timer.cancel();
			timer = new Timer();
			timer.schedule(timerManager, App.WAITFINISHTURN);
		}
		else{
			timer.cancel();
			getMatchDataUpdate().setMatchFinished();
			preMatch.finish();
		}
	}

	
	public ArrayList<Player> getWinners() {
		ArrayList<Player> winnersList = new ArrayList<Player>();
		winnersList.addAll(winners);
		return winnersList;
	}


	private int getNextValidPlayerIndex() {
		int index = currentPlayerIndex;
		do{
			if(index == players.size()-1)
				index = 0;
			else 
				index++;
		}
		while(players.get(index).getCharacter().getCurrentPosition() == null && !(index==currentPlayerIndex));
		return index;
	}


	public Map getMap() {
		return map;
	}

	public void addToUsedCards(ObjectCard card){
		this.usedCards.add(card);
	}
	
	

	public ArrayList<Player> getSpotted() {
		return spotted;
	}


	public void noise(Coordinates noiseCoordinates){
		getMatchDataUpdate().setNoiseCoordinates();
	}
	
	public void spotlight(Coordinates selectedCoordinates){
		ArrayList<Coordinates> lightedCoordinates = map.adiacentCoordinates(selectedCoordinates);
		for(Coordinates analyzedCoordinates : lightedCoordinates){
			for(Player analyzedPlayer : players)
				if(analyzedPlayer.getCharacter().getCurrentPosition() != null && analyzedPlayer.getCharacter().getCurrentPosition().equals(analyzedCoordinates))
					this.spotted.add(analyzedPlayer);
		}
		spotlightCoordinates = selectedCoordinates;
		useAndSignalObjectCard(new Spotlight());
		getMatchDataUpdate().setSpottedPlayers();
	}

	
	public boolean searchUsedObjectCard(ObjectCard searchedCard) {
		for(ObjectCard card : usedCards)
			if(searchedCard.getClass().equals(card.getClass()))
				return true;
		return false;
	}
	
	public void attack(){
		Player analyzedPlayer;
		int remainingHumans;
		Player currentPlayer = getMatchDataUpdate().getCurrentPlayer();
		for(int i = 0; i < players.size(); i++){
			if(i != this.currentPlayerIndex){
				analyzedPlayer = players.get(i);
				if(analyzedPlayer.getCharacter().getCurrentPosition() != null && analyzedPlayer.getCharacter().getCurrentPosition().equals(currentPlayer.getCharacter().getCurrentPosition())){
					if(analyzedPlayer.getCharacter().getSide() == SideName.HUMAN && (analyzedPlayer.getCharacter().removeCardFromBag(new Defense())))
						this.survived.add(analyzedPlayer);
					else{
						this.killed.add(analyzedPlayer);
						analyzedPlayer.getCharacter().setAlive(false);
						analyzedPlayer.getCharacter().setCurrentPosition(null);
						remainingHumans = 0;
						for(Player player : players)
							if(player.getCharacter().getSide() == SideName.HUMAN && player.getCharacter().getCurrentPosition() != null)
								remainingHumans++;
						if(remainingHumans == 0)
							lastHumanKilled = true;
						if(i == firstPlayerIndex)
							firstPlayerIndex = getNextValidPlayerIndex();
					}	
				}
			}
		}
		if(currentPlayer.getCharacter().getSide() == SideName.HUMAN){
			useAndSignalObjectCard(new Attack());

		}
		getMatchDataUpdate().setAttackOutcome();
		killed.clear();
		survived.clear();
	}
	
	public void teleport(){
		Coordinates baseCoordinates;

		baseCoordinates = map.getHumanBaseCoordinates();
		move(baseCoordinates);
		useAndSignalObjectCard(new Teleport());

	}
	
	public void adrenalin(){
		useAndSignalObjectCard(new Adrenalin());
	}
	
	public void sedatives(){
		useAndSignalObjectCard(new Sedatives());
	}

	public void useAndSignalObjectCard(ObjectCard card){
		usedCards.add(card);
		objectsDeck.addToDiscardPile(card);
		getMatchDataUpdate().setUsedObjectCard();
	}

	public String getMatchName() {
		return matchName;
	}
	
	
	public int getTurnNumber() {
		return turnNumber;
	}


	public ObjectCard getLastUsedCard() {
		return usedCards.get(usedCards.size()-1);
	}


	public Coordinates getNoiseCoordinates() {
		return noiseCoordinates;
	}


	public void setNoiseCoordinates(Coordinates noiseCoordinates) {
		this.noiseCoordinates = noiseCoordinates;
	}


	public MatchDataUpdate getMatchDataUpdate() {
		return matchDataUpdate;
	}


	private void setMatchDataUpdate(MatchDataUpdate matchDataUpdate) {
		this.matchDataUpdate = matchDataUpdate;
	}


	
	
	
}


