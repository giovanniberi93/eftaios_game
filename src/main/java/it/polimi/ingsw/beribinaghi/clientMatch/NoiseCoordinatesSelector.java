package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInAnySector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInYourSector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Silence;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;

public interface NoiseCoordinatesSelector {
	
	public Coordinates select(SectorCard sectorCard);
	
	public void visit(NoiseInYourSector noiseInYourSector);
	public void visit(NoiseInAnySector noiseInAnySector);
	public void visit(Silence silence);
	public void visit(NothingToPick nothingToPick);
	public void visit(ShallopCard shallopCard);
}
