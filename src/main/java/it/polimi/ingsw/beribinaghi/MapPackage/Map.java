/**
 * 
 */
package it.polimi.ingsw.beribinaghi.MapPackage;

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
	 * 	Generate a new Map with name mapName
	 */
	public Map(String mapName){
		this.mapName = mapName;
		this.sectors = new Sector[WIDTH][HEIGHT];
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
