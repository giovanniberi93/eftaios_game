/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
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
	private ArrayList<Player> deadPlayers = new ArrayList<Player>();
	private ArrayList<Player> escapedPlayers = new ArrayList<Player>();

	public Character getMyCharacter() {
		return myCharacter;
	}

	public MatchController(String playerName, GameInterface graphicInterface, GameSessionClientSide session) {
		this.graphicInterface = graphicInterface;
		this.session = session;
		this.graphicInterface.setController(this);
		this.session.setController(this);
		this.myPlayerName = playerName;
		map = session.getMap();
		myCharacter = session.getCharacter();
		graphicInterface.printMap(map,myCharacter.getCurrentPosition());
		graphicInterface.printCharacter(myCharacter.getName(),myCharacter.getRole(),myCharacter.getSide());
		turn();
	}

	public Map getMap() {
		return map;
	}
	
	
	public GameInterface getGraphicInterface() {
		return graphicInterface;
	}

	private void turn() {
		String currentPlayer = session.listenTurn(); 
		this.currentPlayer = currentPlayer;
		if (currentPlayer.equals(myPlayerName))
			graphicInterface.managesMyTurn();
		else{
			graphicInterface.notifyOthersTurn(currentPlayer);
			session.listenUpdate();
		}
	}
	
	public void callMove(Coordinates destinationCoordinates){
		NoiseCoordinatesSelector noiseCoordinatesSelector = new WatcherNoiseCoordinatesSelector(this);
		ArrayList<Card> pickedCards = new ArrayList<Card>();
		try {
			pickedCards = session.move(destinationCoordinates);
		} catch (WrongSyntaxException e) {
			System.out.println("Errore nella sintassi della comunicazione");
			e.printStackTrace();
		}
		if(pickedCards.size()>1)
			this.getMyCharacter().addCardToBag((ObjectCard) pickedCards.get(1));
		graphicInterface.showPickedCard(pickedCards);
		Coordinates noiseCoordinates = noiseCoordinatesSelector.select((SectorCard) pickedCards.get(0));
		session.noise(noiseCoordinates);
	}

	public void callObjectCard(ArrayList<String> command) {
		session.useObjectcard(command);
	}

	public void callEndTurn() {
		session.endTurn();
		turn();
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

}
