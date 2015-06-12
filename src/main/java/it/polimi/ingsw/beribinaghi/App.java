package it.polimi.ingsw.beribinaghi;


/**
 * This class is the main class of Server, it start the server 
 *
 */
public class App 
{
	public static int PORTSOCKET = 2767;
	public static int PORTRMI = 2768;
	public static int MAX_PLAYER = 8;
	public static int NUMBEROFTURNS = 39;
	public static final int WAITBEGINMATCH = 2000;
	public static final int WAITFINISHTURN = 2000;

	
    public static void main( String[] args )
    {
       new ServerManager(PORTSOCKET,PORTRMI);
    }
}
