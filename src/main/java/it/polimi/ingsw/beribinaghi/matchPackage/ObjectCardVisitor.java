package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.*;

public interface ObjectCardVisitor {
	public void visit (VisitableObjectCard visitableObjectCard);
	
	public void visit (Defense defense);
	public void visit (Attack attack);
	public void visit (Sedatives sedatives);
	public void visit (Spotlight spotlight);
	public void visit (Teleport teleport);
	public void visit (Adrenalin adrenalin);
}
