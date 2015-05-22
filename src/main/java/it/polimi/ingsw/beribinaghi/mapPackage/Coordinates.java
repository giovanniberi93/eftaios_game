package it.polimi.ingsw.beribinaghi.mapPackage;

import java.io.Serializable;

/**
 * Immutable class.
 * It conteins a cordinate of the map
 *	
 */
public final class Coordinates implements Serializable{
	private static final long serialVersionUID = 1L;
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
	
	@Override
	public String toString() {
		String adaptedNumber = String.format("%02d", this.getNumber());
		String string = new String(this.getLetter()+adaptedNumber);
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + letter;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (letter != other.letter)
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	public static char getLetterFromNumber(int num){
		return (char) ('a' + num);
	}
	
	public static int getNumberFromLetter(char letter){
		return (int) (letter - 'a');
	}
}
