package it.polimi.ingsw.beribinaghi.matchPackage;

import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Adrenalin;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Attack;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Defense;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Sedatives;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Spotlight;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Teleport;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.VisitableObjectCard;

public class WatcherObjectCardVisitor implements ObjectCardVisitor {

	@Override
	public void visit(VisitableObjectCard visitableObjectCard) {
		visitableObjectCard.accept(this);

	}

	@Override
	public void visit(Defense defense) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Attack attack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Sedatives sedatives) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Spotlight spotlight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Teleport teleport) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Adrenalin adrenalin) {
		// TODO Auto-generated method stub

	}

}
