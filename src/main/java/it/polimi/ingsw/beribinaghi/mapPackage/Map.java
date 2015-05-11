/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

/**
 * @
 *	This class manage all information of the Map
 */
public class Map {
	final int HEIGHT = 14;
	final int WIDTH = 23;
	private String mapName;
	private Sector[][] sectors;
	/**
	 * @param mapName
	 * 	Generate a new Map with name mapName and with graphics grMap.
	 *  The grMap must be of the right dimension.
	 */
	public Map(String mapName, SectorType [][]grMap) throws SizeErrorException{
		this.mapName = mapName;
		this.sectors = new Sector[WIDTH][HEIGHT];
		if ((grMap.length!=WIDTH) || (grMap[0].length!=HEIGHT))
			throw new SizeErrorException();
		generate(grMap);
	}
	
	private void generate(SectorType [][]grMap) {
		for (int i=0;i<grMap.length;i++)
			for (int j=0;j<grMap[i].length;j++)
			{
				sectors[i][j] = grMap[i][j].getSector();
				sectors[i][j].setCoordinates(new Coordinates(Coordinates.getLetter(i),j));
			}
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
