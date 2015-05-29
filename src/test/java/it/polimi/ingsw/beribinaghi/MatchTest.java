package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.DangerousSectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

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
		match = new Match(gameSessions, players, "testMap", "Galilei", MapModel.GALILEI);
	}	
	
	
	@Test
	public void killEveryoneAttackTest(){
		Coordinates coord = new Coordinates ('c',5);
		for(int i = 0; i<4; i++)
			players.get(i).getCharacter().setCurrentPosition(coord);
		match.attack();
		int survived = 0;
		for (Player player : players)
			if(player.getCharacter().isAlive())
				survived++;
		assertTrue(survived == 1);
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
	
	@Test 
	public void sedativesCardEffectTest(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		this.match.addToUsedCards(new Sedatives());
		pickedCard = match.move(new Coordinates('a',2));
		assertTrue(pickedCard.get(0) == null);
		
	}
	
	@Test 
	public void dangerousSectorCardPicked(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('a',2));
		assertTrue(pickedCard.get(0) instanceof DangerousSectorCard);
	}
	
	@Test 
	public void safeSectorCardPicked(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('h',7));
		assertTrue(pickedCard.get(0) instanceof NothingToPick);
	}
	
	@Test
	public void teleportTest(){
		Coordinates coord = new Coordinates ('c',5);
		for(Player player : players)
			player.getCharacter().setCurrentPosition(coord);
		match.teleport();
		assertTrue(match.matchDataUpdate.getCurrentPlayer().getCharacter().getCurrentPosition().equals(match.getMap().getHumanBaseCoordinates()));		
	}

	
}









