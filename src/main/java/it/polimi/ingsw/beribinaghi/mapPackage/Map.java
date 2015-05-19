package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.gameNames.*;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Map {
	final int HEIGHT = 14;		//TODO aggiungi costanti
	final int WIDTH = 23;
	private String mapName;
	private HashMap <Coordinates, Sector> map = new HashMap<Coordinates, Sector>();
	
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
				Coordinates actualCoordinates = new Coordinates (Coordinates.getLetterFromNumber(j),i+1);
				Sector actualSector = graphicMap[i][j].getSector();
				actualSector.acceptDeck(deckAssigner);
				map.put(actualCoordinates, actualSector);				
			}
		}

	public ArrayList<Coordinates> adiacentCoordinates (Coordinates centralCoordinates){
		int otherNumber;
		char currentLetter;
		int currentNumber;
		Coordinates actualCoordinates;
		ArrayList<Coordinates> adiacentCoordinates = new ArrayList<Coordinates>();
		currentLetter = centralCoordinates.getLetter();
		currentNumber = centralCoordinates.getNumber();
		if(((int) currentLetter) % 2 != 0)		//a,c,e...
			otherNumber = currentNumber - 1;
		else
			otherNumber = currentNumber + 1;	//b,d,f...
		for(int i = -1; i < 2; i++){
			actualCoordinates = new Coordinates((char) ((int)currentLetter + i), currentNumber);	
			if(actualCoordinates.isValid())
				adiacentCoordinates.add(actualCoordinates);
			actualCoordinates = new Coordinates((char) ((int)currentLetter + i), otherNumber);
			if(actualCoordinates.isValid())
				adiacentCoordinates.add(actualCoordinates);
		}
		otherNumber = currentNumber + (currentNumber - otherNumber);
		actualCoordinates = new Coordinates(currentLetter, otherNumber);
		adiacentCoordinates.add(actualCoordinates);
			return adiacentCoordinates;
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
			if(map.get(nextCoordinates).getClass().equals(wantedSectorType.getClass()));
				return nextCoordinates;
			}
		return null;
		}
	
	/**
	 * Returns the coordinates reachable with a passed number of steps from a passed Coordinate. Note: this method returns the coordinates reachable with a movement, not the sectors that have a determinate distance from the passed one
	 * @param initialCoordinates are the coordinates of the sector in which the movement start
	 * @param distance is the maximum reachable distance allowed in the movement. It depends from the player side, and from the object card he is using
	 * @return the ArrayList of the reachable coordinates
	 */
	public ArrayList<Coordinates> getReachableCoordinates(Coordinates initialCoordinates, int distance){
		ArrayList<Coordinates> reachableCoordinates = new ArrayList<Coordinates>();
		ArrayList<Coordinates> adiacentToAnalyzed = new ArrayList<Coordinates>();
		
		reachableCoordinates.add(initialCoordinates);
		for(int i=0; i<distance; i++){
			
			ArrayList<Coordinates> tmpReachable = reachableCoordinates;
			
			for(Coordinates analyzedCoordinates: tmpReachable){
				Sector analyzedSector = this.getSector(analyzedCoordinates);
				if(analyzedSector.getClass().equals((new HumanBase()).getClass()) || analyzedSector.getClass().equals((new AlienBase()).getClass()) || analyzedSector.getClass().equals((new BlankSector()).getClass()))
					tmpReachable.remove(analyzedCoordinates);		//rimuove il settore se è blank, alienbase o humanbase
				else{
					adiacentToAnalyzed = adiacentCoordinates(analyzedCoordinates);
					for(Coordinates adiacentCoordinates: adiacentToAnalyzed){
						if(!reachableCoordinates.contains(adiacentCoordinates))
							reachableCoordinates.add(adiacentCoordinates);
					}
				}
			}
		}
		reachableCoordinates.remove(initialCoordinates);
		return reachableCoordinates;
	}

	public Sector getSector (Coordinates coordinates){
		Sector selectedSector = map.get(coordinates);
		return selectedSector;
	}

	public String getMapName() {
		return mapName;
	}


	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	
	

	
}


