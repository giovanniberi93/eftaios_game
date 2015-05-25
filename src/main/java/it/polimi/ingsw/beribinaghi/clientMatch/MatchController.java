/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
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

	private void turn() {
		String playerTurn = session.listenTurn(); 
		if (playerTurn.equals(myPlayerName))
			graphicInterface.managesMyTurn();
		else
			graphicInterface.notifyOthersTurn(playerTurn);
	}
	
	public void callMove(Coordinates destinationCoordinates){
		ArrayList<Card> pickedCards = new ArrayList<Card>();
		try {
			pickedCards = session.move(destinationCoordinates);
		} catch (WrongSyntaxException e) {
			System.out.println("Errore nella sintassi della comunicazione");
			e.printStackTrace();
		}
		graphicInterface.manageSectorCard(pickedCards.get(0));
		if(pickedCards.size()>1){
			graphicInterface.manageNewObjectCard((ObjectCard) pickedCards.get(1));
			this.getMyCharacter().addCardToBag((ObjectCard) pickedCards.get(1));
		}
	}

	public void callObjectCard(ArrayList<String> command) {
		graphicInterface.manageUsedObjectCard(command);
		switch(command.get(0)){
		case "teleport":
			session.teleport();
			break;
		case "adrenalin":
			session.adrenalin();
			break;
		case "sedatives":
			session.sedatives();
			break;
		case "attack":
			session.attack();
			break;
		case "spotlight":
			
			break;
		}
	}

	public void callEndTurn() {
		session.endTurn();
		turn();
	}

	public void listenOtherTurn() {

		
	}

}
