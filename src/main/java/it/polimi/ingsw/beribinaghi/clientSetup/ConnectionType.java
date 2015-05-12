/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;


/**
 *enum explain the type of possible connections;
 *
 */
public enum ConnectionType {
	RMI(null),SOCKET(new SetupSocketSession()); //TODO modificare rmi
	
	  private SetupSession setupSession;
	    
	    private ConnectionType(SetupSession setupSession) {
	         this.setupSession=setupSession;
	    }
	     
	    public SetupSession getSetupSession(){
	         return setupSession;
	    }
}
