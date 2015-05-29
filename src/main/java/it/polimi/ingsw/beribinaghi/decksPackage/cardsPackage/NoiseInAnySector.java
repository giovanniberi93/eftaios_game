package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

public class NoiseInAnySector extends DangerousSectorCard {

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
