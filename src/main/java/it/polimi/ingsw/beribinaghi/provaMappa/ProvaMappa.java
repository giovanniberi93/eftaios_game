package it.polimi.ingsw.beribinaghi.provaMappa;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.Sector;

import java.util.ArrayList;

public class ProvaMappa {

	public Map map;
	SectorName[][] graphicMap = {	
			{SectorName.ALIENBASE,	SectorName.HUMANBASE, 	SectorName.DANGEROUS},
			{SectorName.BLANK, 		SectorName.DANGEROUS,	SectorName.DANGEROUS},
			{SectorName.SAFE,		SectorName.SHALLOP,		SectorName.DANGEROUS}
		};
	
	public static void main(String[] args) {
		ProvaMappa provaMappa = new ProvaMappa();
		Coordinates coordDiProva = new Coordinates('z',1);
		
		provaMappa.map = new Map("mappaDiProva",provaMappa.graphicMap,new DangerousSectorsDeck(), new ShallopsDeck());
		System.out.println("Mappa creata");
		//Coordinates coordBaseAlienaForse = provaMappa.map.searchSectorType(SectorName.ALIENBASE);
		
		Sector sectorCercato = provaMappa.map.getSector(new Coordinates('a',1));
		/*System.out.println("dovrebbe stampare z1: " + coordDiProva.getLetter() + coordDiProva.getNumber());
		
		System.out.println("base aliena "+provaMappa.map.getSector(new Coordinates('a',1)));
		
		


		
		Coordinates coordShallop = provaMappa.map.searchSectorType(SectorName.ALIENBASE);
		System.out.println("prova  "+coordShallop.toString());
		*/
		ArrayList<Coordinates> adiacentCoordinates = provaMappa.map.adiacentCoordinates(new Coordinates('a',2));
		
		
		System.out.println("Size: "+adiacentCoordinates.size());
		for(Coordinates coord : adiacentCoordinates){
			System.out.println(coord.toString());
		}
	}
}
	
	
	
	


