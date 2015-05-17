package it.polimi.ingsw.beribinaghi.decksPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;

import java.util.ArrayList;

/**
 * Class representing a generic Deck
 *
 */
public abstract class Deck {
	
	protected ArrayList<Card> validCards = new ArrayList<Card>();
	private ArrayList<Card> discardPile = new ArrayList<Card>();
	
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
		
	/**
	 * restore the ArrayList validCards from the ArrayList discardPile and clear the discardPile
	 */
	private void restore() {
		validCards.addAll(discardPile);
		discardPile.clear();
	}
	
	/**
	 * add a card to the discardPile of a deck
	 * @param card is the card that must be added to discardPile
	 */
	public void addToDiscardPile (Card card) throws WrongCardTypeException{
		
		if(card.getClass() == (discardPile.get(0)).getClass() || card.getClass() == (validCards.get(0)).getClass())
			discardPile.add(card);
		else throw new WrongCardTypeException("");
		}
	}


