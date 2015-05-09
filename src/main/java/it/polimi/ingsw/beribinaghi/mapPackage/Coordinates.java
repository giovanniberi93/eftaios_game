/**
 * 
 */
package it.polimi.ingsw.beribinaghi.mapPackage;

/**
 * Immutable class.
 * It conteins a cordinate of the map
 *	
 */
public final class Coordinates {
	private char letter;
	private int number;
	
	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * @param letter
	 * @param number
	 * 		It generates a Codinate whit letter and number
	 */
	public Coordinates(char letter,int number){
		this.letter = letter;
		this.number = number;
	}

	public static char getLetter(int num){
		return (char) ('a' + num);
	}
	
	public static int getNumber(char letter){
		return (int) (letter - 'a');
	}
}
