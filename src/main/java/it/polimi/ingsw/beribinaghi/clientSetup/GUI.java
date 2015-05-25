/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.GameInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.media.*;

/**
 * Manage GUI 
 * it manages the interface for pre match communication
 *
 */
public class GUI extends JFrame implements GraphicInterface {
	private static final long serialVersionUID = 1L;
	private static final String title = "Fuga dagli alieni nello spazio più profondo";
	private Container cont;

	public GUI(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
	    this.setUndecorated(true);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle(title);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setVisible(true);
	    cont = this.getContentPane();
	    cont.setBackground(Color.BLACK);
	   // gd.setFullScreenWindow(this);
	  //  playVideo();
	    setUpInterface();
	}
	
	private void setUpInterface() {
		try {
			Image img = ImageIO.read(new File("media/immintro.jpg").toURI().toURL());
			Graphics gra = cont.getGraphics();
			gra.drawImage(img, 0, this.getWidth()-img.getWidth(null), null);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void playVideo() {
		URL mediaURL;
		try {
			mediaURL = new File("media/introvideo.mov").toURI().toURL();
			Player mediaPlayer = Manager.createRealizedPlayer( mediaURL );
			Component video = mediaPlayer.getVisualComponent();
			this.add( video, BorderLayout.CENTER);
			mediaPlayer.start();
		} catch (MalformedURLException e) {
		} catch (NoPlayerException e) {
		} catch (CannotRealizeException e) {
		} catch (IOException e) {
		}
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#signalConnessionError()
	 */
	@Override
	public boolean signalConnessionError() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#printMatchesName(java.util.ArrayList)
	 */
	@Override
	public void printMatchesName(ArrayList<String> matchesName) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#receiveCommand()
	 */
	@Override
	public Boolean receiveCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#setSetupController(it.polimi.ingsw.beribinaghi.clientSetup.SetupController)
	 */
	@Override
	public void setSetupController(SetupController setupController) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#receiveCommandInRoom()
	 */
	@Override
	public void receiveCommandInRoom() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#beginMatch()
	 */
	@Override
	public GameInterface beginMatch() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#notifyNewPlayer(java.lang.String)
	 */
	@Override
	public void notifyNewPlayer(String namePlayer) {
		// TODO Auto-generated method stub

	}

}
