package it.polimi.ingsw.beribinaghi.decksPackage;

/**
 * @author La Cugina Trans+
 *
 */
public class Deck {
	
	private Card[] validCards;
	private Card[] discardPile;
	
	private int cardNumber;
	
	public Card pickCard(){
		
		if(validCards.length != 0)
		{
			Card tempCard = new Card();			//creo carta temporanea per lo scambio. Ma si incasina con le 
												//classi che ereditano?
			
			int randomIndex = (int) Math.random()*validCards.length;
			
			tempCard = 
			
		}
			
			
	}
	
	
	public int getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
}
