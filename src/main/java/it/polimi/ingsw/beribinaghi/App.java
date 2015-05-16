package it.polimi.ingsw.beribinaghi;


/**
 * Hello world!
 *
 */
public class App 
{
	public static int PORT = 2767;
	public static int MAX_PLAYER = 8;
	public static final int WAITBEGINMATCH = 10000;
	
    public static void main( String[] args )
    {
       new ServerManager(PORT);
    }
}
