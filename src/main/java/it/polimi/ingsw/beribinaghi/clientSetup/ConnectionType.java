/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;


/**
 *enum explain the type of possible connections;
 *
 */
public enum ConnectionType {
	RMI(new SetupRMISession()),SOCKET(new SetupSocketSession());
	
	  private SetupSession setupSession;
	    
	    private ConnectionType(SetupSession setupSession) {
	         this.setupSession=setupSession;
	    }
	     
	    /**
	     * @return the setup session that is choosen 
	     */
	    public SetupSession getSetupSession(){
	         return setupSession;
	    }
}
