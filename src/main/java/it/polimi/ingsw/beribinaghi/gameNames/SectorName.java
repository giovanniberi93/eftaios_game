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
	    BLANK(new BlankSector(),"B"),
	    SAFE(new SafeSector(),"S"), 
	    DANGEROUS(new DangerousSector(),"D"),
	    SHALLOP(new ShallopSector(),"S"), 
	    HUMANBASE(new HumanBase(),"HM"),
	    ALIENBASE(new AlienBase(),"AB");
	    
	    private Sector sector;
	    private String abbrevation;
	    
	    private SectorName(Sector sector,String abb) {
	         this.sector=sector;
	         this.abbrevation = abb;
	    }
	     
	    public Sector getSector(){
	         return sector;
	    }

		/**
		 * @return the abbrevation
		 */
		public String getAbbrevation() {
			return abbrevation;
		}
}
