package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.matchPackage.ObjectCardVisitor;

public class Attack implements VisitableObjectCard {

	@Override
	public void accept(ObjectCardVisitor visitor) {
		visitor.visit(this);
		
	}

}
