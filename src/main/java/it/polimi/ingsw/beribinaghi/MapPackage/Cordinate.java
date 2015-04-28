/**
 * 
 */
package it.polimi.ingsw.beribinaghi.MapPackage;

/**
 * Immutable class.
 * It conteins a cordinate of the map
 *	
 */
public final class Cordinate {
	private char letter;
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

	private int number;
	
	/**
	 * @param letter
	 * @param number
	 * 		It generates a Codinate whit letter and number
	 */
	public Cordinate(char letter,int number){
		this.letter = letter;
		this.number = number;
	}

}
