package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;


/**
 * Class representing a sector card; supertype of NoiseInYourSector, NoiseInAnySector, Silence, 
 *
 */
public abstract class SectorCard implements Card{
	protected boolean containsObject;

	public boolean containsObject() {
		return containsObject;
	}

	public void accept(NoiseCoordinatesSelector noiseAssigner) {}
}
