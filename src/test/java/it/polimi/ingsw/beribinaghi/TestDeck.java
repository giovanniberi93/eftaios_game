package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.decksPackage.ObjectsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;

import org.junit.Test;

public class TestDeck {
	@Test
	public void tautology() {
		assertTrue(true);
	}
	
	@Test
	public void creationDeck() {
		ObjectsDeck od = new ObjectsDeck();
		ObjectCard oc = (ObjectCard) od.pickCard();
		assertTrue(true);
	}
}
