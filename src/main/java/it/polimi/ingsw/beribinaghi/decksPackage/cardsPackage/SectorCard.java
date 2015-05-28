package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;


public abstract class SectorCard implements Card{
	protected boolean containsObject;

	public boolean containsObject() {
		return containsObject;
	}

	public void accept(NoiseCoordinatesSelector noiseAssigner) {}
}
