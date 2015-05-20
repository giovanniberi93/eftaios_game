package ProvaMatch;

import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;


public class ProvaCreazioneMatch {

	Match match;
	ArrayList<Player> players = new ArrayList<Player>();
	SectorName[][] graphicMap = {	
									{SectorName.ALIENBASE,	SectorName.HUMANBASE},
									{SectorName.BLANK, 		SectorName.DANGEROUS},
									{SectorName.SAFE,		SectorName.SHALLOP}
								};
	
	public void prova() {
		
		Player mario = new Player("Mario");
		Player sassate = new Player("Sassate");
		Player spugna = new Player("Spugna");
		
		players.add(mario);
		players.add(sassate);
		players.add(spugna);
		
		match = new Match(null,players, "mappaDiProva","galilei", graphicMap);
	}
	
	public static void main(String[] args) {
		ProvaCreazioneMatch prova = new ProvaCreazioneMatch();
		prova.prova();
		System.out.println("Arrivato in fondo alla creazione");
		}
	
}
	