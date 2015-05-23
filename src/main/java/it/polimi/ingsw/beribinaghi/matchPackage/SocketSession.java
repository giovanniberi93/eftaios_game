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
	private Match match;
	
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
	}


	@Override
	protected void notifyBeginTurn(String string) {
		out.println("turn=" + string);
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
}
