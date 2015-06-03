package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import java.io.Serializable;



public abstract class DangerousSectorCard extends SectorCard implements Serializable{
	private static final long serialVersionUID = 1L;

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
