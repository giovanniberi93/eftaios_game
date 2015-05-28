/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Attack;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInAnySector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInYourSector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Silence;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Spotlight;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;
import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.StringSyntaxNotOfCoordinatesException;
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class that manages all communication with server during the game
 *
 */
public class ClientSocketSession implements GameSessionClientSide {
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	private ObjectInputStream objectInputStream;
	private MatchController controller;

	
	public ClientSocketSession(Socket socket, Scanner in, PrintWriter out) {
			this.socket = socket;
			this.in = in;
		    this.out = out;
	}

	@Override
	public void setController(MatchController matchController) {
		this.controller = matchController;
		
	}

	@Override
	public Character getCharacter() {
		Character character = null;
		CharacterName myName = null;
		if (in.nextLine().equals("sending character"))
		{
			ArrayList<String> list = manageCommand(in.nextLine());
			for (CharacterName characterName:CharacterName.values())
				if (characterName.getPersonalName().equals(list.get(1)))
				{
					myName = characterName;
					break;
				}
			if (myName.getSide().equals(SideName.HUMAN))
					character = new HumanCharacter(myName);
			else
					character = new AlienCharacter(myName);
			character.setCurrentPosition(new Coordinates(list.get(3).charAt(0),Integer.parseInt(list.get(5))));
		}
		return character;
	}

	private static ArrayList<String> manageCommand(String command) {
		ArrayList<String> commands = new ArrayList<String>();
		String []different = command.split("&");
		for (String single:different)
		{
			String []singleWord = single.split("=");
			commands.add(singleWord[0]);
			commands.add(singleWord[1]);
		}
		return commands;	
	}

	@Override
	public String listenTurn() {
		String line = in.nextLine();
		String command[] = line.split("=");
		if (command[0].equals("turn"))
		{
			return command[1];
		}
		return null;
	}

	@Override
	public Map getMap() {
		SectorName[][] graphicMap;
		String mapName = (manageCommand(in.nextLine())).get(1);
		int height = Integer.parseInt((manageCommand(in.nextLine())).get(1));
		int width = Integer.parseInt((manageCommand(in.nextLine())).get(1));
		graphicMap = new SectorName[height][width];
		for (int i=0;i<height;i++)
		{
			String[] singleCell = in.nextLine().split(",");
			for (int j=0;j<width;j++){
				graphicMap[i][j] = abbrevationToSector(singleCell[j]);
			}
		}	
		return new Map(mapName,graphicMap);
	}

	private static SectorName abbrevationToSector(String abbrevation) {
		SectorName correctSector = null;
		for (SectorName sectorName: SectorName.values())
			if (sectorName.getAbbrevation().equals(abbrevation))
			{
				correctSector = sectorName;
				break;
			}
		return correctSector;
	}

	@Override
	public ArrayList<Card> move(Coordinates destinationCoord) throws WrongSyntaxException {
		String pickedCardString = new String();
		ArrayList<Card> pickedCards = new ArrayList<Card>();
		out.println("move="+destinationCoord.toString());
		out.flush();
		pickedCardString = in.nextLine();
		String[] command = pickedCardString.split("=");
			if(!command[0].equals("card"))
				throw new WrongSyntaxException();
			if(command[1].equals("anySector"))
				pickedCards.add(new NoiseInAnySector(false));
			else if (command[1].equals("yourSector"))
				pickedCards.add(new NoiseInYourSector(false));
			else if (command[1].equals("silence"))
				pickedCards.add(new Silence(false));
			else if (command[1].equals("nothing"))
				pickedCards.add(new NothingToPick());
			if(command.length > 2)
				if(command[1].equals("adrenalin"))
					pickedCards.add(new Adrenalin());
				else if(command[1].equals("sedatives"))
				pickedCards.add(new Sedatives());
				else if(command[1].equals("defense"))
					pickedCards.add(new Defense());
				else if(command[1].equals("attack"))
					pickedCards.add(new Attack());
				else if(command[1].equals("spotlight"))
					pickedCards.add(new Spotlight());
				else if(command[1].equals("teleport"))
					pickedCards.add(new Teleport());
		return pickedCards;
	}

	@Override
	public void endTurn() {
		out.println("endTurn");
		out.flush();		
	}

	@Override
	public void noise(Coordinates noiseCoordinates) {
		String string;
		if(noiseCoordinates == null)
			string = "nothing";
		else
			string = noiseCoordinates.toString();
		out.println("noise="+string);
		out.flush();
	}

	@Override
	public void useObjectcard(ArrayList<String> command) {
		String commandString = new String();
		for(String string : command)
			commandString += string+"=";
		out.println(commandString);
		out.flush();
	}

	@Override	
	public void listenUpdate() {
		String commandString = in.nextLine();
		String[] command = commandString.split("=");
		switch(command[0]){
		case "noise":
			Coordinates noiseCoord = null;
			noiseCoord = Coordinates.stringToCoordinates(command[1]);
			controller.getGraphicInterface().showNoise(noiseCoord);
			break;
		case "escaped":
			boolean result;
			if(command[1].equals("true"))
				result = true;
			else
				result = false;
			Coordinates coord = null;
			coord = Coordinates.stringToCoordinates(command[2]);
			controller.getGraphicInterface().showEscapeResult(result, coord);
			break;
		case "card":
			ObjectCard usedCard = ObjectCard.stringToCard(command[1]);
			Coordinates destinationCoord;
			destinationCoord = Coordinates.stringToCoordinates(command[2]);
			controller.getGraphicInterface().showUsedCard(usedCard);
			break;
		case "killed":
			ArrayList<String> killed = new ArrayList<String>();
			ArrayList<String> survived = new ArrayList<String>();
			killed = this.selectKilled(command);
			survived = this.selectSurvived(command);
			controller.getGraphicInterface().showAttackResult(killed, survived);
			break;
		case "turn":
			listenTurn();
			break;
		case "endMatch":
			// TODO boh.
			break;
		case "spotted":
			for(int i=1; i<command.length; i++){
				String[] spottedPlayer = command[i].split("&");
				String username = spottedPlayer[0];
				Coordinates position = Coordinates.stringToCoordinates(spottedPlayer[1]);
				controller.getGraphicInterface().showSpottedPlayer(username, position);
			}
				
			
		}
		
		
	}

	private ArrayList<String> selectSurvived(String[] command) {
		ArrayList<String> survived = new ArrayList<String>();
		int i = 1;
		String string = command[i];
		while(!string.equals("survived"))
			i++;
		i++;
		while (i < command.length){
			survived.add(command[i]);
			i++;
		}
		return survived;
	}

	private ArrayList<String> selectKilled(String[] command) {
		ArrayList<String> killed = new ArrayList<String>();
		int i = 1;
		while(command[i].equals("survived")){
			killed.add(command[i]);
			i++;
		}
		return killed;
			
	}



}
