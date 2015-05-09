package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

/**
 * controller of the matches
 *
 */
public class ControllerMatch {
	private ArrayList<PreMatch> matches;
	
	public ControllerMatch(){
		matches = new ArrayList<PreMatch>();
	}
	
	/**
	 * @param player
	 * Add a player to a match
	 */
	public void addPlayer(Player player){
		
	}
	
	/**
	 * @return all match name inactive
	 */
	public ArrayList<String> getMatchesName()
	{
		ArrayList<String> listName = new ArrayList<String>();
		for (PreMatch pre : matches)
			if (!pre.isActive())
				listName.add(pre.getMatchName());
		return listName;
	}

}
