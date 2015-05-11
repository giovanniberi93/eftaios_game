package it.polimi.ingsw.beribinaghi;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
	public static int PORT = 2767;
	public static int MAX_PLAYER = 8;
	public static final Date WAIT = null;
	
    public static void main( String[] args )
    {
       new ServerManager(PORT);
    }
}
