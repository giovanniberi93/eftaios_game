package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

/**
 * Class representing a object card; supertype of adrenalin, sedatives, attack, teleport, defense, spotlight
 *
 */
public abstract class ObjectCard implements Card{
	
	public static ObjectCard stringToCard(String cardName){
		switch(cardName){
		case "teleport":
			return new Teleport();
		case "spotlight":
			return new Spotlight();
		case "sedatives":
			return new Sedatives();
		case "attack":
			return new Attack();
		case "adrenalin":
			return new Adrenalin();			
		default:
			return null;
		}
	}
	
}