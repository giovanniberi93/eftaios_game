package it.polimi.ingsw.beribinaghi.gameNames;

import it.polimi.ingsw.beribinaghi.mapPackage.AlienBase;
import it.polimi.ingsw.beribinaghi.mapPackage.BlankSector;
import it.polimi.ingsw.beribinaghi.mapPackage.DangerousSector;
import it.polimi.ingsw.beribinaghi.mapPackage.HumanBase;
import it.polimi.ingsw.beribinaghi.mapPackage.SafeSector;
import it.polimi.ingsw.beribinaghi.mapPackage.Sector;
import it.polimi.ingsw.beribinaghi.mapPackage.ShallopSector;

public enum SectorName {
	    BLANK(new BlankSector(null)), SAFE(new SafeSector(null)), 
	    DANGEROUS(new DangerousSector(null)), SHALLOP(new ShallopSector(null)), 
	    HUMANBASE(new HumanBase(null)),ALIENBASE(new AlienBase(null));
	    
	    private Sector sector;
	    
	    private SectorName(Sector sector) {
	         this.sector=sector;
	    }
	     
	    public Sector getSector(){
	         return sector;
	    }
}
