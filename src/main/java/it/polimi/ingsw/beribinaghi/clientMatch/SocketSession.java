/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.AlienCharacter;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;
import it.polimi.ingsw.beribinaghi.playerPackage.HumanCharacter;
import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class that manages all comunication with server during the game
 *
 */
public class SocketSession implements GameSessionClientSide {
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	private matchController controller;
	
	public SocketSession(Socket socket, Scanner in, PrintWriter out) {
			this.socket = socket;
			this.in = in;
		    this.out = out;
	}

	@Override
	public void setController(matchController matchController) {
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
			return line.substring(command[0].length()+1, line.length());
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

}
