/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

/**
 * main client class
 *
 */
public class EscapeFromTheAliensInOuterSpace {

	public final static ConnectionType connectionType = ConnectionType.SOCKET;
	public final static InterfaceType interfaceType = InterfaceType.GUI;


	public final static String ADDRESS = "127.0.0.1";
	public final static int SOCKETPORT = 2767;
	public final static int RMIPORT = 2768;
	
	public static void main(String[] args) {
		GraphicInterface gi = interfaceType.getGraphicInterface();
		SetupSession ss = connectionType.getSetupSession();
		new SetupController(gi,ss);
	}

}
