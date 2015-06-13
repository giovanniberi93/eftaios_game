package it.polimi.ingsw.beribinaghi;


/**
 * This class is the main class of Server, it starts the server 
 *
 */
public class App 
{
	public static int PORTSOCKET = 2767;
	public static int PORTRMI = 2768;
	public static int MAX_PLAYER = 8;
	public static int NUMBEROFTURNS = 39;
	public static final int WAITBEGINMATCH = 10000;
	public static final int WAITFINISHTURN = 120000;

	
    public static void main (String[] args){
       new ServerManager(PORTSOCKET,PORTRMI);
    }
}
