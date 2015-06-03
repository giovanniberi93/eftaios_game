package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import java.io.Serializable;

import it.polimi.ingsw.beribinaghi.clientMatch.NoiseCoordinatesSelector;


public abstract class SectorCard implements Card,Serializable{
	private static final long serialVersionUID = 1L;
	protected boolean containsObject;

	public boolean containsObject() {
		return containsObject;
	}

	public void accept(NoiseCoordinatesSelector noiseAssigner) {}
}
