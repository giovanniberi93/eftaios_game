/**
 * 
 */
package it.polimi.ingsw.beribinaghi.playerPackage;

/**
 * Class that manage a physical player
 *
 */
public class Player {
	private String userName;
	
	public Player(String user){
		userName = user;
	}

	/**
	 * @return the user name
	 */
	public String getUser() {
		return userName;
	}

}
