package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

/**
 * Class representing a NoiseInYourSector card
 *
 */
public class NoiseInYourSector extends DangerousSectorCard {
	private static final long serialVersionUID = 1L;

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
