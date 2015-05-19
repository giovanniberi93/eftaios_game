package it.polimi.ingsw.beribinaghi.provaMappa;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;

import java.util.ArrayList;

public class ProvaMappa {

	public Map map;

	public static void main(String[] args) {
		ProvaMappa provaMappa = new ProvaMappa();
		MapModel mapModel = new MapModel();
		ArrayList<Coordinates> reachable = new ArrayList<Coordinates>();

		provaMappa.map = new Map("pollo", MapModel.GALILEI, new DangerousSectorsDeck(), new ShallopsDeck());
		
		Coordinates initial = new Coordinates('a',1);
		reachable = provaMappa.map.getReachableCoordinates(initial, 1);
		
		for(Coordinates coord : reachable){
			System.out.println(coord.toString());
		}
	}
}
	
	
	
	


