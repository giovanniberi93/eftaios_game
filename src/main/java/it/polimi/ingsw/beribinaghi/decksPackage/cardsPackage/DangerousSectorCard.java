package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;



public abstract class DangerousSectorCard extends SectorCard {
	
	public DangerousSectorCard (boolean containsObject){
		super.containsObject = containsObject;
	}

	public void setContainsObject(boolean containsObject) {
		this.containsObject = containsObject;
	}
}
