package it.polimi.ingsw.beribinaghi.gameNames;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.mapPackage.AlienBase;
import it.polimi.ingsw.beribinaghi.mapPackage.BlankSector;
import it.polimi.ingsw.beribinaghi.mapPackage.DangerousSector;
import it.polimi.ingsw.beribinaghi.mapPackage.HumanBase;
import it.polimi.ingsw.beribinaghi.mapPackage.SafeSector;
import it.polimi.ingsw.beribinaghi.mapPackage.Sector;
import it.polimi.ingsw.beribinaghi.mapPackage.ShallopSector;

public enum SectorName {
	    BLANK(new BlankSector(null, null)),
	    SAFE(new SafeSector(null, null)), 
	    DANGEROUS(new DangerousSector(null, DangerousSectorsDeck.getInstance())),
	    SHALLOP(new ShallopSector(null, null)), 
	    HUMANBASE(new HumanBase(null, null)),
	    ALIENBASE(new AlienBase(null, null));
	    
	    private Sector sector;
	    
	    private SectorName(Sector sector) {
	         this.sector=sector;
	    }
	     
	    public Sector getSector(){
	         return sector;
	    }
}
