package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.gameNames.*;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

import java.util.HashMap;
import java.util.Iterator;

public class Map {
	final static int HEIGHT = 14;		//TODO aggiungi costanti
	final static int WIDTH = 23;
	private String mapName;
	private HashMap <Coordinates, Sector> map;;
	private SectorName[][] graphicMap;
	
	/**
	 * @param mapName is a map model, where all sectors type are defined
	 * 	Generate a new Map with name mapName and graphics map.
	 */
	public Map (String mapName, SectorName[][] graphicMap, DangerousSectorsDeck dangerousDeck, ShallopsDeck shallopsDeck){
		
		this.setMapName(mapName);
		DeckAssigner deckAssigner = new WatcherDeckAssigner(dangerousDeck, shallopsDeck);
	
		for (int i=0;i<graphicMap.length;i++)
			for (int j=0;j<graphicMap[i].length;j++)
			{
				Coordinates actualCoordinates = new Coordinates (Coordinates.getLetter(i),j+1);
				Sector actualSector = (Sector) (graphicMap[i][j].getSector()).clone();
				actualSector.acceptDeck(deckAssigner);
				map.put(actualCoordinates, actualSector);				
			}
		}

		
	/**
	 * return the coordinates of the first found instance of the searched sector type 
	 * @param sectorName is the sector type searched
	 * @return the coordinates of the desired sector
	 */
	public Coordinates searchSectorType (SectorName wantedSectorName){

		Sector wantedSectorType = wantedSectorName.getSector();	//sector is instance of the sector class indicated in wantedSectorName 
		Iterator<Coordinates> keySetIterator = map.keySet().iterator();

		while(keySetIterator.hasNext()){
			Coordinates nextCoordinates = keySetIterator.next();
			if(map.get(nextCoordinates).getClass() == wantedSectorType.getClass())
				return nextCoordinates;
			}
		return null;
		}

	public Sector getSector (Coordinates coordinates){
		return map.get(coordinates);
	}

	public String getMapName() {
		return mapName;
	}


	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	

	
}


