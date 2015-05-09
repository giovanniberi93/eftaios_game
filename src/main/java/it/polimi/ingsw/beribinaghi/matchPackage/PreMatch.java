/**
 * 
 */
package it.polimi.ingsw.beribinaghi.matchPackage;

import java.util.ArrayList;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

/**
 * @author damianobinaghi
 * This class manages a pre match and a close match
 */
public class PreMatch {
	private String matchName;
	private Boolean active;
	private ArrayList<Player> players;
	private Player administrator;

	public Boolean isActive() {
		return active;
	}

	public String getMatchName() {
		return matchName;
	}
	
	/**
	 * @param name
	 * @param administrator
	 * Create a preMatch with name and a player administrator
	 */
	public PreMatch(String name,Player administrator){
		matchName = name;
		players = new ArrayList<Player>();
		this.administrator = administrator;
		active = false;
	}

}
