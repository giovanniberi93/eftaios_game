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
	public Coordinates select(SectorCard sectorCard) {
		sectorCard.accept(this);
		return noiseCoordinates;
	}

	@Override
	public void visit(NoiseInYourSector noiseInYourSector) {
		noiseCoordinates = matchController.getMyCharacter().getCurrentPosition();
	}

	@Override
	public void visit(NoiseInAnySector noiseInAnySector) {
		noiseCoordinates = matchController.getGraphicInterface().chooseAnyCoordinates();
	}

	@Override
	public void visit(Silence silence) {
		noiseCoordinates = Coordinates.SILENCE;
	}

	@Override
	public void visit(NothingToPick nothingToPick) {
		noiseCoordinates = null;		
	}

	@Override
	public void visit(ShallopCard shallopCard) {
		noiseCoordinates = null;
		
	}

}
