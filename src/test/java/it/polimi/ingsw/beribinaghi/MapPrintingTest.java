package it.polimi.ingsw.beribinaghi;

import static org.junit.Assert.assertNotNull;
import it.polimi.ingsw.beribinaghi.clientMatch.GameGUI;
import it.polimi.ingsw.beribinaghi.clientSetup.GUI;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.mapPackage.MapModel;
import it.polimi.ingsw.beribinaghi.matchPackage.GameSessionServerSide;
import it.polimi.ingsw.beribinaghi.matchPackage.Match;
import it.polimi.ingsw.beribinaghi.playerPackage.Player;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MapPrintingTest {
	GameGUI game;
	GUI gui;
	
	@Test
	public synchronized void setup(){
		gui = new GUI();
		game = (GameGUI) gui.beginMatch();
		//game.printMap(new Map("sas",MapModel.GALILEI), new Coordinates('a', 0));
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
