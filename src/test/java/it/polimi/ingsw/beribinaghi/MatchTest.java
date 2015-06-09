package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
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
		match = new Match(gameSessions, players, "testMap", "Galilei", MapModel.GALILEI,null);
	}	
	
	@Test
	public void matchNotNull(){
		assertTrue(match != null);
	}
	
	@Test
	public void moveAndPickObjectCard(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		int countDefense = 0;
		int bagSizeBefore = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getBagSize();
		while(match.getObjectsDeck().getValidCardsSize()>0){
			pickedCard = match.move(new Coordinates('n',5));
			if(pickedCard.size() == 2 && pickedCard.get(1) instanceof Defense)
				countDefense++;
		}
		int bagSizeAfter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getBagSize();
		assertTrue(bagSizeAfter - bagSizeBefore == countDefense);
		ArrayList<ObjectCard> bag = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getBag();
		for(ObjectCard obj : bag)
			match.discard(obj.toString());
	}
	
	@Test
	public void moveAndTryEscape(){
		int countSuccess = 0;
		int countFail = 0;
		
		it.polimi.ingsw.beribinaghi.playerPackage.Character currentCharacter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter();
		while(currentCharacter instanceof AlienCharacter){
			match.finishTurn();
			currentCharacter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter();
		}
		while(match.getShallopsDeck().getValidCardsSize()>0){
			match.move(new Coordinates('v',2));
			if(match.isEscapeSuccessful())
				countSuccess++;
			else
				countFail++;
		}
		assertEquals(countSuccess, countFail);
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
	public void finishedTurnsTest(){
		match.setTurnNumber(40);
		assertTrue(match.isFinished());
	}
	
	@Test
	public void moreThanOneHumanAlive(){
		match.setTurnNumber(1);
		for(Player player : match.getPlayers())
			player.getCharacter().setCurrentPosition(new Coordinates('a',1));
		assertFalse(match.isFinished());
	}
	
	@Test
	public void allHumansKilledOrEscaped(){
		for(Player player : players){
			if(player.getCharacter().getSide() == SideName.HUMAN)
				player.getCharacter().setCurrentPosition(null);
		}
		assertTrue(match.isFinished());
	}
	
	@Test
	public void calculateWinners(){
		match.setTurnNumber(40);
		int i = 0;
		while(!players.get(i).getCharacter().getSide().equals(SideName.HUMAN))
			i++;
		HumanCharacter hChar = (HumanCharacter) players.get(i).getCharacter();
		hChar.setEscaped(true);
		match.isFinished();
		assertTrue(match.getWinners().size() == 3);
	}

	
	@Test
	public void turnsNotFinishedTest(){
		match.setTurnNumber(27);
		assertFalse(match.isFinished());
	}
	

	
	
	@Test
	public void nextInitialCharacterTest(){
		match.finishTurn();
		match.finishTurn();
		match.finishTurn();
		players.get(3).getCharacter().setCurrentPosition(null);
		match.finishTurn();
		assertTrue(match.getMatchDataUpdate().getCurrentPlayer().equals(players.get(0)));
	}
	
	@Test
	public void searchCardTest(){
		match.useAndSignalObjectCard(new Teleport());
		assertTrue(match.searchUsedObjectCard(new Teleport()));
	}
	
}









