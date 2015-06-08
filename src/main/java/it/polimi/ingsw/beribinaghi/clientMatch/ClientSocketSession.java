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
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * class that manages all communication with server during the game using socket
 *
 */
public class ClientSocketSession implements GameSessionClientSide {
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	private ObjectInputStream objectInputStream;
	private MatchController controller;

	
	/**
	 * Constructs a ClientSocketSession assigning socket, scanner and printwriter
	 * @param socket
	 * @param in
	 * @param out
	 */
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

	/**
	 * splits a command with the string "&"
	 * @param command is the string command
	 * @return the array of string containing the splitted strings
	 */
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
		int turnNumber;
		String line = in.nextLine();
		String command[] = line.split("=");
		if (command[0].equals("turn"))
		{
			turnNumber = Integer.parseInt(command[2]);
			controller.setTurnNumber(turnNumber);
			return command[1];
		}
		return null;
	}
	
	@Override
	public void signalDiscardedObjectCard(ObjectCard discardedCard){
		String command = new String("discarded="+discardedCard.toString());
		out.println(command);
		out.flush();
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

	/**
	 * create a sector from its abbreviation
	 * @param abbrevation is the short name of the sector
	 * @return an instance of correspondent sector type
	 */
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
			if(command.length == 3)
				if(command[2].equals("adrenalin"))
					pickedCards.add(new Adrenalin());
				else if(command[2].equals("sedatives"))
				pickedCards.add(new Sedatives());
				else if(command[2].equals("defense"))
					pickedCards.add(new Defense());
				else if(command[2].equals("attack"))
					pickedCards.add(new Attack());
				else if(command[2].equals("spotlight"))
					pickedCards.add(new Spotlight());
				else if(command[2].equals("teleport"))
					pickedCards.add(new Teleport());
		return pickedCards;
	}

	@Override
	public void endTurn() {
		out.println("end");
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
		if(command.get(0).equals("attack")){
			String[] attackResult = in.nextLine().split("=");
			analyzeAndShowAttack(attackResult);
		}
		else 
			if (command.get(0).equals("spotlight")){
				String[] spotlightResult = in.nextLine().split("=");
				analyzeAndShowSpotlight(spotlightResult);
			}
				
			
	}


	/**
	 * analyzes the spotlight and invokes the graphic interface methods to show the spotted players 
	 * @param spotlightResult is  an array of strings containing the coordinates of the spotlight and the spotted players
	 */
	private void analyzeAndShowSpotlight(String[] spotlightResult) {
		for(int i=1; i<spotlightResult.length; i++){
			String[] spottedPlayer = spotlightResult[i].split("&");
			String username = spottedPlayer[0];
			Coordinates position = Coordinates.stringToCoordinates(spottedPlayer[1]);
			controller.getGraphicInterface().showSpottedPlayer(username, position);
		}
	}

	/**
	 * analyzes the attack result, and if an alien has killed an human in his attack, the boolean isStrong in the AlienCharacter is setted true
	 * @param attackResult is an array of strings containing the coordinates of the attack, the killed players' username and character, the survived players' username
	 */
	public void analyzeAndShowAttack(String[] attackResult) {

		Coordinates attackCoordinates = Coordinates.stringToCoordinates(attackResult[1]);
		boolean humanKilled = this.showKilledAndAttackPosition(attackResult, attackCoordinates);
		showSurvived(attackResult);
		if(controller.isMyTurn() && humanKilled && controller.getMyCharacter().getSide().equals(SideName.ALIEN)){
			AlienCharacter myCharacter = (AlienCharacter) controller.getMyCharacter();
			myCharacter.setStrong(true);
		}
	}
	
	/**
	 * analyzes the attack result and invokes the graphic interface methods to show username and character of the killed players 
	 * @param command is an array of strings containing the coordinates of the attack, the killed players' username and character, the survived players' username
	 * @param attackCoordinates are the coordinates of the attack
	 * @return true if an human was killed by an alien
	 */
	private boolean showKilledAndAttackPosition(String[] command, Coordinates attackCoordinates) {
		String[] killed;
		boolean humanKilled = false;
		int i = 0;
		while(!command[i].equals("killed"))
			i++;
		i++;
		int start = i;
		while(!command[i].equals("survived"))
			i++;
		int end = i;
		killed = Arrays.copyOfRange(command, start, end);
		controller.getGraphicInterface().showAttackCoordinates(attackCoordinates);
		if(killed.length == 0){
			controller.getGraphicInterface().showKill(null, null);
			return false;
		}	
		for(String singleKilled : killed){
			String[] killedCharacter = singleKilled.split("&");
			controller.getGraphicInterface().showKill(killedCharacter[0], killedCharacter[1]);
			if(killedCharacter[2].equals(SideName.HUMAN.toString()))
				humanKilled = true;

		}
		return humanKilled;		
	}

	/**
	 * analyzes the s result and invokes the graphic interface methods to show username of the survived players 
	 * @param command is an array of strings containing the coordinates of the attack, the killed players' username and character, the survived players' username
	 */
	private void showSurvived (String[] command) {
		String[] survived;
		boolean humanKilled = false;
		int i = 0;
		while(!command[i].equals("survived"))
			i++;
		i++;
		int start = i;
		int end = command.length;
		survived = Arrays.copyOfRange(command, start, end);
		if(survived.length != 0){
			for(String singleSurvived : survived){
				String[] survivedCharacter = singleSurvived.split("&");
				controller.getGraphicInterface().showSurvived(survivedCharacter[0]);
				if(survivedCharacter[0].equals(controller.getMyPlayerName()))
					controller.getMyCharacter().removeCardFromBag(new Defense());
			}
		}	
	}

	@Override	
	public void listenUpdate() {
		String commandString = in.nextLine();
		String[] command = commandString.split("=");
		while(!command[0].equals("end") && !controller.isMatchFinished()){
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
					Coordinates destinationCoord = null;
					if(command[1].equals("spotlight"))
						destinationCoord = Coordinates.stringToCoordinates(command[2]);
					controller.getGraphicInterface().showUsedCard(usedCard, destinationCoord);
					break;
				case "attack":
					analyzeAndShowAttack(command);
					break;
				case "endMatch":
					controller.getGraphicInterface().showMatchResults(command);
					break;
				case "spotlight":
					analyzeAndShowSpotlight(command);
					break;
				case "discarded":
					controller.getGraphicInterface().notifyDiscardedObject();
			}
			if(!controller.isMatchFinished()){
				commandString = in.nextLine();
				command = commandString.split("=");		
			}
		}
		if(!controller.isMatchFinished())
			controller.turn(true);
	}
	

	@Override
	public boolean isMatchFinished() {
		String matchStatus = in.nextLine();

		if(matchStatus.equals("endMatch"))
			 return true;
		return false;
	}

	@Override
	public void listenMatchResult() {
		String matchResult = in.nextLine();
		String[] resultStrings = matchResult.split("=");
		controller.getGraphicInterface().showMatchResults(resultStrings);
	}

	@Override
	public void listenEscapeResult() {
		boolean result;
		
		String[] command = in.nextLine().split("=");
		if(command[1].equals("true"))
			result = true;
		else
			result = false;
		Coordinates shallopCoord;
		shallopCoord = Coordinates.stringToCoordinates(command[2]);
		controller.setAttemptedEscape(true);
		controller.getMap().addUsedShallop(shallopCoord);
		controller.getGraphicInterface().showEscapeResult(result, shallopCoord);
	}



}
