package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.*;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MatchTest {
	Match match;
	ArrayList<Player> players = new ArrayList<Player>();
	SectorName[][] graphicMap = {	
									{SectorName.ALIENBASE,	SectorName.HUMANBASE},
									{SectorName.BLANK, 		SectorName.DANGEROUS},
									{SectorName.SAFE,		SectorName.SHALLOP}
								};

	
	@Test
	public void testMatchNotNull(){
		Player mario = new Player("Mario");
		Player sassate = new Player("Sassate");
		Player spugna = new Player("Spugna");
		
		players.add(mario);
		players.add(sassate);
		players.add(spugna);
		
		match = new Match(players, "mappaDiProva", graphicMap);
		assertNotNull(match);
	}	
}









