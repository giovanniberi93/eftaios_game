package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import java.io.Serializable;

public abstract class ObjectCard implements Card,Serializable{
	private static final long serialVersionUID = 1L;

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