package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

public class Silence extends DangerousSectorCard {

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
