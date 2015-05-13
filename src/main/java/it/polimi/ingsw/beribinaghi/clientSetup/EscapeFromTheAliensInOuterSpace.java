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
	public final static InterfaceType interfaceType = InterfaceType.CLI;
	public final static String address = "127.0.0.1";
	public final static int port = 2767;
	
	public static void main(String[] args) {
		GraphicInterface gi = interfaceType.getGraphicInterface();
		SetupSession ss = connectionType.getSetupSession();
		new SetupController(gi,ss);
	}

}
