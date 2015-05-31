/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

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
				hasMoved = true;
				controller.callMove(destinationCoordinates);
				break;
			case "card":
				chooseObjectCard();
				break;
			case "attack":
				hasAttacked = true;
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

	@Override
	public Coordinates chooseAnyCoordinates(){
		Coordinates selected = null;
		System.out.println("Inserisci la coordinata desiderata");
		String coordinatesInString = in.nextLine();
		selected = Coordinates.stringToCoordinates(coordinatesInString);
		while(selected== null || !selected.isValid()){
			System.out.println("Sintassi della stringa inserita errata, reinserisci");
			coordinatesInString = in.nextLine();
			selected = Coordinates.stringToCoordinates(coordinatesInString);
		}
		return selected;
	}
	
	@Override
	public void chooseObjectCard() {
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
			destinationCoord = Coordinates.stringToCoordinates(stringDestination);
			while(destinationCoord == null || !destinationCoord.isValid()){
				System.out.println("ERRORE: inserisci coordinate valide");
				stringDestination = in.nextLine();
				destinationCoord = Coordinates.stringToCoordinates(stringDestination);
			}
			command.add(stringDestination);
		}
		controller.callObjectCard(command);	
	}

	private Coordinates chooseAdiacentCoordinates(Coordinates currentPosition, int percorrableDistance) {
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(currentPosition, percorrableDistance);
		System.out.println("Scegli la coordinata di destinazione");
		for(Coordinates coord : selectableCoordinates){
			System.out.println(coord.toString());
		}
		String stringCoord = in.nextLine();
		Coordinates selectedCoord = null;
		selectedCoord = Coordinates.stringToCoordinates(stringCoord);
		while(!selectableCoordinates.contains(selectedCoord)){
			System.out.println("ERROR: Inserisci coordinate valide!");
			stringCoord = in.nextLine();
			selectedCoord = Coordinates.stringToCoordinates(stringCoord);
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
	public void showUsedCard(ObjectCard card, Coordinates coord) {
		System.out.println("Il giocatore corrente ha usato la carta "+ card.toString());
		if(coord !=  null)
			System.out.print(" alla coordinata " + coord);
		
	}

	@Override
	public void notifyOthersTurn(String playerTurn) {
		System.out.println("E' il turno di " + playerTurn);
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
	public void showPickedCards(ArrayList<Card> pickedCards) {
		if(pickedCards.size() == 2){
			System.out.println("Hai trovato un oggetto di tipo: " + pickedCards.get(1));
			if(controller.getMyCharacter().getSide() == SideName.ALIEN)
				System.out.print(", ma sei un alieno e non potrai usarlo.");
		}
		if(!(pickedCards.get(0) instanceof NothingToPick))
			System.out.println("Hai pescato una carta rumore di tipo "+pickedCards.get(0));	
	}


	@Override
	public void manageUsedObjectCard(ArrayList<String> command) {
		System.out.println("Hai usato la carta "+command.get(0).toString());
		if(command.size() > 0)
			System.out.println("nella coordinata "+command.get(1));
	}


	@Override
	public void showEscapeResult(boolean result, Coordinates coord) {
		System.out.println(controller.getCurrentPlayer());
		if(!result)
			System.out.print(" non");
		System.out.print(" è riuscito a scappare!");
		if(!result)
			System.out.print("Peggio per lui.");		
	}

	@Override
	public void showNoise(Coordinates noiseCoord) {
		if(noiseCoord.equals(Coordinates.SILENCE))
			System.out.println("SILENZIO");
		else
			System.out.print("Rumore in "+noiseCoord.toString());	
	}

	@Override
	public void showAttackResult(Coordinates attackCoordinates, ArrayList<String> killed, ArrayList<String> survived) {
		System.out.println("E' stato effettuato un attacco in " + attackCoordinates + "!");
		for(String kills : killed)
			System.out.print(kills);
		System.out.println(" sono stati uccisi,");
		for(String surv : survived)
			System.out.print(surv);
		System.out.print(" sono riusciti a difendersi");		
	}

	@Override
	public void showSpottedPlayer(String username, Coordinates position) {
		System.out.println("Spotlight!");
		System.out.println(username + " si trova in " + position);	
	}

	@Override
	public void manageSectorCard(Card card) {
		// TODO Auto-generated method stub
		
	}


}
