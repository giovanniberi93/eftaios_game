package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MapTest {

	private Map map;
	MapModel mapModel = new MapModel();
	
	@Before
	public void setupMap(){
		map = new Map("testMap", MapModel.GALILEI);
	}
	
	@Test
	public void wellConstructedMap(){
		assertTrue(map != null);
	}
	
	@Test
	public void usedShallopsNotReachableForHumans(){
		map.addUsedShallop(new Coordinates('v',2));
		ArrayList<Coordinates> adiacentCoord = map.getReachableCoordinates(new Coordinates('v',3),1,false);
		assertTrue(!adiacentCoord.contains(new Coordinates('v',2)));
	}
	
	@Test
	public void shallopsNotReachableForAliens(){
		ArrayList<Coordinates> reachableCoord = map.getReachableCoordinates(new Coordinates('v',3), 2, true);
		assertFalse(reachableCoord.contains(new Coordinates('v',2)));
	}
	
	@Test
	public void reachableCoord(){
		ArrayList<Coordinates> reachable = map.getReachableCoordinates(new Coordinates('m',8), 1, false);
		assertTrue(reachable.remove(new Coordinates('n',8)) &&
				   reachable.remove(new Coordinates('m',9)) && 
				   reachable.size() == 0);
	}
	
	@Test
	public void notValidCoordinates1(){
		Coordinates c = Coordinates.stringToCoordinates("7");
		assertNull(c);
	}
	
	@Test
	public void notValidCoordinates2(){
		Coordinates c = Coordinates.stringToCoordinates("a");
		assertNull(c);
	}
	
	@Test
	public void notValidCoordinates3(){
		Coordinates c = Coordinates.stringToCoordinates("as1");
		assertNull(c);
	}
	
	@Test
	public void notValidCoordinates4(){
		Coordinates c = Coordinates.stringToCoordinates("assd");
		assertNull(c);
	}
	
	@Test
	public void notValidCoordinates5(){
		Coordinates c = Coordinates.stringToCoordinates(null);
		assertNull(c);
	}
	
	@Test
	public void validCoordinates1(){
		Coordinates c = Coordinates.stringToCoordinates("a1");
		assertNotNull(c);
	}
	
	@Test
	public void validCoordinates2(){
		Coordinates c = Coordinates.stringToCoordinates("a01");
		assertNotNull(c);
	}
	
	@Test
	public void validCoordinates3(){
		Coordinates c = Coordinates.stringToCoordinates("a13");
		assertNotNull(c);
	}
	
	
}
	
