package it.polimi.ingsw.beribinaghi.decksPackage;

import java.util.ArrayList;

/**
 * Class rapresenting a generic Decxk
 *
 */
public abstract class Deck {
	
	private ArrayList<Card> validCards;
	private ArrayList<Card> discardPile;
	
	private int cardNumber;
	
	/**
	 * @return a random picked card by the deck
	 */
	public Card pickCard(){
		Card pickedCard;
		if(validCards.size() == 0)
			restore();
		int randomIndex = (int) Math.random()*validCards.size();
		pickedCard = validCards.get(randomIndex);
		validCards.remove(randomIndex);
		return pickedCard;
	}
		
	private void restore() {
		validCards.addAll(discardPile);
		discardPile.clear();
	}

	public int getCardNumber() {
		return cardNumber;
	}
	
}
