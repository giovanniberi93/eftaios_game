package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInAnySector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NoiseInYourSector;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.NothingToPick;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.SectorCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ShallopCard;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Silence;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;

/**
 * is the visitor of a visitor pattern, in order to call the right procedure for the picked sector card 
 *
 */
public interface NoiseCoordinatesSelector {
	
	/**
	 * @param sectorCard is the picked sector card
	 * call the matchController's function make noise
	 */
	public void select(SectorCard sectorCard);
	
	/**
	 * standard visit method of the visitor pattern
	 * @param noiseInYourSector is the visited sectorCard
	 */
	public void visit(NoiseInYourSector noiseInYourSector);
	
	/**
	 * standard visit method of the visitor pattern
	 * @param noiseInAnySector is the visited sectorCard
	 */
	public void visit(NoiseInAnySector noiseInAnySector);
	
	/**
	 * standard visit method of the visitor pattern
	 * @param silence is the visited sectorCard
	 */
	public void visit(Silence silence);
	
	/**
	 * standard visit method of the visitor pattern
	 * @param nothingToPick is the visited sectorCard
	 */
	public void visit(NothingToPick nothingToPick);
	
	/**
	 * standard visit method of the visitor pattern
	 * @param shallopCard is the visited sectorCard
	 */
	public void visit(ShallopCard shallopCard);
}
