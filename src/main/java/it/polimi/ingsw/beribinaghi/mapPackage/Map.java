package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;
import it.polimi.ingsw.beribinaghi.matchPackage.WatcherDeckAssigner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Map implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static int HEIGHT = 14;		//TODO aggiungi costanti
	public final static int WIDTH = 23;
	private String mapName;
	private HashMap <String, Sector> map = new HashMap<String, Sector>();
	private Coordinates AlienBaseCoordinates;
	private Coordinates HumanBaseCoordinates;
	private SectorName[][] graphicMap;
	private ArrayList<Coordinates> usedShallops = new ArrayList<Coordinates>();
	private ArrayList<Coordinates> shallopsCoordinates = new ArrayList<Coordinates>();

	
	/**
	 * Generate a new Map with name mapName and graphics map, and sets the alienBaseCoordinates and the HumanBaseCoordinates;
	 * it also assign the right deck to every sector
	 * @param mapName is a map model, where all sectors types are defined	
	 */
	public Map (String mapName, SectorName[][] graphicMap, DangerousSectorsDeck dangerousDeck, ShallopsDeck shallopsDeck){
		
		this.setMapName(mapName);
		DeckAssigner deckAssigner = new WatcherDeckAssigner(dangerousDeck, shallopsDeck);
		this.graphicMap = graphicMap;
	
		for (int i=0;i<graphicMap.length;i++)
			for (int j=0;j<graphicMap[i].length;j++){
				
				Coordinates actualCoordinates = new Coordinates (Coordinates.getLetterFromNumber(j),i+1);
				Sector actualSector = (Sector) (graphicMap[i][j].getSector()).clone();
				actualSector.acceptDeck(deckAssigner);
				map.put(actualCoordinates.toString(), actualSector);
				if(actualSector instanceof AlienBase)
					setAlienBaseCoordinates(actualCoordinates);
				else if(actualSector instanceof HumanBase)
					setHumanBaseCoordinates(actualCoordinates);
				else if(actualSector instanceof ShallopSector)
					shallopsCoordinates.add(actualCoordinates);
			}
		}

	/**
	 * Generate a new Map with name mapName and graphics map, and sets the alienBaseCoordinates and the HumanBaseCoordinates;
	 * @param mapName is a map model, where all sectors types are defined
	 * @param graphicMap
	 */
	public Map(String mapName, SectorName[][] graphicMap) {
		this.setMapName(mapName);
		this.graphicMap = graphicMap;
		for (int i=0;i<graphicMap.length;i++)
			for (int j=0;j<graphicMap[i].length;j++){
				Coordinates actualCoordinates = new Coordinates (Coordinates.getLetterFromNumber(j),i+1);
				Sector actualSector = (Sector) (graphicMap[i][j].getSector()).clone();
				map.put(actualCoordinates.toString(), actualSector);
				if(actualSector instanceof AlienBase)
					setAlienBaseCoordinates(actualCoordinates);
				else if(actualSector instanceof HumanBase)
					setHumanBaseCoordinates(actualCoordinates);
				else if(actualSector instanceof ShallopSector)
					shallopsCoordinates.add(actualCoordinates);
			}
	}

	/**
	 * 
	 * @param centralCoordinates
	 * @return the valid coordinates at distance 1 from the central coordinates
	 */
	public ArrayList<Coordinates> adiacentCoordinates (Coordinates centralCoordinates){
		int otherNumber;
		char currentLetter;							//non tiene conto dell'effettiva dimensione della mappa
		int currentNumber;							//però con una mappa come galilei funziona lo stesso
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
			if(areCoordinatesValid(actualCoordinates))
				adiacentCoordinates.add(actualCoordinates);
			actualCoordinates = new Coordinates((char) ((int)currentLetter + i), otherNumber);
			if(actualCoordinates.isValid())
				adiacentCoordinates.add(actualCoordinates);
		}
		otherNumber = currentNumber + (currentNumber - otherNumber);
		actualCoordinates = new Coordinates(currentLetter, otherNumber);
		if(areCoordinatesValid(actualCoordinates))
			adiacentCoordinates.add(actualCoordinates);
		return adiacentCoordinates;
	}

	
	/**
	 * Returns the coordinates reachable with a passed number of steps from a passed Coordinate. 
	 * Note: this method returns the coordinates reachable with a movement, not the sectors that have a determinate distance from the passed one
	 * @param initialCoordinates are the coordinates of the sector in which the movement start
	 * @param distance is the maximum reachable distance allowed in the movement. It depends from the player side, and from the object card he is using
	 * @return the ArrayList of the reachable coordinates
	 */

	public ArrayList<Coordinates> getReachableCoordinates(Coordinates initialCoordinates, int distance, boolean isAlien){
		ArrayList<Coordinates> reachableCoordinates = new ArrayList<Coordinates>();
		ArrayList<Coordinates> adiacentToTmp = new ArrayList<Coordinates>();
		ArrayList<Coordinates> tmpReachable = new ArrayList<Coordinates>();
		boolean found;

		reachableCoordinates.add(initialCoordinates);
		tmpReachable.add(initialCoordinates);
		for(int i=0; i<distance; i++){				//itera per la distanza raggiungbile
			for(Coordinates analyzedCoordinates: tmpReachable)
				adiacentToTmp.addAll(this.adiacentCoordinates(analyzedCoordinates));		//agggiungo tutte le raggiunte all'adiacent di tmp
			tmpReachable.clear();
			for(Coordinates analyzedCoordinates : adiacentToTmp){
				Sector analyzedSector = this.getSector(analyzedCoordinates);
				if(!(analyzedSector instanceof HumanBase || analyzedSector instanceof AlienBase || analyzedSector instanceof BlankSector)){
					found = false;
					for(Coordinates coord : reachableCoordinates){
						if (coord.equals(analyzedCoordinates))
						found = true;
					}	
					if(!found){
						reachableCoordinates.add(analyzedCoordinates);
						tmpReachable.add(analyzedCoordinates);
						}
					}
			}
			adiacentToTmp.clear();
		}
		reachableCoordinates.remove(initialCoordinates);
		ArrayList<Coordinates> unreachableShallops;
		if(isAlien)
			unreachableShallops = shallopsCoordinates;
		else
			unreachableShallops = usedShallops;
		for(Coordinates shallopCoord : unreachableShallops)
			reachableCoordinates.remove(shallopCoord);
		return reachableCoordinates;
	}

	/**
	 * determinates if a coordinate is valid in that map
	 * @param coord are the cooordianates to evaluate
	 * @return true if the coordinates are valid
	 */
	public boolean areCoordinatesValid(Coordinates coord){
		if(coord.isValid() && !usedShallops.contains(coord))
			return true;
		return false;
			/*int number = coord.getNumber();
			int letterNumber = Coordinates.getNumberFromLetter(coord.getLetter());*/
			//TODO aggiungi controlli sulla dimensione della mappa; al momento funziona tutto perché la mappa galilei è della dimensione massima
	}
	
	
	/**
	 * adds an used shallop coordinates to the list of used shallops coordinates
	 * @param usedShallop
	 */
	public void addUsedShallop(Coordinates usedShallop){
		usedShallops.add(usedShallop);
	}
	
	
	/**
	 * @param coordinates are the coordinates we want to analyze
	 * @return the sector found at the passed coordinates
	 */
	public Sector getSector (Coordinates coordinates){
		Sector selectedSector = map.get(coordinates.toString());
		return selectedSector;
	}

	public String getMapName() {
		return mapName;
	}


	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Coordinates getAlienBaseCoordinates() {
		return AlienBaseCoordinates;
	}

	private void setAlienBaseCoordinates(Coordinates alienBaseCoordinates) {
		AlienBaseCoordinates = alienBaseCoordinates;
	}

	public Coordinates getHumanBaseCoordinates() {
		return HumanBaseCoordinates;
	}

	private void setHumanBaseCoordinates(Coordinates humanBaseCoordinates) {
		HumanBaseCoordinates = humanBaseCoordinates;
	}

	/**
	 * @return the graphicMap
	 */
	public SectorName[][] getGraphicMap() {
		return graphicMap;
	}
}


