/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.gameNames.SectorName;

/**
 * @
 *	This class manage all information of the Map
 */
public class MapOld {
	final int HEIGHT = 14;		//TODO aggiungi costanti
	final int WIDTH = 23;
	private String mapName;
	private Sector[][] sectors;
	/**
	 * @param mapName
	 * 	Generate a new Map with name mapName and with graphics grMap.
	 *  The grMap must be of the right dimension.
	 */
	public MapOld(String mapName, SectorName [][]grMap) throws SizeErrorException{
		this.mapName = mapName;
		this.sectors = new Sector[WIDTH][HEIGHT];
		if ((grMap.length!=WIDTH) || (grMap[0].length!=HEIGHT))
			throw new SizeErrorException();
		generate(grMap);
	}
	
	/**
	 * generate a map from a a matrix of sectorNames
	 * @param grMap a matrix of sectorNames, such as SAFESECTOR, DANGEROUSSECTOR, HUMANBASE...
	 */
	private void generate(SectorName [][]grMap) {
		for (int i=0;i<grMap.length;i++)
			for (int j=0;j<grMap[i].length;j++)
			{
				sectors[i][j] = grMap[i][j].getSector();
				sectors[i][j].setCoordinates(new Coordinates(Coordinates.getLetter(i),j));
			}
	}
	
	/**
	 * return the coordinates of the first found instance of the searched sector type 
	 * @param sectorName is the sector type searched
	 * @return the coordinates of the desired sector
	 */
	public Coordinates searchSectorType (SectorName wantedSectorName){

		Sector wantedSectorType = wantedSectorName.getSector();	//sector is instance of the sector class indicated in wantedSectorName 
		
		for(Sector[] sectorArray: sectors){
			for(Sector sector: sectorArray){
				if(sector.getClass() == wantedSectorType.getClass())
					return sector.getCoordinates();
			}
		}
		return null;
	}

	/**
	 * @return The map string name
	 */
	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

}
