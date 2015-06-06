package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class manage all communication with client during the game using Socket
 *
 */
public class ServerSocketSession extends GameSessionServerSide implements Runnable{
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private Match match;
	
	public ServerSocketSession(Socket socket,Scanner in, PrintWriter out, Player player) throws IOException {
		this.in = in;
	    this.out = out;
	    this.player = player;
	    this.socket = socket;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	public synchronized void notifyCharacter() {
		out.println("sending character");
		out.flush();
		out.println("name="+player.getCharacter().getName()+"&poslet="+player.getCharacter().getCurrentPosition().getLetter()+"&posnum="+player.getCharacter().getCurrentPosition().getNumber());
		out.flush();
	}

 
	@Override
	protected void notifyBeginTurn() {
		Player currentPlayer = match.getMatchDataUpdate().getCurrentPlayer();
		Player oldCurrentPlayer = match.getMatchDataUpdate().getOldCurrentPlayer();
		if(!(oldCurrentPlayer == null) && !(this.player.getUser().equals(oldCurrentPlayer.getUser()))){
			out.println("end");
			out.flush();
		}
		String string = "turn="+currentPlayer.getUser()+"="+match.getTurnNumber();
		out.println(string);
		out.flush();
		if(currentPlayer.getUser().equals(this.player.getUser())){
			new Thread(this).start(); 
		}

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

		while(!command[0].equals("end")){
			if(command[0].equals("move"))
				executeMove(command[1]);	
			else if (command[0].equals("discarded"))
					match.discard(command[1]);
				else 
					executeCardAction(command);
			line = in.nextLine();
			command = line.split("=");
		}
		match.getMatchDataUpdate().setOldCurrentPlayer(this.player);
		//provo
		boolean finished = match.isFinished();
		if(finished){
			out.println("endMatch");
			out.flush();
		}
		else{
			out.println("continue");
			out.flush();
		}
			//
		match.finishTurn();  
	}
	


	private void executeCardAction(String[] command) {
		switch(command[0]){
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
			Coordinates selectedCoordinates;
			selectedCoordinates = Coordinates.stringToCoordinates(command[1]);
			match.spotlight(selectedCoordinates);
			break;
		}
	}


	@Override
	protected void notifyDiscardedObject() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
			out.println("discarded");
			out.flush();
			}
	}

	private void executeMove(String coordinatesString){
		ArrayList<Card> pickedCards = new ArrayList<Card>();
		String pickedCardString = new String("card=");
		Coordinates destinationCoordinates;
		destinationCoordinates = Coordinates.stringToCoordinates(coordinatesString);
		
		pickedCards = match.move(destinationCoordinates);
		for(Card card : pickedCards)
			pickedCardString += card.toString() + "=";
		out.println(pickedCardString);
		out.flush();
		String[] noise = in.nextLine().split("=");		//aspetto il rumore comunicato dall'altra parte!
		if(!noise[1].equals("nothing")){
			match.setNoiseCoordinates(Coordinates.stringToCoordinates(noise[1]));
			match.getMatchDataUpdate().setNoiseCoordinates();
		}
		if(match.isSuccessfulEscape() != null){
			match.getMatchDataUpdate().setEscaped();
			match.setSuccessfulEscape(null);
			}
	}

	@Override
	protected void notifyEndMatch() {
		String winners = new String();
		ArrayList<Player> winnersList = match.getWinners();
		for(Player player : winnersList)
			winners += "Il giocatore "+ player.getUser() + ", nel ruolo di " + player.getCharacter().toString()+"=";
		out.println("endMatch="+winners);
		out.flush();
	}


	@Override
	protected void notifyCard() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
			String usedCard = match.getLastUsedCard().toString();
			out.println("card="+usedCard);
			out.flush();
			if(usedCard.equals("spotlight")){
				out.println(match.getSpotlightCoordinates());
				out.flush();
			}
		}
	}


	@Override 
	protected void notifySpotted() {
		ArrayList<Player> spotted = match.getSpotted();
		String result = "spotlight=";
		for(Player player : spotted){
			Coordinates playerCoordinates = player.getCharacter().getCurrentPosition();
			result += player.getUser() + "&" + playerCoordinates + "=";
		}
		out.println(result);
		out.flush();
	}


	@Override
	protected void notifyNoise() {
		if(!match.getMatchDataUpdate().getCurrentPlayer().equals(this.player)){
			Coordinates noiseCoordinates = match.getNoiseCoordinates();
			String noise = new String("noise="+noiseCoordinates.toString());
			out.println(noise);
			out.flush();
		}
		
	}


	@Override
	protected void notifyEscape() {
			Coordinates shallopPosition = match.getUsedShallopCoordinates();
			String escapeResult = new String("escaped="+match.isSuccessfulEscape()+"="+shallopPosition);
			out.println(escapeResult);
			out.flush();
	}


	@Override
	protected void notifyAttackResult() {
		String attackResult;
		ArrayList<Player> killed = match.getKilled();
		Coordinates attackPosition = match.getMatchDataUpdate().getCurrentPlayer().getCharacter().getCurrentPosition();
		attackResult = "attack=" + attackPosition+ "=";
		attackResult += "killed=";		
		for(Player player : killed)
			attackResult += "Il giocatore "+player.getUser() + ", nel ruolo di " + player.getCharacter() + "="; 
		ArrayList<Player> survived = match.getSurvived();
		attackResult += "survived=";
		for(Player player : survived)
			attackResult += player.getUser() + ", nel ruolo di " + player.getCharacter() + "="; 
		out.println(attackResult);
		out.flush();
	}

	@Override
	public void run() {
		this.myTurn();
	}
}
