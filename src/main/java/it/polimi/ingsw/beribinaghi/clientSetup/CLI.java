/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.GameCLI;
import it.polimi.ingsw.beribinaghi.clientMatch.GameInterface;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * class for CLI
 *
 */
public class CLI implements GraphicInterface {
	private Scanner inLine;
	private SetupController setupController;
	
	@Override
	public void setSetupController(SetupController setupController) {
		this.setupController = setupController;
	}

	public CLI() {
		inLine = new Scanner(System.in);
		System.out.println("Connessione al server in corso...");
	}

	@Override
	public boolean signalConnessionError() {
		System.out.println("Connessione con il server fallita, riprovare? (Si/No)");
		String answer = correct(inLine.nextLine());
		return answer.equals("si");
	}
	
	private static String correct(String string) {
		return string.trim().toLowerCase();
	}


	@Override
	public String getUserName() {
		System.out.println("Inserire il tuo nome:");
		return inLine.nextLine();
	}

	@Override
	public void printMatchesName(ArrayList<String> matchesName) {
		if (matchesName.size()>0)
		{
			System.out.println("I match disponibili sono:");
			for (String matchName:matchesName)
				System.out.println(matchName);
		} else
			System.out.println("Non ci sono match disponibili");
	}

	@Override
	public Boolean receiveCommand() {
		Boolean inRoom = false;
		Boolean exitGame = false;
		do{
			System.out.println("Cosa vuoi fare? Entra (nome partita)/Crea (nome partita)/Aggiorna/Esci");
			String command = inLine.nextLine().trim();
			String commandType[] = command.split(" "); //Divide il comando in parole
			commandType[0] = correct(commandType[0]);
			if (commandType.length>1 && commandType[0].equals("crea"))
			{
				if (setupController.create(command.substring(commandType[0].length()+1, command.length()))) //Tutta la stringa tranne il comando
				{
					inRoom = true;
					System.out.println("Partita creata, sei nella room");
					System.out.println("Giocatori aggiunti:");
				}
				else 
					System.out.println("Nome partita già esistente");
			}
			else if (commandType.length>1 && commandType[0].equals("entra"))
			{
				int result = setupController.enter(command.substring(commandType[0].length()+1, command.length()));
				if (result==0){
					System.out.println("Entrato nella partita, sei nella room");
					printPlayer(setupController.getPlayersName());
					inRoom = true;
				}
				else if (result==1)
					System.out.println("Nome partita non esistente");
				else
					System.out.println("Ci sono troppi giocatori in questa partita");
			}
			else if (commandType.length>0 && commandType[0].equals("aggiorna"))
				setupController.printMatch();
			else if (commandType.length>0 && commandType[0].equals("esci"))
				exitGame = true;
			else
				System.out.println("Comando non riconosciuto!");
		}while(!inRoom && !exitGame);
		return !exitGame;
	}

	private void printPlayer(ArrayList<String> playersName) {
		if (playersName.size()>0)
		{
			System.out.println("I giocatori nella room sono:");
			for (String playerName:playersName)
				System.out.println(playerName);
		} else
			System.out.println("Non ci sono ancora giocatori nella room");
	}

	@Override
	public void receiveCommandInRoom() {
		if (inLine.hasNextLine())
		{
			String command = correct(inLine.nextLine());
			if (command.equals("aggiorna"))
				setupController.getPlayersName();
			else if (command.equals("esci"))
				setupController.exitRoom();
			else
				System.out.println("Comando non riconosciuto");
		}
	}

	@Override
	public GameInterface beginMatch() {
		System.out.println("Partita iniziata");
		return (GameInterface) new GameCLI();
	}

	@Override
	public void notifyNewPlayer(String namePlayer) {
		System.out.println(namePlayer);
		
	}

}
