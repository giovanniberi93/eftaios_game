/**
 * 
 */
package clientMatch;

import it.polimi.ingsw.beribinaghi.gameNames.SideName;

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

}
