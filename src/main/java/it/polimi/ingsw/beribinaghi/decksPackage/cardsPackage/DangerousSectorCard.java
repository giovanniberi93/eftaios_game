package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;



public abstract class DangerousSectorCard implements Card {
	private boolean containsObject;
	
	public DangerousSectorCard (boolean containsObject){
		this.containsObject = containsObject;
	}

	public boolean isContainsObject() {
		return containsObject;
	}

	public void setContainsObject(boolean containsObject) {
		this.containsObject = containsObject;
	}
}
