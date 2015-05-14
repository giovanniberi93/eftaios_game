package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.matchPackage.ObjectCardVisitor;

public interface VisitableObjectCard {

	public void accept(ObjectCardVisitor visitor);

}