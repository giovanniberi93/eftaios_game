package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInAnySector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInYourSector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Silence;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;

public class WatcherNoiseCoordinatesSelector implements NoiseCoordinatesSelector {
	
	MatchController matchController;
	Coordinates noiseCoordinates;
	
	WatcherNoiseCoordinatesSelector(MatchController matchController){
		this.matchController = matchController;
	}

	@Override
	public void select(SectorCard sectorCard) {
		sectorCard.accept(this);
	}

	@Override
	public void visit(NoiseInYourSector noiseInYourSector) {
		noiseCoordinates = matchController.getMyCharacter().getCurrentPosition();
		matchController.makeNoise(noiseCoordinates);
	}

	@Override
	public void visit(NoiseInAnySector noiseInAnySector) {
		matchController.getGraphicInterface().chooseAnyCoordinates(this);
	}
	
	public void makeNoise(Coordinates coordinates){
		noiseCoordinates = coordinates;
		matchController.makeNoise(noiseCoordinates);
	}

	@Override
	public void visit(Silence silence) {
		noiseCoordinates = Coordinates.SILENCE;
		matchController.makeNoise(noiseCoordinates);
	}

	@Override
	public void visit(NothingToPick nothingToPick) {
		noiseCoordinates = null;	
		matchController.makeNoise(noiseCoordinates);
	}

	@Override
	public void visit(ShallopCard shallopCard) {
		noiseCoordinates = null;
		matchController.makeNoise(noiseCoordinates);
	}

}
