package provacoordinate;

import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.StringSyntaxNotOfCoordinatesException;

public class Prova {

	public static void main(String[] args) {
		String string = new String("a5");
		Coordinates coord = null;
		try {
			coord = Coordinates.stringToCoordinates(string);
		} catch (StringSyntaxNotOfCoordinatesException e) {
			System.out.println("Stringa non convertibile in coordinate");
		}
		System.out.println(coord);
	}
}
