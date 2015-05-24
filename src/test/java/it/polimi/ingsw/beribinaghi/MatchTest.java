package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.DangerousSectorCard;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import org.junit.Before;
import org.junit.Test;
 

public class MatchTest {
	Match match;
	ArrayList<Player> players = new ArrayList<Player>();
	MapModel mapModel = new MapModel();

	
	@Before
	public void setup(){
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		Player player3 = new Player("Player3");
		Player player4 = new Player("Player4");
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		ArrayList<GameSessionServerSide> gameSessions = new ArrayList<GameSessionServerSide>();
		gameSessions = null;
		match = new Match(gameSessions, players, "testMap", "Galilei", MapModel.GALILEI);
		assertNotNull(match);
	}	
	
	@Test
	public void matchNotNull(){
		assertNotNull(match);
	}
	
	@Test
	public void charactersNotNull(){
		boolean unassignedCharacter = false;
		for(Player analyzedPlayer : players){
			if(analyzedPlayer.getCharacter() == null)
				unassignedCharacter = true;
		}
		assertFalse(unassignedCharacter);
	}
	
	@Test
	public void rightInitialPosition(){
		boolean rightInitialPosition = true;
		boolean tmpRight;
		for(Player analyzedPlayer : players){
			if(analyzedPlayer.getCharacter().getSide().equals(SideName.ALIEN)){
				tmpRight = (analyzedPlayer.getCharacter().getCurrentPosition().equals(match.getMap().getAlienBaseCoordinates()));
				rightInitialPosition = (rightInitialPosition && tmpRight);
			}	
			if(analyzedPlayer.getCharacter().getSide().equals(SideName.HUMAN)){
				tmpRight = (analyzedPlayer.getCharacter().getCurrentPosition().equals(match.getMap().getHumanBaseCoordinates()));
				rightInitialPosition = (rightInitialPosition && tmpRight);
			}	
		}
		assertTrue(rightInitialPosition);
	}
	
	/*@Test 
	public void dangerousSectorCardPicked(){
		Card pickedCard;
		pickedCard = match.move(new Coordinates('a',2));
		assertTrue(pickedCard instanceof DangerousSectorCard);
	}*/
	
	/*public void testStartMatch(){
		testMatchNotNull();
		match.start();
		assertTrue(true);
	}	*/
}









