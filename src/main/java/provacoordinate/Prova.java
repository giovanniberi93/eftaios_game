package provacoordinate;

import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;

public class Prova {

	public static void main(String[] args) {
		String string = new String("a05");
		Coordinates coord = Coordinates.stringToCoordinates(string);
		System.out.println(coord);
	}
}
