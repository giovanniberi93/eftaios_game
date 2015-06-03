package ProvaMatch;

import it.polimi.ingsw.beribinaghi.clientMatch.ClientSocketSession;


public class ProvaCoordinate {


	public static void main(String[] args){
		ClientSocketSession s = new ClientSocketSession(null, null, null);
		String[] attackResult = "attack=c5=killed=survived".split("=");
		s.analyzeAndShowAttack(attackResult);
	}
	
}
