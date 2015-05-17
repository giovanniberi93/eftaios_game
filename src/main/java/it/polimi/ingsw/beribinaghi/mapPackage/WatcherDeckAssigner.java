package it.polimi.ingsw.beribinaghi.mapPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.DangerousSectorsDeck;
import it.polimi.ingsw.beribinaghi.decksPackage.ShallopsDeck;
import it.polimi.ingsw.beribinaghi.matchPackage.DeckAssigner;

public class WatcherDeckAssigner implements DeckAssigner {
	private DangerousSectorsDeck dangerous;
	private ShallopsDeck shallop;
	

	public WatcherDeckAssigner (DangerousSectorsDeck dangerous, ShallopsDeck shallop){
		this.dangerous = dangerous;
		this.shallop = shallop;
	}
	
	@Override
	public void assignDeck(Sector sector) {
		sector.acceptDeck(this);
		
	}

	@Override
	public void visit(SafeSector safeSector) {
		safeSector.setAssociatedDeck(null);
		
	}

	@Override
	public void visit(DangerousSector dangerousSector) {
		dangerousSector.setAssociatedDeck(dangerous);
		
	}

	@Override
	public void visit(ShallopSector shallopSector) {
		shallopSector.setAssociatedDeck(shallop);
		
	}

	@Override
	public void visit(AlienBase alienBase) {
		alienBase.setAssociatedDeck(null);
		
	}

	@Override
	public void visit(HumanBase humanBase) {
		humanBase.setAssociatedDeck(null);
		
	}

	@Override
	public void visit(BlankSector blankSector) {
		blankSector.setAssociatedDeck(null);
		
	}

}
