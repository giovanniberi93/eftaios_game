/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.StringSyntaxNotOfCoordinatesException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * manages all communications during the game with user using command line
 */
public class GameCLI implements GameInterface {

	private MatchController controller;
	private Scanner in = new Scanner(System.in);

	@Override
	public void setController(MatchController matchController) {
		this.controller = matchController;
		
	}

	@Override
	public void printCharacter(String name, String role, SideName side) {
		String sideName;
		if (side == SideName.ALIEN)
			sideName = "Alieno";
		else
			sideName = "Umano";
		System.out.println("Ti chiami " + name + ", sei " + role + " e sei un " + sideName);
	}

	@Override
	public void managesMyTurn() {
		String choose;
		boolean hasMoved = false;
		boolean hasAttacked = false;
		boolean isHuman = controller.getMyCharacter().getSide().equals(SideName.HUMAN);
		System.out.println("E' il tuo turno!");
		do{
			choose = chooseAction(hasMoved,isHuman, hasAttacked); 
			switch(choose){
			case "move": 
				Coordinates destinationCoordinates = chooseAdiacentCoordinates(controller.getMyCharacter().getCurrentPosition(), controller.getMyCharacter().getPercorrableDistance());
				controller.callMove(destinationCoordinates);
				break;
			case "card":
				chooseObjectCard();
				break;
			case "attack":
				ArrayList<String> command = new ArrayList<String>();
				command.add("attack");
				controller.callObjectCard(command);
				break;
			case "end":
				break;
			}
		}
		while (!choose.equals("end"));
		controller.callEndTurn();
	}

	private void chooseObjectCard() {
		ArrayList<String> availableCards = new ArrayList<String>();
		System.out.println("Carte disponibili:");
		for(int i = 0; i < controller.getMyCharacter().getBagSize(); i++){
			String cardName = controller.getMyCharacter().getCardFromBag(i).toString();
			if(!cardName.equals("defense")){
				availableCards.add(cardName);
				System.out.println(cardName);
			}
			
		}
		System.out.println("Scegli la carta che vuoi utilizzare");
		String chosenCard = in.nextLine();
		while(!availableCards.contains(chosenCard)){
			System.out.println("ERROR: Inserisci carta valida!");
			chosenCard = in.nextLine();
		}
		executeObjectCard(chosenCard);
	}


	private void executeObjectCard(String chosenCard) {
		ArrayList<String> command = new ArrayList<String>();
		command.add(chosenCard);
		if(chosenCard.equals("spotlight")){
			System.out.println("In quali coordinate vuoi lanciare lo spotlight?");
			String stringDestination = in.nextLine();
			Coordinates destinationCoord = null;
			try {
				destinationCoord = Coordinates.stringToCoordinates(stringDestination);
			} catch (StringSyntaxNotOfCoordinatesException e) {
				System.out.println("Stringa non convertibile in coordinate");
			}
			while(!destinationCoord.isValid()){
				System.out.println("ERRORE: inserisci coordinate valide");
				stringDestination = in.nextLine();
				try {
					destinationCoord = Coordinates.stringToCoordinates(stringDestination);
				} catch (StringSyntaxNotOfCoordinatesException e) {
					System.out.println("Stringa non convertibile in coordinate");
				}
			}
			command.add(stringDestination);
		}
		controller.callObjectCard(command);	
	}

	@Override
	public void showUsedCard(ArrayList<String> command) {
		System.out.println("Hai usato la carta"+command.get(0));
		if(command.size()>1)
			System.out.println("alle coordinate "+command.get(1));
		
	}

	private Coordinates chooseAdiacentCoordinates(Coordinates currentPosition, int percorrableDistance) {
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(currentPosition, percorrableDistance);
		System.out.println("Scegli la coordinata di destinazione");
		for(Coordinates coord : selectableCoordinates){
			System.out.println(coord.toString());
		}
		String stringCoord = in.nextLine();
		Coordinates selectedCoord = null;
		try {
			selectedCoord = Coordinates.stringToCoordinates(stringCoord);
		} catch (StringSyntaxNotOfCoordinatesException e) {
			System.out.println("Stringa non convertibile in coordinate");
		}
		while(!selectableCoordinates.contains(selectedCoord)){
			System.out.println("ERROR: Inserisci coordinate valide!");
			stringCoord = in.nextLine();
			try {
				selectedCoord = Coordinates.stringToCoordinates(stringCoord);
			} catch (StringSyntaxNotOfCoordinatesException e) {
				System.out.println("Stringa non convertibile in coordinate");
			}
		}
		return selectedCoord;	
	}

	private String chooseAction(boolean hasMoved, boolean isHuman, boolean hasAttacked) {
		String command;
		
		System.out.println("Scegli azione:");
		if(!hasMoved){
			System.out.println("[move] - muovi");
		}
		if(isHuman && (controller.getMyCharacter().getBagSize() > 0)){
			System.out.println("[card] - usa carta oggetto");
		}
		if(!isHuman && !hasAttacked && hasMoved){
			System.out.println("[attack] - attacca");
		}
		if(hasMoved){
			System.out.println("[end] - finisci turno");
		}
		command = in.nextLine();
		while(!((command.equals("move") && !hasMoved) || 
		   (command.equals("card") && (isHuman && (controller.getMyCharacter().getBagSize() > 0))) ||
		   (command.equals("attack") && (!isHuman && !hasAttacked && hasMoved)) ||
		   (command.equals("end") && hasMoved)))
		{
			System.out.println("Comando non valido! Reinserisci il comando");
			command = in.nextLine();
		}
		return command;
	}

	@Override
	public void notifyOthersTurn(String playerTurn) {
		System.out.println("E' il turno di " + playerTurn);
		System.out.println("Aspetto perche' non so cosa fare");
		in.nextLine();
	}

	@Override
	public void printMap(Map map,Coordinates myCoordinates) {
		int j;
		System.out.println("La tua posizione è indicata con mappa è:");
		SectorName[][] graphicMap = map.getGraphicMap();
		for (int i=0;i<graphicMap.length;i++)
		{
			for (j=0;j<graphicMap[i].length-1;j++){
				if (myCoordinates.getLetter() == Coordinates.getLetterFromNumber(j) && myCoordinates.getNumber() == i+1)
					System.out.print("MY,");
				else
					System.out.print(graphicMap[i][j].getAbbrevation() + ",");
			}
			System.out.println(graphicMap[i][j].getAbbrevation());
		}
	}

	@Override
	public void manageSectorCard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageNewObjectCard(ObjectCard card) {
		System.out.println("Hai pescato una nuova carta: "+card.toString());
	}

	@Override
	public void manageUsedObjectCard(ArrayList<String> command) {
		System.out.println("Hai usato la carta "+command.get(0).toString());
		if(command.size() > 0)
			System.out.println("nella coordinata "+command.get(1));
	}
}
