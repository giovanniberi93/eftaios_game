package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

public class NothingToPick extends SectorCard {

	public NothingToPick(){
		super.containsObject = false;
	}
	
	@Override
	public String toString() {
		return "nothing";
	}
	
	@Override
	public void accept(NoiseCoordinatesSelector noiseAssigner) {
		noiseAssigner.visit(this);
	}

}
