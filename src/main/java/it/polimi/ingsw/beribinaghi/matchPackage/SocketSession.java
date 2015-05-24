package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketSession extends GameSessionServerSide{
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	public SocketSession(Socket socket,Scanner in, PrintWriter out, Match match, Player player) throws IOException {
		this.in = in;
	    this.out = out;
	    this.player = player;
	    this.socket = socket;
	    this.match = match;
	}

	
	public synchronized void notifyCharacter() {
		out.println("sending character");
		out.flush();
		out.println("name="+player.getCharacter().getName()+"&poslet="+player.getCharacter().getCurrentPosition().getLetter()+"&posnum="+player.getCharacter().getCurrentPosition().getNumber());
		out.flush();
	}


	@Override
	protected void notifyBeginTurn(String turn) {
		out.println(turn);
		out.flush();
	}

	@Override
	public void sendMap(Map map) {
		String line;
		SectorName[][] graphicMap = map.getGraphicMap();
		out.println("mapName=" + map.getMapName());
		out.println("heigth=" + graphicMap.length);
		out.println("width=" + graphicMap[0].length);
		for (int i=0;i<graphicMap.length;i++)
		{
			line = "";
			for (int j=0;j<graphicMap[i].length;j++){
				line = line.concat(graphicMap[i][j].getAbbrevation() + ",");
			}
			line = line.substring(0, line.length()-1);
			out.println(line);
		}	
		out.flush();
	}


	@Override
	protected void notifyEndMatch() {
		out.println("endMatch");
		out.flush();
	}


	@Override
	protected void notifyCard(String usedCard) {
		out.println(usedCard);
		out.flush();
	}


	@Override
	protected void notifySpotted(String spottedPlayers) {
		out.println(spottedPlayers);
		out.flush();
	}


	@Override
	protected void notifyNoise(String noisePosition) {
		out.println(noisePosition);
		out.flush();
	}


	@Override
	protected void notifyEscape(String escapeResult) {
		out.println(escapeResult);
		out.flush();
	}


	@Override
	protected void notifyAttackResult(String attackResult) {
		out.println(attackResult);
		out.flush();
	}
}
