/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientSetup;

import it.polimi.ingsw.beribinaghi.clientMatch.GameInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import javax.media.*;

/**
 * Manage GUI 
 * it manages the interface for pre match communication
 *
 */
public class GUI extends JFrame implements GraphicInterface {
	private static final long serialVersionUID = 1L;
	private static Boolean FULLSCREEN = false;
	private static final String title = "Fuga dagli alieni nello spazio più profondo";
	private Container cont;
	private Graphics gra;
	private GraphicsDevice gd;

	public GUI(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
	    this.setUndecorated(true);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle(title);
	    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setVisible(true);
	    cont = this.getContentPane();
	    cont.setBackground(Color.BLACK);
	    if (GUI.FULLSCREEN)
	    	gd.setFullScreenWindow(this);
	  //  playVideo();
	}
	
	private void paintComponent() {
		super.paintComponents(this.getGraphics());
		setUpInterface();
	}

	private void setUpInterface() {
		gra = cont.getGraphics();
		try {
			Image img = ImageIO.read(new File("media/immintro.jpg").toURI().toURL());
			gra.drawImage(img, 0,0/* this.getWidth()-img.getWidth(null)*/, null);
			img = ImageIO.read(new File("media/logo.jpg").toURI().toURL());
			gra.drawImage(img, this.getWidth()/2 - img.getWidth(null)/2,0/* this.getWidth()-img.getWidth(null)*/, null);
		} catch (IOException e) {
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
	
	private class HandlerConnessionErrorButton implements ActionListener {

		Boolean pressed = false;
		Boolean choose = false;
		
		public Boolean getChoose() {
			return choose;
		}

		public Boolean isPressed() {
			return pressed;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			pressed = true;
			if (button.getText().equals("Si"))
				choose = true;
			else
				choose = false;
		} 
		
	}
	
	private JInternalFrame createInternal(String title,Color borderColor)
	{
		JInternalFrame internalFrame = new JInternalFrame(title,false,false,false,false);
		internalFrame.setVisible(true);
		internalFrame.setBorder(new LineBorder(borderColor));
		return internalFrame;
	}

	@Override
	public synchronized boolean signalConnessionError() {
		this.paintComponent();
		JInternalFrame errorFrame = createInternal("Errore di connessione",Color.RED);
		Container errorCont = errorFrame.getContentPane();
		Label labelErr = new Label("Errore di connessione!");
		Label labelRe = new Label("Vuoi riprovare a connetterti?");
		labelErr.setFont(new Font("Helvetica", Font.BOLD, 22));
		labelErr.setForeground(Color.WHITE);
		labelRe.setFont(new Font("Helvetica", Font.BOLD, 18));
		labelRe.setForeground(Color.WHITE);
		errorCont.setBackground(Color.BLACK);
		JButton bYes = new JButton("Si");
		JButton bNo = new JButton("No");
		cont.add(errorFrame);
		errorCont.add(labelErr);
		errorCont.add(labelRe);
		errorCont.add(bYes);
		errorCont.add(bNo);
		errorCont.add(new Label());
		errorFrame.setBounds(this.getWidth()/2-150, this.getHeight()/2-200, 300, 200);
		labelErr.setBounds(10, 10, 250, 20);
		labelRe.setBounds(10, 50, 250, 20);
		bYes.setBounds(20, 100, 80, 40);
		bNo.setBounds(200, 100, 80, 40);
		errorFrame.setBounds(this.getWidth()/2-150, this.getHeight()/2-200, 300, 200);
		HandlerConnessionErrorButton handler = new HandlerConnessionErrorButton();
		bNo.addActionListener(handler);
		bYes.addActionListener(handler);
		try {
			errorFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
		while (!handler.isPressed()){
			try {
				this.wait(50);
			} catch (InterruptedException e) {
			}
		}
		errorFrame.setVisible(false);
		errorFrame.dispose();
		if (!handler.getChoose())
		{
			if (GUI.FULLSCREEN)
				gd.setFullScreenWindow(null);
			this.setVisible(false);
			this.dispose();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#getUserName()
	 */
	@Override
	public String getUserName() {
		this.paintComponent();
		JInternalFrame loginFrame = createInternal("Login",Color.BLUE);
		Container errorCont = loginFrame.getContentPane();
		Label labelLogin = new Label("Login");
		Label labelRe = new Label("Inserire il tuo nome utente:");
		labelLogin.setFont(new Font("Helvetica", Font.BOLD, 22));
		labelLogin.setForeground(Color.WHITE);
		labelRe.setFont(new Font("Helvetica", Font.BOLD, 18));
		labelRe.setForeground(Color.WHITE);
		errorCont.setBackground(Color.BLACK);
		JTextField tUser = new JTextField();
		JButton bLogin = new JButton("Login");
		cont.add(loginFrame);
		errorCont.add(labelLogin);
		errorCont.add(labelRe);
		errorCont.add(tUser);
		errorCont.add(bLogin);
		errorCont.add(new Label());
		loginFrame.setBounds(this.getWidth()/2-150, this.getHeight()/2-200, 300, 200);
		labelLogin.setBounds(10, 10, 250, 20);
		labelRe.setBounds(10, 50, 250, 20);
		tUser.setBounds(20, 100, 80, 40);
		bLogin.setBounds(200, 100, 80, 40);
		loginFrame.setBounds(this.getWidth()/2-150, this.getHeight()/2-200, 300, 200);
		HandlerConnessionErrorButton handler = new HandlerConnessionErrorButton();
	/*	bNo.addActionListener(handler);
		bYes.addActionListener(handler);
		try {
			errorFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
		while (!handler.isPressed()){
			try {
				this.wait(50);
			} catch (InterruptedException e) {
			}
		}
		errorFrame.setVisible(false);
		errorFrame.dispose();
		if (!handler.getChoose())
		{
			if (GUI.FULLSCREEN)
				gd.setFullScreenWindow(null);
			this.setVisible(false);
			this.dispose();
			return false;
		}
		return true;*/
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
