package it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage;

import it.polimi.ingsw.beribinaghi.matchPackage.ObjectCardVisitor;

public interface VisitableObjectCard extends Card{

	public void accept(ObjectCardVisitor visitor);

}