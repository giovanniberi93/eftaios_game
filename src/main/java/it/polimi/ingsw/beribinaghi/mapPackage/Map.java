package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.gameNames.SectorName;

import java.util.HashMap;
import java.util.Iterator;

public class Map {
	final int HEIGHT = 14;		//TODO aggiungi costanti
	final int WIDTH = 23;
	private String mapName;
	private HashMap <Coordinates, Sector> map;
	private HashMap <Coordinates, SectorName> mapModel;
	
	/**
	 * @param mapName
	 * 	Generate a new Map with name mapName and graphics grMap.
	 */
	public Map (String mapName, HashMap mapModel){
		this.setMapName(mapName);
		this.mapModel = mapModel;
		
		Iterator<Coordinates> keySetIterator = mapModel.keySet().iterator();

		while(keySetIterator.hasNext()){
			Coordinates actualCoordinates = keySetIterator.next();
			map.put(actualCoordinates, ((SectorName) mapModel.get(actualCoordinates)).getSector());
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


