package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import java.io.Serializable;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;

/**
 * Class representing a sector card; supertype of NoiseInYourSector, NoiseInAnySector, Silence, 
 *
 */
public abstract class SectorCard implements Card,Serializable{
	private static final long serialVersionUID = 1L;
	protected boolean containsObject;

	public boolean containsObject() {
		return containsObject;
	}

	public void accept(NoiseCoordinatesSelector noiseAssigner) {}
}
