package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.DangerousSectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DecksTest {
	
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
	public void creationDeck() {
		ObjectsDeck od = new ObjectsDeck();
		ObjectCard oc = od.pickCard();
		assertTrue(oc instanceof ObjectCard);
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
	public void alienBaseCardPicked(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('l',6));
		assertTrue(pickedCard.get(0) instanceof NothingToPick);
	}
	
	@Test
	public void humanBaseCardPicked(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('l',8));
		assertTrue(pickedCard.get(0) instanceof NothingToPick);
	}
	
	@Test
	public void shallopCardPicked(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('v',2));
		assertTrue(pickedCard.get(0) instanceof ShallopCard);
	}
	
	@Test
	public void shallopSectorWithSedatives(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		match.sedatives();
		pickedCard = match.move(new Coordinates('v',2));
		assertTrue(pickedCard.get(0) instanceof ShallopCard);
	}
	
	@Test
	public void escapeResult(){
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('v',2));
		ShallopCard shallopCard = (ShallopCard) pickedCard.get(0);
		assertTrue(match.isSuccessfulEscape() != shallopCard.isDamaged());
	}

	
}
