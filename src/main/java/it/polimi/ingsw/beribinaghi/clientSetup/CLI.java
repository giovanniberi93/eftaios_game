/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

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
		System.out.println("Inserire il nome dell'utente:");
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
	public void receiveCommand() {
		do{
			System.out.println("Cosa vuoi fare? Entra nome partita/Crea Nome partita/Aggiorna/Esci");
			String command = inLine.nextLine();
			String commandType[] = command.split(" "); //Divide il comando in parole
			commandType[0] = correct(commandType[0]);
			if (commandType[0].equals("crea"))
				setupController.create(command.substring(commandType[0].length(), command.length()));
		}while(true);
	}

}
