package it.polimi.ingsw.beribinaghi.mapPackage;

public enum TypeSector {
	    BLANK(new BlankSector(null)), SAFE(new SafeSector(null)), 
	    DANGEROUS(new DangerousSector(null)), SHALLOP(new ShallopSector(null)), 
	    HUMANBASE(new HumanBase(null)),ALIENBASE(new AlienBase(null));
	    
	    private Sector sector;
	    
	    private TypeSector(Sector sector) {
	         this.sector=sector;
	    }
	     
	    public Sector getSector(){
	         return sector;
	    }
}
