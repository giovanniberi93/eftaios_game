package it.polimi.ingsw.beribinaghi.gameNames;

import it.polimi.ingsw.beribinaghi.mapPackage.AlienBase;
import it.polimi.ingsw.beribinaghi.mapPackage.BlankSector;
import it.polimi.ingsw.beribinaghi.mapPackage.DangerousSector;
import it.polimi.ingsw.beribinaghi.mapPackage.HumanBase;
import it.polimi.ingsw.beribinaghi.mapPackage.SafeSector;
import it.polimi.ingsw.beribinaghi.mapPackage.Sector;
import it.polimi.ingsw.beribinaghi.mapPackage.ShallopSector;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

public enum SectorName {
	    BLANK(new BlankSector()),
	    SAFE(new SafeSector()), 
	    DANGEROUS(new DangerousSector()),
	    SHALLOP(new ShallopSector()), 
	    HUMANBASE(new HumanBase()),
	    ALIENBASE(new AlienBase());
	    
	    private Sector sector;
	    
	    private SectorName(Sector sector) {
	         this.sector=sector;
	    }
	     
	    public Sector getSector(){
	         return sector;
	    }
}
