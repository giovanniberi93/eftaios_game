package it.polimi.ingsw.beribinaghi.clientSetup;

/**
 *enum explain the type of possible interfaces;
 *
 */
public enum InterfaceType {
	CLI(new CLI()),GUI(null);
	
	private GraphicInterface graphicInterface;

	private InterfaceType(GraphicInterface graphicInterface) {
		this.graphicInterface=graphicInterface;
	}

	public GraphicInterface getGraphicInterface(){
		return graphicInterface;
	}

}
