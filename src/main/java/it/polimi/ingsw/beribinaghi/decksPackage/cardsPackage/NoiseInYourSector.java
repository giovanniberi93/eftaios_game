package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

public class NoiseInYourSector extends DangerousSectorCard {

	public NoiseInYourSector(boolean containsObject) {
		super(containsObject);
	}
	
	public String toString(){
		return new String("yourSector");
	}
	
	@Override
	public void accept(NoiseCoordinatesSelector noiseAssigner) {
		noiseAssigner.visit(this);
	}

}
