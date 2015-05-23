/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;

/**
 * manages all communications during the game with user using command line
 */
public class GameCLI implements GameInterface {

	private matchController controller;

	@Override
	public void setController(matchController matchController) {
		this.controller = matchController;
		
	}

	@Override
	public void printCharacter(String name, String role, SideName side) {
		String sideName;
		if (side == SideName.ALIEN)
			sideName = "Alieno";
		else
			sideName = "Umano";
		System.out.println("Ti chiami " + name + ", sei " + role + " e sei un " + sideName);
	}

	@Override
	public void managesTurn() {
		System.out.println("E' il tuo turno!");
	}

	@Override
	public void notifyOthersTurn(String playerTurn) {
		System.out.println("E' il turno di " + playerTurn);
	}

	@Override
	public void printMap(Map map,Coordinates myCoordinates) {
		int j;
		System.out.println("La tua posizione è indicata con mappa è:");
		SectorName[][] graphicMap = map.getGraphicMap();
		for (int i=0;i<graphicMap.length;i++)
		{
			for (j=0;j<graphicMap[i].length-1;j++){
				if (myCoordinates.getLetter() == Coordinates.getLetterFromNumber(j) && myCoordinates.getNumber() == i+1)
					System.out.print("MY,");
				else
					System.out.print(graphicMap[i][j].getAbbrevation() + ",");
			}
			System.out.println(graphicMap[i][j].getAbbrevation());
		}
	}
}
