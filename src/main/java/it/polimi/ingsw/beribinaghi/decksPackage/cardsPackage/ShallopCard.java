package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;


public class ShallopCard extends SectorCard {
	private boolean isDamaged;
	
	public ShallopCard (boolean isDamaged){
		this.setDamaged(isDamaged);
		this.containsObject = false;
	}

	public boolean isDamaged() {
		return isDamaged;
	}

	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

}