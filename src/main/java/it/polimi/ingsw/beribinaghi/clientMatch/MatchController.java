/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.util.ArrayList;


/**
 * this class manages all match, it manages all communications with network and graphics and it manages the model
 *
 */
public class MatchController {
	private GameInterface graphicInterface;
	private GameSessionClientSide session;
	private String myPlayerName;
	private Character myCharacter;
	private Map map;

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
		pickedCards = session.move(destinationCoordinates);
		graphicInterface.manageSectorCard(pickedCards.get(0));
		if(pickedCards.size()>1){
			graphicInterface.manageNewObjectCard(pickedCards.get(1));
		}
	}


	public void callObjectCard(ArrayList<String> command) {
		
		
		
	}

	public void callEndTurn() {
		// TODO Auto-generated method stub
		
	}

}
