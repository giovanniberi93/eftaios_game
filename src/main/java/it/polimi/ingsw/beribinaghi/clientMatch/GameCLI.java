/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;

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

	private void printCharacter(String name, String role, SideName side) {
		String sideName;
		if (side == SideName.ALIEN)
			sideName = "Alieno";
		else
			sideName = "Umano";
		System.out.println("Ti chiami " + name + ", sei " + role + " e sei un " + sideName);
	}

	/**
	 * Shows the choice that can be done during the turn, and invokes the associated methods
	 */
	private void managesMyTurn() {
		String choose;
		boolean hasMoved = false;
		boolean hasAttacked = false;
		boolean isHuman = controller.getMyCharacter().getSide().equals(SideName.HUMAN);
		printTurnNumber(controller.getTurnNumber());
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
	public void chooseAnyCoordinates(WatcherNoiseCoordinatesSelector selector){
		Coordinates selected = null;
		System.out.println("Inserisci la coordinata desiderata");
		String coordinatesInString = in.nextLine();
		selected = Coordinates.stringToCoordinates(coordinatesInString);
		while(selected== null || !selected.isValid()){
			System.out.println("Sintassi della stringa inserita errata, reinserisci");
			coordinatesInString = in.nextLine();
			selected = Coordinates.stringToCoordinates(coordinatesInString);
		}
		selector.makeNoise(selected);
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


	/**
	 * executes the actions associated to the choice of an object card
	 * @param chosenCard is the card that is wanted to be used
	 */
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

	/**
	 * @param currentPosition is the position of the player who is doing the movement
	 * @param percorrableDistance is the maximum distance reachable from the player
	 * @return the arrayList of the reachable coordinates
	 */
	private Coordinates chooseAdiacentCoordinates(Coordinates currentPosition, int percorrableDistance) {
		boolean isAlien = (controller.getMyCharacter().getSide().equals(SideName.ALIEN));
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(currentPosition, percorrableDistance, isAlien);
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

	/**
	 * prints the list of the possible actions available to the player
	 * @param hasMoved is true if the player has already moved
	 * @param isHuman is true if the player's character is an human
	 * @param hasAttacked is true if the player has aready attacked
	 * @return the string containing the chosen command
	 */
	private String chooseAction(boolean hasMoved, boolean isHuman, boolean hasAttacked) {
		String command;
		ArrayList<String> availableCommands = new ArrayList<String>();
		
		System.out.println("Scegli azione:");
		if(!hasMoved){
			System.out.println("[move] - muovi");
			availableCommands.add("move");
		}
		if(isHuman && controller.getMyCharacter().getBagSize() > 0 && 
				!controller.isAttemptedEscape() &&
				!(controller.getMyCharacter().getBagSize() == 1 && controller.getMyCharacter().getBag().get(0) instanceof Defense)){
			System.out.println("[card] - usa carta oggetto");
			availableCommands.add("card");
		}
		if(!isHuman && !hasAttacked && hasMoved){
			System.out.println("[attack] - attacca");
			availableCommands.add("attack");
		}
		if(hasMoved){
			System.out.println("[end] - finisci turno");
			availableCommands.add("end");
		}
		command = in.nextLine();
		while(!availableCommands.contains(command))
		{
			System.out.println("Comando non valido! Reinserisci il comando");
			command = in.nextLine();
		}
		return command;
	}

	@Override
	public void showUsedCard(ObjectCard card, Coordinates coord) {
		System.out.print("Il giocatore corrente ha usato la carta "+ card.toString());
		if(coord !=  null)
			System.out.println(" alla coordinata " + coord);
		else
			System.out.println("");
		
	}

	@Override
	public void notifyOthersTurn(String playerTurn) {
		printTurnNumber(controller.getTurnNumber());
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
				System.out.println("Ma sei un alieno e non potrai usarlo.");
		}
		if(!(pickedCards.get(0) instanceof NothingToPick))
			System.out.println("Hai pescato una carta rumore di tipo "+pickedCards.get(0));	
	}



	@Override
	public void showEscapeResult(boolean result, Coordinates coord) {
		if(controller.isMyTurn())
			if (result)
				System.out.println("Sei riuscito a scappare! La tua partita è finita");
			else
				System.out.println("Non sei riuscito a scappare, dovrai provare un'altra scialuppa");
		else{
			String currentPlayerUsername = controller.getCurrentPlayer();
			if (result)
				System.out.println("Il giocatore " + currentPlayerUsername + " è riuscito a scappare dalla scialuppa in "+ coord);
			else
				System.out.println("Il giocatore " + currentPlayerUsername + " non è riuscito a scappare dalla scialuppa in "+coord+", dovrà provare un'altra scialuppa");
		}
	}

	@Override
	public void showNoise(Coordinates noiseCoord) {
		if(noiseCoord.equals(Coordinates.SILENCE))
			System.out.println("SILENZIO");
		else
			System.out.println("Hai sentito un rumore dal settore "+noiseCoord.toString());	
	}

	
	@Override
	public void showAttackCoordinates(Coordinates attackCoord){
		System.out.println("È stato effettuato un attacco nel settore " + attackCoord);
	}

	@Override
	public void showSpottedPlayer(String username, Coordinates position) {
		if(username == null)
			System.out.println("Nessun giocatore è stato scoperto");
		else if (username.equals(controller.getMyPlayerName()))
				System.out.println("Sei stato visto!");
		else
			System.out.println(username + " si trova in " + position);	
	}



	@Override
	public void startRapresenting() {
		this.printMap(controller.getMap(), controller.getMyPosition());
		this.printCharacter(controller.getMyCharacter().getName(), controller.getMyCharacter().getRole(), controller.getMyCharacter().getSide());
		this.changedTurn();
	}

	@Override
	public void printTurnNumber(int turnNumber) {
		System.out.println("");
		System.out.println("Turno numero " + turnNumber);
	}

	@Override
	public void selectObjectToDiscard() {
		System.out.println("Non puoi trasportare più di 3 oggetti; selezionane uno da scartare");
		ArrayList<String> cardsInBag = new ArrayList<String>();
		ArrayList<ObjectCard> bag = controller.getMyCharacter().getBag();
		
		for(int i = 0; i < bag.size(); i++){
			cardsInBag.add(bag.get(i).toString());
			System.out.println(bag.get(i).toString());
		}
		String selectedCard = in.nextLine();
		while(!cardsInBag.contains(selectedCard)){
			System.out.println("Inserisci una carta esistente");
			selectedCard = in.nextLine();
		}
		System.out.println("Hai scartato una carta "+selectedCard.toString());
		controller.getSession().signalDiscardedObjectCard(bag.get(cardsInBag.indexOf(selectedCard)));
		bag.remove(cardsInBag.indexOf(selectedCard));
	}

	
	@Override
	public void notifyDiscardedObject() {
		System.out.println("Il giocatore corrente ha dovuto scartare un oggetto");
	}
	
	@Override
	public void changedTurn() {
		if (controller.isMyTurn())
			this.managesMyTurn();
		else
			this.notifyOthersTurn(controller.getCurrentPlayer());
	}

	@Override
	public void showMatchResults(String[] command) {
		System.out.println("");
		System.out.println("La partita è finita! I vincitori sono:");
		for(int i=1; i<command.length; i++)
			System.out.println(command[i]);
		System.out.println("");
		System.out.println("Premi invio per continuare");
		in.nextLine();
		controller.endMatch();
	}


	@Override
	public void showKill(String username, String character) {
		if(username == null)
			System.out.println("Nessuno è rimasto ucciso!");
		else if(username.equals(controller.getMyPlayerName())){
			System.out.println("Sei stato ucciso!");
			controller.getMyCharacter().setCurrentPosition(null);
		}
		else
			System.out.println("Il giocatore " + username + ", nel ruolo di " + character + ", è stato ucciso");
	}


	@Override
	public void showSurvived(String username) {
		if(username.equals(controller.getMyPlayerName()))
			System.out.println("Sei riuscito a difenderti!");
		else 
			System.out.println("Il giocatore " + username + " è riuscito a difendersi");
		
	}

	@Override
	public void showPlayerExit(String username, String character) {
		System.out.println(username+", nel ruolo di "+character+", è uscito dal gioco");
		
	}
}

