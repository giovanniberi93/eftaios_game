package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.StringSyntaxNotOfCoordinatesException;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerSocketSession extends GameSessionServerSide{
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Match match;
	
	public ServerSocketSession(Socket socket,Scanner in, PrintWriter out, Match match, Player player) throws IOException {
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
		String[] command = turn.split("=");
		out.println(turn);
		out.flush();
		if(command[1].equals(this.player.getUser()))
			this.myTurn();
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

	protected void myTurn(){
		String line = in.nextLine();
		String[] command = line.split("=");

		while(!command[0].equals("endTurn")){
			if(command[0].equals("move")){
				executeMove(command[1]);	
			}
			else if (command[0].equals("card")){
				executeCardAction(command);
			}
		}
		match.finishTurn();  
	}
	
	private void executeCardAction(String[] command) {
		switch(command[1]){
		case "teleport" : 
			match.teleport();
			break;
		case "attack" :
			match.attack();
			break;
		case "sedatives" :
			match.sedatives();
			break;
		case "adrenalin" :
			match.adrenalin();
			break;
		case "spotlight" :
			Coordinates selectedCoordinates = null;
			try {
				selectedCoordinates = Coordinates.stringToCoordinates(command[2]);
			} catch (StringSyntaxNotOfCoordinatesException e) {
				System.out.println("Stringa non convertibile in coordinate");
			}
			match.spotlight(selectedCoordinates);
			break;
		}
	}


	private void executeMove(String coordinatesString){
		ArrayList<Card> pickedCards = new ArrayList<Card>();
		String pickedCardString = new String("card=");
		Coordinates destinationCoordinates = null;
		try {
			destinationCoordinates = Coordinates.stringToCoordinates(coordinatesString);
		} catch (StringSyntaxNotOfCoordinatesException e1) {
			System.out.println("Stringa non convertibile in coordinate");
		}
		
		pickedCards = match.move(destinationCoordinates);
		for(Card card : pickedCards)
			pickedCardString += card.toString() + "=";
		out.println(pickedCardString);
		out.flush();
		String[] noise = in.nextLine().split("=");
		Coordinates noiseCoordinates = null;
		try {
			noiseCoordinates = Coordinates.stringToCoordinates(noise[1]);
		} catch (StringSyntaxNotOfCoordinatesException e) {
			System.out.println("Stringa non convertibile in coordinate");
		}
		match.noise(noiseCoordinates);
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
