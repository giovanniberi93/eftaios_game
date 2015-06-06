package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;

import java.util.ArrayList;
import java.util.Arrays;

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
	public void copyRange(){
		String a = new String("a");
		String b = new String("b");
		String c = new String("c");
		String d = new String("d");
		String e = new String("e");
		
		String[] cosa = new String[5];
		cosa[0] = a;
		cosa[1] = b;
		cosa[2] = c;
		cosa[3] = d;
		cosa[4] = e;

		String[] sub = Arrays.copyOfRange(cosa, 1, 4);
		assertTrue(true);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
