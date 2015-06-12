package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.decksPackage.CharactersDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.DangerousSectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CardsTest {
	
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
	public void decksNotNull(){
		ShallopsDeck sh = match.getShallopsDeck();
		ObjectsDeck ob = match.getObjectsDeck();
		CharactersDeck ch = match.getCharactersDeck();
		DangerousSectorsDeck ds = match.getDangerousSectorsDeck();
		assertTrue(sh!=null && ob!=null && ch!=null && ds!=null);
	}
	
	
	@Test
	public void restoreDeck(){
		
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
		it.polimi.ingsw.beribinaghi.playerPackage.Character currentCharacter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter();
		while(currentCharacter instanceof AlienCharacter){
			match.finishTurn();
			currentCharacter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter();
		}
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		pickedCard = match.move(new Coordinates('v',2));
		assertTrue(pickedCard.get(0) instanceof NothingToPick);
	}
	
	@Test
	public void discardCard(){
		int sizeBefore = match.getObjectsDeck().getDiscardPileSize();
		match.discard("teleport");
		int sizeAfter = match.getObjectsDeck().getDiscardPileSize();
		assertTrue(sizeBefore+1 == sizeAfter);
	}
	
	@Test
	public void shallopSectorWithSedatives(){
		it.polimi.ingsw.beribinaghi.playerPackage.Character currentCharacter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter();
		while(currentCharacter instanceof AlienCharacter){
			match.finishTurn();
			currentCharacter = match.getMatchDataUpdate().getCurrentPlayer().getCharacter();
		}
		ArrayList<Card> pickedCard =  new ArrayList<Card>();
		match.sedatives();
		pickedCard = match.move(new Coordinates('v',2));
		assertTrue(pickedCard.get(0) instanceof NothingToPick);
	}
	
	@Test
	public void removeCard(){
		it.polimi.ingsw.beribinaghi.playerPackage.Character ch = players.get(0).getCharacter();
		ch.addCardToBag(new Sedatives());
		assertTrue(ch.removeCardFromBag(new Sedatives()));
	}

	
}
