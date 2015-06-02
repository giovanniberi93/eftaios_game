package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;



/**
 * Class representing a Dangerous sector card
 *
 */
public abstract class DangerousSectorCard extends SectorCard {
	
	public DangerousSectorCard (boolean containsObject){
		super.containsObject = containsObject;
	}

	public void setContainsObject(boolean containsObject) {
		this.containsObject = containsObject;
	}
	
	public String toString(){
		return new String("dangerous");
	}

}
