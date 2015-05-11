package it.polimi.ingsw.beribinaghi.mapPackage;

public enum SectorType {
	    BLANK(new BlankSector(null)), SAFE(new SafeSector(null)), 
	    DANGEROUS(new DangerousSector(null)), SHALLOP(new ShallopSector(null)), 
	    HUMANBASE(new HumanBase(null)),ALIENBASE(new AlienBase(null));
	    
	    private Sector sector;
	    
	    private SectorType(Sector sector) {
	         this.sector=sector;
	    }
	     
	    public Sector getSector(){
	         return sector;
	    }
}
