package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

public class NothingToPick extends SectorCard {

	private static final long serialVersionUID = 1L;

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
