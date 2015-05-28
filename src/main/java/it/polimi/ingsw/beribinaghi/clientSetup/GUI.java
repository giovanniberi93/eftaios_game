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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
	private static String fontName = "Helvetica";
	private Container cont;
	private Graphics gra;
	private GraphicsDevice gd;
	private SetupController setupController;
	private DefaultListModel<String> listModel = null;
	private DefaultListModel<String> listPlayer;

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
	
	private void paintComponent(Graphics g) {
		super.paintComponents(g);
		setUpInterface();
	}

	private void setUpInterface() {
		gra = cont.getGraphics();
		try {
			Image img = ImageIO.read(new File("media/immintro.jpg").toURI().toURL());
			gra.drawImage(img, 0,0, null);
			img = ImageIO.read(new File("media/logo.jpg").toURI().toURL());
			gra.drawImage(img, this.getWidth()/2 - img.getWidth(null)/2,0, null);
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
		private GUI gui;
		private JInternalFrame errorFrame;
		
		public HandlerConnessionErrorButton(JInternalFrame errorFrame, GUI gui) {
			this.errorFrame = errorFrame;
			this.gui = gui;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
		    errorFrame.setVisible(false);
			errorFrame.dispose();
			cont.removeAll();
			if (button.getText().equals("Si"))
				setupController.connect();
			else{
				if (GUI.FULLSCREEN)
					gd.setFullScreenWindow(null);
				gui.setVisible(false);
				gui.dispose();
			}
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
	public void signalConnessionError() {
		this.paintComponent(this.getGraphics());
		JInternalFrame errorFrame = createInternal("Errore di connessione",Color.RED);
		Container errorCont = errorFrame.getContentPane();
		Label labelErr = new Label("Errore di connessione!");
		Label labelRe = new Label("Vuoi riprovare a connetterti?");
		labelErr.setFont(new Font(fontName, Font.BOLD, 22));
		labelErr.setForeground(Color.WHITE);
		labelRe.setFont(new Font(fontName, Font.BOLD, 18));
		labelRe.setForeground(Color.WHITE);
		errorCont.setBackground(Color.BLACK);
		JButton bYes = new JButton("Si");
		JButton bNo = new JButton("No");
		cont.add(errorFrame);
		Label trys= new Label(); //adding last element
		trys.setVisible(false);
		cont.add(trys);
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
		HandlerConnessionErrorButton handler = new HandlerConnessionErrorButton(errorFrame,this);
		bNo.addActionListener(handler);
		bYes.addActionListener(handler);
		try {
			errorFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
	}


	private class HandlerLogin implements ActionListener {
		private JInternalFrame loginFrame;
		JTextField tUser;
		
		public HandlerLogin(JInternalFrame errorFrame,JTextField tUser) {
			this.loginFrame = errorFrame;
			this.tUser = tUser;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			loginFrame.setVisible(false);
			loginFrame.dispose();
			setupController.login(tUser.getText());
			printMatchesName();
		} 
		
	}
	
	@Override
	public void signalConnessionSuccess() {
		this.paintComponent(this.getGraphics());
		JInternalFrame loginFrame = createInternal("Login",Color.BLUE);
		Container loginCont = loginFrame.getContentPane();
		Label labelRe = new Label("Inserire il tuo nome utente:");
		labelRe.setFont(new Font(fontName, Font.BOLD, 18));
		labelRe.setForeground(Color.WHITE);
		loginCont.setBackground(Color.BLACK);
		JTextField tUser = new JTextField();
		JButton bLogin = new JButton("Login");
		cont.add(loginFrame);
		Label trys= new Label(); //adding last element
		trys.setVisible(false);
		cont.add(trys);
		loginCont.add(labelRe);
		loginCont.add(tUser);
		loginCont.add(bLogin);
		loginCont.add(new Label());
		loginFrame.setBounds(this.getWidth()/2-150, this.getHeight()/2-200, 300, 200);
		labelRe.setBounds(10, 30, 250, 20);
		tUser.setBounds(25, 70, 250, 25);
		bLogin.setBounds(110, 110, 80, 40);
		tUser.requestFocusInWindow();
		loginFrame.setBounds(this.getWidth()/2-150, this.getHeight()/2-200, 300, 200);
		HandlerLogin handler = new HandlerLogin(loginFrame,tUser);
		bLogin.addActionListener(handler);
		try {
			loginFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
	}

	private class HandlerChooser implements ActionListener {
		private JTextField fieldNew;
		
		public HandlerChooser(JTextField fieldNew) {
			this.fieldNew = fieldNew;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (button.getText().equals("Aggiorna"))
			{
				ArrayList<String> matchesName = setupController.getMatchesName();
				listModel.clear();
				if (matchesName.size()>0)
					for (String matchName : matchesName)
						listModel.addElement(matchName);
				else
					listModel.addElement("Non ci sono partite disponibili");
			}
			else {
				if (setupController.create(fieldNew.getText())){
					graphicsInRoom();
					listPlayer.addElement(setupController.getPlayerName());
					new inRoomThread(setupController).start();
				}else
					signalEnterCreateError("Nome partita già esistente");
			}
		} 
		
	}
	
	private class HandlerMouse implements MouseListener {
		private JList<String> list;
		
		public HandlerMouse(JList<String> list) {
			this.list = list;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2 && !list.getSelectedValue().equals("Non ci sono partite disponibili")) {
				if (setupController.enter(list.getSelectedValue())==0){
					graphicsInRoom();
					printPlayer(setupController.getPlayersName());
					new inRoomThread(setupController).start();
				}
				else
					signalEnterCreateError("Partita già iniziata");
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}	
	}
	
	private class HandlerError implements ActionListener {
		private JInternalFrame frame;
		
		public HandlerError(JInternalFrame frame) {
			this.frame = frame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		} 
		
	}
	
	private synchronized void signalEnterCreateError(String message) {
		JInternalFrame errorFrame = createInternal(message,Color.RED);
		Container errorCont = errorFrame.getContentPane();
		Label labelErr = new Label(message);
		labelErr.setFont(new Font(fontName, Font.BOLD, 20));
		labelErr.setForeground(Color.WHITE);
		errorCont.setBackground(Color.BLACK);
		JButton bOk = new JButton("Ok");
		cont.add(errorFrame);
		errorCont.add(labelErr);
		errorCont.add(bOk);
		errorCont.add(new Label());
		errorFrame.setBounds(this.getWidth()/2-100, this.getHeight()/2-50, 200, 80);
		labelErr.setBounds(10, 10, 280, 30);
		bOk.setBounds(100, 65, 80, 40);
		errorFrame.setBounds(this.getWidth()/2-140, this.getHeight()/2-100, 280, 150);
		HandlerError handler = new HandlerError(errorFrame);
		bOk.addActionListener(handler);
		try {
			errorFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
	}
	
	private void printMatchesName() {
		ArrayList<String> matchesName = this.setupController.getMatchesName();
		listModel = new DefaultListModel<String>();
		if (matchesName.size()>0)
			for (String matchName : matchesName)
				listModel.addElement(matchName);
		else
			listModel.addElement("Non ci sono partite disponibili");
		JList<String> listComp = new JList<String>(listModel);
		Label labelTit = new Label("PARTITE ATTIVE:");
		labelTit.setFont(new Font(fontName, Font.BOLD, 22));
		labelTit.setForeground(Color.WHITE);
		listComp.setBackground(Color.BLACK);
		listComp.setFont(new Font(fontName, Font.BOLD, 18));
		listComp.setForeground(Color.WHITE);
		listComp.setBorder(new LineBorder(Color.BLUE));
		JTextField fieldNew = new JTextField();
		JButton btnCreate = new JButton("Crea");
		JButton btnUpdate = new JButton("Aggiorna");
		Label labelCre = new Label("Crea una nuova partita:");
		Label labelInf = new Label("Nome:");
		JScrollPane scroll = new JScrollPane(listComp);
		scroll.setBorder(new LineBorder(Color.BLUE));
		labelCre.setFont(new Font(fontName, Font.BOLD, 22));
		labelCre.setForeground(Color.WHITE);
		labelInf.setFont(new Font(fontName, Font.BOLD, 18));
		labelInf.setForeground(Color.WHITE);
		this.paintComponent(getGraphics());
		cont.add(fieldNew);
		cont.add(btnCreate);
		cont.add(labelTit);
		cont.add(scroll);
		cont.add(labelCre);
		cont.add(labelInf);
		Label trys= new Label(); //adding last element
		trys.setVisible(false);
		cont.add(btnUpdate);
		cont.add(trys);
		labelTit.setBounds(this.getWidth()-500, 150, 300, 20);
		listComp.setBounds(this.getWidth()-500, labelTit.getY()+50, 400, 250);
		btnUpdate.setBounds(listComp.getX()+listComp.getWidth()-80, listComp.getY()-40, 80, 40);
		labelCre.setBounds(listComp.getX(), listComp.getY()+listComp.getHeight()+20, 400, 20);
		labelInf.setBounds(labelCre.getX(), labelCre.getY()+40, 70, 20);
		fieldNew.setBounds(labelInf.getX() + labelInf.getWidth()+10, labelInf.getY()-labelInf.getHeight()/4, 225, 25);
		btnCreate.setBounds(fieldNew.getX()+fieldNew.getWidth()+10, labelInf.getY()-10, 80, 35);
		scroll.setBounds(listComp.getBounds());
		if (matchesName.size()>0)
			listComp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listComp.setVisibleRowCount(-1);
		HandlerChooser handlerChooser = new HandlerChooser(fieldNew);
		HandlerMouse mouseHandler = new HandlerMouse(listComp);
		btnUpdate.addActionListener(handlerChooser);
		btnCreate.addActionListener(handlerChooser);
		listComp.addMouseListener(mouseHandler);
		this.paintComponent(gra);
	}
	
	private synchronized void graphicsInRoom() {
		cont.removeAll();
		this.paintComponent(gra);
		listPlayer = new DefaultListModel<String>();
		JList<String> listPl = new JList<String>(listPlayer);
		Label labelTit = new Label("Nome partita: " + setupController.getMatchName());
		labelTit.setFont(new Font(fontName, Font.BOLD, 22));
		labelTit.setForeground(Color.WHITE);
		Label labelInd = new Label("I giocatori nella room sono:");
		labelInd.setFont(new Font(fontName, Font.BOLD, 18));
		labelInd.setForeground(Color.WHITE);
		listPl.setBackground(Color.BLACK);
		listPl.setFont(new Font(fontName, Font.BOLD, 18));
		listPl.setForeground(Color.WHITE);
		listPl.setBorder(new LineBorder(Color.BLUE));
		JScrollPane scroll = new JScrollPane(listPl);
		scroll.setBorder(new LineBorder(Color.BLUE));
		cont.add(labelTit);
		cont.add(labelInd);
		cont.add(scroll);
		Label trys= new Label(); //adding last element
		trys.setVisible(false);
		cont.add(trys);
		labelTit.setBounds(this.getWidth()-500, 150, 300, 20);
		labelInd.setBounds(this.getWidth()-500, labelTit.getY()+70, 300, 20);
		listPl.setBounds(this.getWidth()-500, labelTit.getY()+100, 400, 250);
		scroll.setBounds(listPl.getBounds());
		listPl.setVisibleRowCount(-1);
		this.paintComponent(gra);
	}
	
	private void printPlayer(ArrayList<String> playersName) {
		if (playersName.size()>0)
			for (String playerName : playersName)
				listPlayer.addElement(playerName);
	}

	@Override
	public void setSetupController(SetupController setupController) {
		this.setupController = setupController;
	}

	@Override
	public void receiveCommandInRoom() {

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientSetup.GraphicInterface#beginMatch()
	 */
	@Override
	public GameInterface beginMatch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyNewPlayer(String namePlayer) {
		listPlayer.addElement(namePlayer);
	}

}
