package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;


/**
 * Class representing a Adrenalin card
 *
 */
public class ShallopCard extends SectorCard {
	private static final long serialVersionUID = 1L;
	private boolean isDamaged;
	
	public ShallopCard (boolean isDamaged){
		this.setDamaged(isDamaged);
		this.containsObject = false;
	}

	public boolean isDamaged() {
		return isDamaged;
	}

	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}
	
	@Override
	public void accept(NoiseCoordinatesSelector noiseAssigner) {
		noiseAssigner.visit(this);
	}

}
