/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.ShallopSector;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;


/**
 * this class manages all match; it manages all communications with network and graphics and it manages the model
 *
 */
public class MatchController {
	private GameInterface graphicInterface;
	private GameSessionClientSide session;

	private String myPlayerName;
	private Character myCharacter;
	private String currentPlayer;
	private Map map;
	private boolean myTurn;
	private int turnNumber;
	private boolean attemptedEscape;
	private boolean matchFinished;

	public Character getMyCharacter() {
		return myCharacter;
	}

	/**
	 * Constructs the matchController assigning the player name, the graphic interface (CLI or GUI) and the gamesession (socket or RMI)
	 * @param playerName is the name of the player
	 * @param graphicInterface is the graphic interface
	 * @param session is the gamesession
	 */
	public MatchController(String playerName, GameInterface graphicInterface, GameSessionClientSide session) {
		this.graphicInterface = graphicInterface;
		this.session = session;
		this.graphicInterface.setController(this);
		this.session.setController(this);
		this.myPlayerName = playerName;
		map = session.getMap();
		myCharacter = session.getCharacter();
		turn(false);
		graphicInterface.startRapresenting();
		if (!this.isMyTurn() && !isMatchFinished())
			session.listenUpdate();
	}

	
	public Map getMap() {
		return map;
	}
	
	public Coordinates getMyPosition() {
		return myCharacter.getCurrentPosition();
	}

	
	public GameInterface getGraphicInterface() {
		return graphicInterface;
	}

	/**
	 * Listens from the session the message assigning the current player; if the player is in his turn, the method managesMyTurn is invoked and the map printed, otherwise the session start listening the updates
	 * 
	 */
	public void turn(Boolean type) {
		String currentPlayer = session.listenTurn(); 
		this.currentPlayer = currentPlayer;
		if (currentPlayer.equals(myPlayerName))
			this.myTurn = true;
		else
			this.myTurn = false;
		if (type){
			graphicInterface.changedTurn();
			if (!this.isMyTurn())	//TODO prova asda
				session.listenUpdate();	
		}
	}
	
	
	public GameSessionClientSide getSession() {
		return session;
	}

	/**
	 * Call in the gamesession the "move" command, manages the noise in the right position and call showPickedCards in the graphic interface
	 * @param destinationCoordinates are the coordinates of the destination coordinates
	 */
	public void callMove(Coordinates destinationCoordinates){
		boolean fullBag = false;
		NoiseCoordinatesSelector noiseCoordinatesSelector = new WatcherNoiseCoordinatesSelector(this);
		ArrayList<Card> pickedCards = new ArrayList<Card>();
		try {
			pickedCards = session.move(destinationCoordinates);
		} catch (WrongSyntaxException e) {
		}
		myCharacter.setCurrentPosition(destinationCoordinates);
		if(pickedCards.size()>1){
			fullBag = getMyCharacter().addCardToBag((ObjectCard) pickedCards.get(1));
		}
			
		graphicInterface.showPickedCards(pickedCards);
		noiseCoordinatesSelector.select((SectorCard) pickedCards.get(0));
		if(fullBag)
			graphicInterface.selectObjectToDiscard();
		if(map.getSector(destinationCoordinates) instanceof ShallopSector)
			session.listenEscapeResult();
	}
	
	/**
	 * Pass to clientSession the coordinates of the noise
	 * @param noiseCoordinates are the coordinates of the noise
	 */
	public void makeNoise(Coordinates noiseCoordinates){
		session.noise(noiseCoordinates);
	}

	/**
	 * call in the gamesession the command corresponding to a selected object card
	 * @param command is the arraylist containing the name of the used card (in position 0) and the coordinates of application (in case of spotlight)
	 */
	public void callObjectCard(ArrayList<String> command) {
		session.useObjectcard(command);
		if(command.get(0).equals("adrenalin"))
			myCharacter.setHasAdrenalin(true);
		if(command.get(0).equals("teleport"))
			myCharacter.setCurrentPosition(map.getHumanBaseCoordinates());
		ObjectCard usedCard = ObjectCard.stringToCard(command.get(0));
		myCharacter.removeCardFromBag(usedCard);
	}

	/**
	 * invokes the method endTurn in the session, and calls the method turn to listen the new current player
	 */
	public void callEndTurn() {
		attemptedEscape = false;
		myCharacter.setHasAdrenalin(false);
		session.endTurn();
		boolean finished = session.isMatchFinished();
		if(!finished)
			turn(true);			
		else
			session.listenMatchResult();
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @return the myTurn
	 */
	public boolean isMyTurn() {
		return myTurn;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	/**
	 *  sets the boolean matchFinished at true
	 */
	public void endMatch() {
		setMatchFinished(true);
		System.out.println("siamo riusciti ad arrivare fin qua");
		
	}

	
	public String getMyPlayerName() {
		return myPlayerName;
	}

	public boolean isAttemptedEscape() {
		return attemptedEscape;
	}

	public void setAttemptedEscape(boolean attemptedEscape) {
		this.attemptedEscape = attemptedEscape;
	}

	public boolean isMatchFinished() {
		return matchFinished;
	}

	public void setMatchFinished(boolean matchFinished) {
		this.matchFinished = matchFinished;
	}


}
