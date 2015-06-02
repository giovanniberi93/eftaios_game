package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

/**
 * Class representing a fake card; this card is picked in safe sectors, human and alien bases, or after the use of a sedatives card
 *
 */
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
