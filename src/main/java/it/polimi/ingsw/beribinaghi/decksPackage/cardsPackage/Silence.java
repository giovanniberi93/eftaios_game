package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

/**
 * Class representing a Silence card
 *
 */
public class Silence extends DangerousSectorCard {
	private static final long serialVersionUID = 1L;

	public Silence(boolean containsObject) {
		super(containsObject);
	}
	
	public String toString(){
		return new String("silence");
	}
	
	@Override
	public void accept(NoiseCoordinatesSelector noiseAssigner) {
		noiseAssigner.visit(this);
	}

}
