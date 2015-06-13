package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CardsEffectTest {

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
	public void sedativesTest(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		this.match.addToUsedCards(new Sedatives());
		pickedCard = match.move(new Coordinates('a',2));
		assertTrue(pickedCard.get(0) instanceof NothingToPick);
		
	}
	
	@Test
	public void notAllHumansKilledTest(){
		assertFalse(match.isFinished());
	}
	
	@Test
	public void attackWithDefense(){
		int killedHumans = 0;
		for(int i = 0; i<4; i++){
			players.get(i).getCharacter().setCurrentPosition(new Coordinates('a',1));
			players.get(i).getCharacter().addCardToBag(new Defense());
		}
		match.attack();
		for(int i = 0; i<4; i++){
			if(players.get(i).getCharacter().getSide() == SideName.HUMAN && !players.get(i).getCharacter().isAlive())
				killedHumans++;
		}
		assertTrue(killedHumans == 0);
	}
	
	@Test
	public void lastUsedCard(){
		match.sedatives();
		assertTrue(match.getLastUsedCard() instanceof Sedatives);
	}
	
	@Test
	public void adrenalin(){
		int i = 0;
		Player aPlayer = players.get(i);
		while(!aPlayer.getCharacter().getSide().equals(SideName.HUMAN)){
			i++;
			aPlayer = players.get(i);
		}
		HumanCharacter h = (HumanCharacter) aPlayer.getCharacter();
		h.setHasAdrenalin(true);
		assertTrue(h.getPercorrableDistance() == 2);
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
	public void teleportTest(){
		Coordinates coord = new Coordinates ('c',5);
		for(Player player : players)
			player.getCharacter().setCurrentPosition(coord);
		match.teleport();
		assertTrue(match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getCurrentPosition().equals(match.getMap().getHumanBaseCoordinates()));		
	}
	
	
	@Test
	public void spotlightTest(){
		Coordinates coord = new Coordinates ('c',5);
		for(int i = 0; i<3; i++)
			players.get(i).getCharacter().setCurrentPosition(coord);
		match.spotlight(new Coordinates('b',5));
		assertTrue(match.getSpotted().size() == 3);
	}
	
	
	
	
	
}
