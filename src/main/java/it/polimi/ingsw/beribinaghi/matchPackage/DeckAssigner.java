package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.mapPackage.AlienBase;
import it.polimi.ingsw.beribinaghi.mapPackage.BlankSector;
import it.polimi.ingsw.beribinaghi.mapPackage.DangerousSector;
import it.polimi.ingsw.beribinaghi.mapPackage.HumanBase;
import it.polimi.ingsw.beribinaghi.mapPackage.SafeSector;
import it.polimi.ingsw.beribinaghi.mapPackage.Sector;
import it.polimi.ingsw.beribinaghi.mapPackage.ShallopSector;

public interface DeckAssigner {
	
	public void assignDeck(Sector sector);
	
	public void visit(SafeSector safeSector);
	public void visit(DangerousSector dangerousSector);
	public void visit(ShallopSector shallopSector);
	public void visit(AlienBase alienBase);
	public void visit(HumanBase humanBase);
	public void visit(BlankSector blankSector);

}
