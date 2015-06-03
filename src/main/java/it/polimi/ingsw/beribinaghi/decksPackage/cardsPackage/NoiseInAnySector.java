package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

/**
 * Class representing a Noise in any sector card
 *
 */
public class NoiseInAnySector extends DangerousSectorCard {
	private static final long serialVersionUID = 1L;

	public NoiseInAnySector(boolean containsObject) {
		super(containsObject);
	}
	
	public String toString(){
		return new String("anySector");
	}

	@Override
	public void accept(NoiseCoordinatesSelector noiseAssigner) {
		noiseAssigner.visit(this);
	}
	
	
}
