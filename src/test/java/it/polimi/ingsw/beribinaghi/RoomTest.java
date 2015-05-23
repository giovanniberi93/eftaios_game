package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;
import it.polimi.ingsw.beribinaghi.serverSetup.AlreadyExistingNameException;
import it.polimi.ingsw.beribinaghi.serverSetup.MatchController;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RoomTest
{	
	
	@Test
	public void tautology() {
		assertTrue(true);
	}
	
	@Test(expected=AlreadyExistingNameException.class)
	public void testAddGame() throws AlreadyExistingNameException {
		MatchController mc = new MatchController();
		mc.createNewMatch("Ciao", new Player("PlayerA"));
		mc.createNewMatch("Ciao", new Player("PlayerB"));
	}
	
	@BeforeClass
	public static void begin(){
		//System.out.println("Begin");
	}
	
	@Before
	public void prepare(){
		//System.out.println("Prepare");
	}
	
	@After
	public void finalize(){
		//System.out.println("Finalize");
	}
	
	@AfterClass
	public static void end(){
		//System.out.println("End");
	}
	
}
