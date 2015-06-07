package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertFalse;
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

	
}
	
