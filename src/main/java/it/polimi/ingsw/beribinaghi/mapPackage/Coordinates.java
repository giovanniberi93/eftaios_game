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
	 * Constructs a Coordinates object from letter and number
	 * @param letter
	 * @param number
	 * 
	 */
	public Coordinates(char letter,int number){
		this.letter = letter;
		this.number = number;
	}
	
	public boolean isValid(){
		int number = this.getNumber();
		int letterNumber = getNumberFromLetter(this.getLetter());
		return (number >= 1 && number <= 14 && letterNumber >= 0 && letterNumber <= 22);
	}
	
	public String toString(){
		String string = new String(this.getLetter()+""+this.getNumber());
		return string;
	}
	
	public static char getLetterFromNumber(int num){
		return (char) ('a' + num);
	}
	
	public static int getNumberFromLetter(char letter){
		return (int) (letter - 'a');
	}
}
