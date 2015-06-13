/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.clientSetup.GUI;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.CharacterName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.DangerousSector;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;
import it.polimi.ingsw.beribinaghi.playerPackage.Character;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * This class manages the graphic user interface in match
 *
 */
public class GameGUI implements GameInterface,MouseListener,Runnable {
	private static int imgx = 90;
	private static int imgy = 135;
	private static int separate = 85;
	private MatchController controller;
	private GUI frame;
	private Graphics g;
	private int mapWidth;
	private int mapHeight;
	private int mapMarginWidth;
	private int mapMarginHeight;
	private Map map;
	private Timer timer;
	private int lw,lh;
	private HashMap<SectorName,Image> hashSector = new HashMap<SectorName,Image>();
	private HashMap<SectorName,Image> hashSelectedSector = new HashMap<SectorName,Image>();
	private HashMap<SectorName,Image> hashMySector = new HashMap<SectorName,Image>();
	private HashMap<SectorName,Image> hashAttackSector = new HashMap<SectorName,Image>();
	private HashMap<SectorName,Image> hashSpotSector = new HashMap<SectorName,Image>();
	private HashMap<String,Image> hashCharacter = new HashMap<String,Image>();
	private HashMap<String,Image> hashCard = new HashMap<String,Image>();
	private HashMap<String,Image> hashLogo = new HashMap<String,Image>();
	private Image imgAttackButton;
	private Image imgFinishTurn;
	private Image imgRumors;
	private ArrayList<Coordinates> posShallop = new ArrayList<Coordinates>();
	private ArrayList<Integer> sitShallop = new ArrayList<Integer>();
	private boolean hasMoved;
	private boolean selectany;
	private WatcherNoiseCoordinatesSelector selector;
	private boolean hasAttacked;
	private boolean isHuman;
	private Coordinates rumorsCoordinates = null;
	private Coordinates attackCoordinates = null;
	private ArrayList<Coordinates> spottedCoordinates = new ArrayList<Coordinates>();
	private boolean spotted;
	private int xRect,yRect,heightRect,widthRect;
	private int nKill,nSurvived;
	private Image imgShallopOk;
	private Image imgShallopKo;
	private PlayerHandler playerRumors;
	private PlayerHandler playerAttack;
	private PlayerHandler playerSilence;
	private int posEscape=-1;
	private boolean isFinish;
	private boolean discard;
	private JTextArea notArea;
	
	
	public GameGUI(GUI frame) {
		super();
		this.frame = frame;
		g = frame.getGraphics();
		printBegin();
		frame.addMouseListener(this);
	}
	
	private void printBegin() {
		mapWidth = frame.getWidth()/10*7;
		lw = mapWidth/(Map.WIDTH*3+1);
		lh =  (int) (lw*(Math.sqrt(3)));
		mapHeight = lh*2*Map.HEIGHT;
		mapMarginWidth =20;
		mapMarginHeight = (frame.getHeight()-mapHeight)/2;
		xRect = this.mapMarginWidth+this.mapWidth+2*imgx+5;
		yRect = 20;
		heightRect =  this.mapMarginHeight + 3*(separate+60)-30;
		widthRect = frame.getWidth()-xRect-5;
		this.isFinish = false;
		try {
			hashSector.put(SectorName.DANGEROUS, ImageIO.read(new File("media/sector/dangerousSector.png").toURI().toURL()));
			hashSector.put(SectorName.SAFE, ImageIO.read(new File("media/sector/safeSector.png").toURI().toURL()));
			hashSector.put(SectorName.HUMANBASE, ImageIO.read(new File("media/sector/humanBase.png").toURI().toURL()));
			hashSector.put(SectorName.ALIENBASE, ImageIO.read(new File("media/sector/alienBase.png").toURI().toURL()));
			hashSector.put(SectorName.SHALLOP, ImageIO.read(new File("media/sector/shallopSector.png").toURI().toURL()));
			hashSector.put(SectorName.BLANK, ImageIO.read(new File("media/sector/blankSector.png").toURI().toURL()));
			hashCharacter.put(CharacterName.CAPTAIN.getPersonalName(), ImageIO.read(new File("media/character/captain.png").toURI().toURL()));
			hashCharacter.put(CharacterName.PILOT.getPersonalName(), ImageIO.read(new File("media/character/pilot.png").toURI().toURL()));
			hashCharacter.put(CharacterName.PSYCHOLOGIST.getPersonalName(), ImageIO.read(new File("media/character/psychologist.png").toURI().toURL()));
			hashCharacter.put(CharacterName.SOLDIER.getPersonalName(), ImageIO.read(new File("media/character/soldier.png").toURI().toURL()));
			hashCharacter.put(CharacterName.FIRSTALIEN.getPersonalName(), ImageIO.read(new File("media/character/alien1.png").toURI().toURL()));
			hashCharacter.put(CharacterName.SECONDALIEN.getPersonalName(), ImageIO.read(new File("media/character/alien2.png").toURI().toURL()));
			hashCharacter.put(CharacterName.THIRDALIEN.getPersonalName(), ImageIO.read(new File("media/character/alien3.png").toURI().toURL()));
			hashCharacter.put(CharacterName.FOURTHALIEN.getPersonalName(), ImageIO.read(new File("media/character/alien4.png").toURI().toURL()));
			hashSelectedSector.put(SectorName.DANGEROUS, ImageIO.read(new File("media/sector/dangerousSectorSelected.png").toURI().toURL()));
			hashSelectedSector.put(SectorName.SAFE, ImageIO.read(new File("media/sector/safeSectorSelected.png").toURI().toURL()));
			hashSelectedSector.put(SectorName.HUMANBASE, ImageIO.read(new File("media/sector/humanBaseSelected.png").toURI().toURL()));
			hashSelectedSector.put(SectorName.ALIENBASE, ImageIO.read(new File("media/sector/alienBaseSelected.png").toURI().toURL()));
			hashSelectedSector.put(SectorName.SHALLOP, ImageIO.read(new File("media/sector/shallopSectorSelected.png").toURI().toURL()));
			hashMySector.put(SectorName.DANGEROUS, ImageIO.read(new File("media/sector/dangerousSectorMy.png").toURI().toURL()));
			hashMySector.put(SectorName.SAFE, ImageIO.read(new File("media/sector/safeSectorMy.png").toURI().toURL()));
			hashMySector.put(SectorName.HUMANBASE, ImageIO.read(new File("media/sector/humanBaseMy.png").toURI().toURL()));
			hashMySector.put(SectorName.ALIENBASE, ImageIO.read(new File("media/sector/alienBaseMy.png").toURI().toURL()));
			hashMySector.put(SectorName.SHALLOP, ImageIO.read(new File("media/sector/shallopSectorMy.png").toURI().toURL()));
			hashAttackSector.put(SectorName.DANGEROUS, ImageIO.read(new File("media/sector/dangerousSectorAttack.png").toURI().toURL()));
			hashAttackSector.put(SectorName.SAFE, ImageIO.read(new File("media/sector/safeSectorAttack.png").toURI().toURL()));
			hashAttackSector.put(SectorName.HUMANBASE, ImageIO.read(new File("media/sector/humanBaseAttack.png").toURI().toURL()));
			hashAttackSector.put(SectorName.ALIENBASE, ImageIO.read(new File("media/sector/alienBaseAttack.png").toURI().toURL()));
			hashAttackSector.put(SectorName.SHALLOP, ImageIO.read(new File("media/sector/shallopSectorAttack.png").toURI().toURL()));
			hashSpotSector.put(SectorName.DANGEROUS, ImageIO.read(new File("media/sector/dangerousSectorSpot.png").toURI().toURL()));
			hashSpotSector.put(SectorName.SAFE, ImageIO.read(new File("media/sector/safeSectorSpot.png").toURI().toURL()));
			hashSpotSector.put(SectorName.HUMANBASE, ImageIO.read(new File("media/sector/humanBaseSpot.png").toURI().toURL()));
			hashSpotSector.put(SectorName.ALIENBASE, ImageIO.read(new File("media/sector/alienBaseSpot.png").toURI().toURL()));
			hashSpotSector.put(SectorName.SHALLOP, ImageIO.read(new File("media/sector/shallopSectorSpot.png").toURI().toURL()));
			hashCard.put("attack", ImageIO.read(new File("media/objectCard/attack.png").toURI().toURL()));
			hashCard.put("teleport", ImageIO.read(new File("media/objectCard/teleport.png").toURI().toURL()));
			hashCard.put("sedatives", ImageIO.read(new File("media/objectCard/sedatives.png").toURI().toURL()));
			hashCard.put("spotlight", ImageIO.read(new File("media/objectCard/spotlight.png").toURI().toURL()));
			hashCard.put("defense", ImageIO.read(new File("media/objectCard/defense.png").toURI().toURL()));
			hashCard.put("adrenalin", ImageIO.read(new File("media/objectCard/adrenalin.png").toURI().toURL()));
			hashCard.put("silence", ImageIO.read(new File("media/sectorCard/silence.png").toURI().toURL()));
			hashCard.put("anySector", ImageIO.read(new File("media/sectorCard/noiseInEverySector.png").toURI().toURL()));
			hashCard.put("yourSector", ImageIO.read(new File("media/sectorCard/rumorsInYourSector.png").toURI().toURL()));
			hashCard.put("shallopOk", ImageIO.read(new File("media/shallopOk.png").toURI().toURL()));
			hashCard.put("shallopKo", ImageIO.read(new File("media/shallopKo.png").toURI().toURL()));
			hashLogo.put("character", ImageIO.read(new File("media/logoCard/characterLogo.png").toURI().toURL()));
			hashLogo.put("escape", ImageIO.read(new File("media/logoCard/escapeLogo.png").toURI().toURL()));
			hashLogo.put("object", ImageIO.read(new File("media/logoCard/objectLogo.png").toURI().toURL()));
			hashLogo.put("sector", ImageIO.read(new File("media/logoCard/sectorLogo.png").toURI().toURL()));
			imgAttackButton = ImageIO.read(new File("media/attack.png").toURI().toURL());
			imgFinishTurn = ImageIO.read(new File("media/end.png").toURI().toURL());
			imgRumors = ImageIO.read(new File("media/sector/dangerousSectorRumors.png").toURI().toURL());
			imgShallopOk = ImageIO.read(new File("media/sector/shallopOk.png").toURI().toURL());
			imgShallopKo = ImageIO.read(new File("media/sector/shallopKo.png").toURI().toURL());
			playerRumors = new PlayerHandler(new MediaLocator(new File("media/noise.wav").toURI().toURL()));
			playerAttack = new PlayerHandler(new MediaLocator(new File("media/attack.wav").toURI().toURL()));
			playerSilence = new PlayerHandler(new MediaLocator(new File("media/silence.wav").toURI().toURL()));
		} catch (IOException e) {
		}
	}

	@Override
	public void setController(MatchController matchController) {
		controller = matchController;
	}
	
	private class TimerHandler implements ActionListener{
		private Boolean trans;
		private Image img;
		private int y;
		private String finalString;
		private static final int diff = 1;
		
		public TimerHandler(Image img,String finalString) {
			this.img = img;
			this.finalString = finalString;
			y=frame.getHeight();
			trans = true;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (trans){
				if (y>(frame.getHeight()-img.getHeight(null))/2){
					y-=diff;
					g.fillRect((frame.getWidth()-img.getWidth(null))/2, y+img.getHeight(null), img.getWidth(null), diff);
					g.drawImage(img,(frame.getWidth()-img.getWidth(null))/2,y,null);
				} else {
					trans = false;
					g.setColor(Color.WHITE);
					g.setFont(new Font(frame.getFontName(), Font.BOLD, 18));
					g.drawString(finalString,(frame.getWidth()-img.getWidth(null))/2-170,(frame.getHeight()+img.getHeight(null))/2+40);
					finishTransiction(this);
				}
			} else {
				printMap(controller.getMap(),controller.getMyPosition());
				printCards();
				notArea = new JTextArea();
				JScrollPane scroll = new JScrollPane (notArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				frame.getContentPane().add(scroll);
				scroll.setVisible(true);
				notArea.setBounds(xRect, yRect, widthRect, heightRect);
				notArea.setLineWrap(true);
				notArea.setWrapStyleWord(true);
				scroll.setBounds(xRect, yRect, widthRect, heightRect);
				notArea.setFont(new Font(frame.getFontName(), Font.BOLD, 14));
				notArea.setEditable(false);
				changedTurn();
			}
		}	
	}


	private void printCharacter(Character character) {
		TimerHandler timerHandler = new TimerHandler(hashCharacter.get(character.getName()), "Ti chiami " + character.getName() + ", sei " + character.getRole() + " e sei un " + character.getSide());
		timer = new Timer(1, timerHandler);
		timer.setRepeats(true);
		timer.start();
	}

	private void finishTransiction(TimerHandler timerHandler) {
		timer.stop();
		timer.setInitialDelay(4000);
		timer.setRepeats(false);
		timer.start();
	}

	private void managesMyTurn() {
		iniTurn();
		this.hasMoved = false;
		this.hasAttacked = false;
		this.selectany = false;
		this.spotted = false;
		this.discard = false;
		notArea.append("\nE' il tuo turno\n");
		notArea.append("turno numero " + controller.getTurnNumber()+"\n");
		this.printSelectableCoordinates(this.controller.getMyCharacter().getCurrentPosition(),controller.getMyCharacter().getPercorrableDistance());
		notArea.append("clicca sulla coordinata desiderata\n");
		if (isHuman && controller.getMyCharacter().getBagSize()>0){
			notArea.append("o usa le carte oggetto\n");
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}
	
	@Override
	public void notifyOthersTurn(String playerTurn) {
		g.setColor(Color.WHITE);
		g.setFont(new Font(frame.getFontName(), Font.BOLD, 24));
		iniTurn();
		notArea.append("\nTurno di " + playerTurn + "\n");
		notArea.append("turno numero " + controller.getTurnNumber() + "\n");
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}
	
	private void iniTurn(){
		g.setColor(Color.BLACK);
		g.fillRect( this.mapMarginWidth+this.mapWidth+imgx-20, this.mapMarginHeight +(separate+60), imgx, imgy);
		g.fillRect( this.mapMarginWidth+this.mapWidth+imgx-20, this.mapMarginHeight + 2*(separate+60), imgx, imgy);
		if (this.rumorsCoordinates!=null)
			if (!this.rumorsCoordinates.equals(controller.getMyPosition()))
				this.printSector( this.rumorsCoordinates.getNumber()-1, Coordinates.getNumberFromLetter(this.rumorsCoordinates.getLetter()), SectorName.DANGEROUS, 0);
			else
				this.printSector( this.rumorsCoordinates.getNumber()-1, Coordinates.getNumberFromLetter(this.rumorsCoordinates.getLetter()), SectorName.DANGEROUS, 2);
		if (this.attackCoordinates!=null){
			SectorName[][] graphicMap = map.getGraphicMap();
			int i = attackCoordinates.getNumber()-1;
			int j = Coordinates.getNumberFromLetter(attackCoordinates.getLetter());
			if (!this.attackCoordinates.equals(controller.getMyPosition()))
				this.printSector(i, j, graphicMap[i][j], 0);
			else
				this.printSector(i, j, graphicMap[i][j], 2);
		} 
		if (this.spottedCoordinates.size()>0){
			SectorName[][] graphicMap = map.getGraphicMap();
			for (Coordinates singleSpot:spottedCoordinates){
				int i = singleSpot.getNumber()-1;
				int j = Coordinates.getNumberFromLetter(singleSpot.getLetter());
				if (!singleSpot.equals(controller.getMyPosition()))
					this.printSector(i, j, graphicMap[i][j], 0);
				else
					this.printSector(i, j, graphicMap[i][j], 2);
			}
		} 
		this.rumorsCoordinates = null;
		this.attackCoordinates = null;
		this.spottedCoordinates.clear();
		g.setColor(Color.BLACK);
		g.fillRect(xRect, yRect, widthRect, heightRect);
		if (this.posEscape!=-1){
			Coordinates coordToClose = posShallop.get(posEscape);
			sitShallop.set(posEscape, 2);
			this.printSector(coordToClose.getNumber()-1, Coordinates.getNumberFromLetter(coordToClose.getLetter()), SectorName.SHALLOP, 0);
			posEscape = -1;
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}
	
	private void printSelectableCoordinates(Coordinates currentPosition, int percorrableDistance) {
		boolean isAlien = (controller.getMyCharacter().getSide().equals(SideName.ALIEN));
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(currentPosition, percorrableDistance,isAlien);
		SectorName[][] graphicMap = map.getGraphicMap();
		for (Coordinates coordinates:selectableCoordinates){	
			int i = coordinates.getNumber()-1;
			int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
			this.printSector(i,j,graphicMap[i][j],1);
		}
	}
	
	private void chooseMove(Coordinates coordinatesSelected) {
		boolean isAlien = (controller.getMyCharacter().equals(SideName.ALIEN));
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(controller.getMyPosition(), controller.getMyCharacter().getPercorrableDistance(), isAlien);
		if (selectableCoordinates.contains(coordinatesSelected)){
			hasMoved  = true;
			SectorName[][] graphicMap = map.getGraphicMap();
			Coordinates myOldCoordinates = controller.getMyPosition();
			this.printSector(myOldCoordinates.getNumber()-1,Coordinates.getNumberFromLetter(myOldCoordinates.getLetter()),graphicMap[myOldCoordinates.getNumber()-1][Coordinates.getNumberFromLetter(myOldCoordinates.getLetter())],0);
			for (Coordinates coordinates:selectableCoordinates){	
				int i = coordinates.getNumber()-1;
				int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
				if (coordinates.equals(coordinatesSelected))
					this.printSector(i,j,graphicMap[i][j],2);
				else
					this.printSector(i,j,graphicMap[i][j],0);
			}
			controller.callMove(coordinatesSelected);
		}
	}


	private void printCards() {
		printLogos();
		this.printSingleCard(hashCharacter.get(controller.getMyCharacter().getName()),0,1,imgx,imgy,0);
		for(int i = 0; i < controller.getMyCharacter().getBagSize(); i++){
			Image img = hashCard.get(controller.getMyCharacter().getCardFromBag(i).toString());
			this.printSingleCard(img,3,i+1,imgx,imgy,0);
		}
	}
	
	private void clearCard() {
		g.setColor(Color.BLACK);
		g.fillRect(this.mapMarginWidth+this.mapWidth+imgx+-20, this.mapMarginHeight + 3*(separate+60), 4*imgx, imgy);
		for(int i = 0; i < controller.getMyCharacter().getBagSize(); i++){
			Image img = hashCard.get(controller.getMyCharacter().getCardFromBag(i).toString());
			this.printSingleCard(img,3,i+1,imgx,imgy,0);
		}
	}
	
	private void clearDefenseCard() {
		int j=0;
		Boolean find = false;
		g.setColor(Color.BLACK);
		g.fillRect(this.mapMarginWidth+this.mapWidth+imgx+-20, this.mapMarginHeight + 3*(separate+60), 4*imgx, imgy);
		for(int i = 0; i < controller.getMyCharacter().getBagSize(); i++){
			if (!controller.getMyCharacter().getCardFromBag(i).toString().equals("defense") || find){
				Image img = hashCard.get(controller.getMyCharacter().getCardFromBag(i).toString());
				this.printSingleCard(img,3,j+1,imgx,imgy,0);
				j++;
			} else
				find = true;
		}
	}

	private void printLogos() {
		this.printSingleCard(hashLogo.get("character"), 0, 0,separate,separate,(imgy-separate)/2);
		this.printSingleCard(hashLogo.get("sector"), 1, 0,separate, separate,(imgy-separate)/2);
		this.printSingleCard(hashLogo.get("escape"), 2, 0,separate, separate,(imgy-separate)/2);
		this.printSingleCard(hashLogo.get("object"), 3, 0,separate, separate,(imgy-separate)/2);
	}

	private void printSingleCard(Image img, int i,int j,int dimensionx,int dimensiony,int marginy) {
		if (j==0)
			g.drawImage(img, this.mapMarginWidth+this.mapWidth+j*(dimensionx+20)-20, this.mapMarginHeight + i*(separate+60)+marginy,dimensionx,dimensiony, null);
		else
			g.drawImage(img, this.mapMarginWidth+this.mapWidth+j*(dimensionx+10)-30, this.mapMarginHeight + i*(separate+60),dimensionx,dimensiony, null);
	}
	
	@Override
	public void printMap(Map map, Coordinates myCoordinates) {
		int j;
		this.map = map;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(Color.WHITE);
		SectorName[][] graphicMap = map.getGraphicMap();
		for (int i=0;i<graphicMap.length;i++)
		{
			for (j=0;j<graphicMap[i].length;j++){
				if (i==myCoordinates.getNumber()-1 && j==Coordinates.getNumberFromLetter(myCoordinates.getLetter()))
					printSector(i,j,graphicMap[i][j],2);
				else
					printSector(i,j,graphicMap[i][j],0);
			}
		}
	}

	private void printSector(int i, int j, SectorName sectorName, int type) {
		if (!sectorName.equals(SectorName.BLANK)){
			Image img;
			if (type==0)
				img = hashSector.get(sectorName);
			else if (type==1)
				img = hashSelectedSector.get(sectorName);
			else if (type==2)
				img = hashMySector.get(sectorName);
			else if (type==3)
				img = this.imgRumors;
			else if (type==4)
				img = hashAttackSector.get(sectorName);
			else
				img = hashSpotSector.get(sectorName);
			g.drawImage(img, mapMarginWidth+j*3*lw, mapMarginHeight+lh*(i*2+j%2), 4*lw, 2*lh, null);
			if (sectorName.equals(SectorName.DANGEROUS) || sectorName.equals(SectorName.SAFE)){
				String num = String.valueOf(i+1);
				if (num.length()==1)
					num = "0"+num;
				g.setColor(Color.GRAY);
				g.setFont(new Font(frame.getFontName(), Font.BOLD, 14));
				g.setFont(frame.getFont());
				g.drawString((Coordinates.getLetterFromNumber(j)+"" + num).toUpperCase(),mapMarginWidth+j*3*lw+2*lw-12 , mapMarginHeight+lh*(i*2+j%2)+lh+5);
			} else if (sectorName.equals(SectorName.SHALLOP)){
				Coordinates coordinates = new Coordinates(Coordinates.getLetterFromNumber(j),i+1);
				if (!posShallop.contains(coordinates)){
					posShallop.add(coordinates);
					sitShallop.add(0);
				} 
				int pos = posShallop.indexOf(coordinates);
				g.setColor(Color.WHITE);
				g.setFont(new Font(frame.getFontName(), Font.BOLD, 18));
				Image cardImg = null;
				if (sitShallop.get(pos)==1){
					img = this.imgShallopOk;
					cardImg = hashCard.get("shallopOk");
				} else if (sitShallop.get(pos)==2){
					img = this.imgShallopKo;
					cardImg = hashCard.get("shallopKo");
				}
				g.drawImage(img, mapMarginWidth+j*3*lw, mapMarginHeight+lh*(i*2+j%2), 4*lw, 2*lh, null);
				g.drawString(""+(posShallop.indexOf(coordinates)+1),mapMarginWidth+j*3*lw+2*lw-3 , mapMarginHeight+lh*(i*2+j%2)+lh+5);
				if (controller.isMyTurn() && controller.getMyCharacter().getCurrentPosition().equals(coordinates))
					this.printSingleCard(cardImg, 2, 1, imgx, imgy,0);
			}	
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		try{
		if (controller.isMyTurn() || this.isFinish){
			int x = e.getX()-this.mapMarginWidth;
			int y = e.getY()-this.mapMarginHeight;
			int q = (int) (x/lw)/3;
			int r = (int) (y/lh-q%2)/2;
			if (x>0 && x<mapWidth && y>0 && y<mapHeight+lh)
			{
				if ((x/lw)%3==0) 
					if (((y/lh)%2==1 && q%2==0) || ((y/lh)%2==0 && q%2==1))
					{
						if (x%lw==0 || (float)(y%lh)/(x%lw)>Math.sqrt(3))
						{
							q--;
							r+=1-q%2;
						}
					}
					else{
						if (x%lw==0 || (float)(-y%lh+lh)/(x%lw)>Math.sqrt(3))
						{
							q--;
							r-=q%2;
						}	
					}
				if (!hasMoved && !spotted)
					this.chooseMove(new Coordinates(Coordinates.getLetterFromNumber(q),r+1));
				else if (selectany)
					this.selectRumorCoordinates(new Coordinates(Coordinates.getLetterFromNumber(q),r+1));
				else if (spotted)
					this.selectSpottedCoordinates(new Coordinates(Coordinates.getLetterFromNumber(q),r+1));
			} else if (e.getX()>this.mapMarginWidth+this.mapWidth-200 && e.getX()<this.mapMarginWidth+this.mapWidth-80 && e.getY()>2 && e.getY()<70 && ((hasMoved && !selectany && !spotted && !discard) || isFinish)){
					this.endTurn();
			}
			else if (e.getX()>this.mapMarginWidth+this.mapWidth-400 && e.getX()<this.mapMarginWidth+this.mapWidth-280 && e.getY()>2 && e.getY()<70 && hasMoved && !selectany && !isHuman && !hasAttacked && !discard){
				this.attack();
			} else if ((isHuman || discard) && !selectany){
				for (int i=1; i<=controller.getMyCharacter().getBagSize();i++){
					if (i<=3 && (e.getX()>this.mapMarginWidth+this.mapWidth+i*(imgx+10)-30) && (e.getX()<this.mapMarginWidth+this.mapWidth+i*(imgx+10)-30+imgx) && (e.getY()>this.mapMarginHeight + 3*(separate+60)) && (e.getY()<this.mapMarginHeight + 3*(separate+60)+imgy)){
						if (!discard)
							this.useObjectCard(controller.getMyCharacter().getBag().get(i-1));
						else
							this.discardCard(controller.getMyCharacter().getBag().get(i-1));
						this.clearCard();
						break;
					} else if (i==4 && discard && (e.getX()>this.mapMarginWidth+this.mapWidth+imgx-20) && (e.getX()<this.mapMarginWidth+this.mapWidth+imgx-20+imgx) && (e.getY()>this.mapMarginHeight + 2*(separate+60)) && (e.getY()<this.mapMarginHeight + 2*(separate+60)+imgy)){
						this.discardCard(controller.getMyCharacter().getBag().get(i-1));
					}
				}
			}
		}
		}
		catch (NoSuchElementException e2){
			this.returnToMenu();
			controller.connectionProblem();
		}
		catch (NullPointerException e2){
			this.returnToMenu();
			controller.connectionProblem();
		}
		
	}

	private void selectSpottedCoordinates(Coordinates coordinates) {
		SectorName[][] graphicMap = map.getGraphicMap();
		int i = coordinates.getNumber()-1;
		int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
		if (!graphicMap[i][j].equals(SectorName.BLANK)){
			this.printSector(i,j,graphicMap[i][j],5);
			this.spottedCoordinates.add(coordinates);
			notArea.append("Spotlight nel settore " + coordinates.toString() +  ", giocatori:\n");
			ArrayList<String> command = new ArrayList<String>();
			command.add("spotlight");
			command.add(coordinates.toString());
			controller.callObjectCard(command);
			this.spotted = false;
			if (this.hasMoved)
				this.showEndButton();
			this.clearCard();
		}
	}

	private void useObjectCard(Card card) {
		SectorName[][] graphicMap = map.getGraphicMap();
		if(!card.toString().equals("defense")){
			if(card.toString().equals("spotlight")){
				g.setColor(Color.BLACK);
				g.fillRect(this.mapMarginWidth+this.mapWidth-200, 2, 120, 60);
				notArea.append("Selezionare la coordinata su cui eseguire lo spotlight\n");
				this.spotted = true;
			} else {
				Coordinates oldcoord = controller.getMyPosition();
				if (!hasMoved && card.toString().equals("teleport")){
					ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(controller.getMyPosition(), controller.getMyCharacter().getPercorrableDistance(),false);
					for (Coordinates coordinates:selectableCoordinates){	
						int i = coordinates.getNumber()-1;
						int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
						this.printSector(i,j,graphicMap[i][j],0);
					}	
				}
				ArrayList<String> command = new ArrayList<String>();
				command.add(card.toString());
				controller.callObjectCard(command);
				if (card.toString().equals("teleport")){
					int i = oldcoord.getNumber()-1;
					int j = Coordinates.getNumberFromLetter(oldcoord.getLetter());
					this.printSector(i,j,graphicMap[i][j],0);
					Coordinates newPos = controller.getMyPosition();
					i = newPos.getNumber()-1;
					j = Coordinates.getNumberFromLetter(newPos.getLetter());
					this.printSector(i,j,graphicMap[i][j],2);
					if (!hasMoved){
						ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(controller.getMyPosition(), controller.getMyCharacter().getPercorrableDistance(),false);
						for (Coordinates coordinates:selectableCoordinates){	
							i = coordinates.getNumber()-1;
							j = Coordinates.getNumberFromLetter(coordinates.getLetter());
							this.printSector(i,j,graphicMap[i][j],1);
						}
					}
				} 
				else if (card.toString().equals("adrenalin") && !this.hasMoved){
					ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(controller.getMyPosition(), 2,false);
					for (Coordinates coordinates:selectableCoordinates){	
						int i = coordinates.getNumber()-1;
						int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
						this.printSector(i,j,graphicMap[i][j],1);
					}
				}
					
			}
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	private void attack() {
		hasAttacked = true;
		ArrayList<String> command = new ArrayList<String>();
		command.add("attack");
		controller.callObjectCard(command);
		g.setColor(Color.BLACK);
		g.fillRect(this.mapMarginWidth+this.mapWidth-400, 2, 120, 68);
	}

	private void endTurn() {
		if (!isFinish)
			new Thread(this).start();
		else{
			controller.endMatch();
			this.returnToMenu();
			frame.printMatchesName();
		}
	}
	
	private void returnToMenu(){
		g.setColor(Color.BLACK);
		frame.removeMouseListener(this);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		frame.getContentPane().removeAll();
		frame.paintComponents(g);
	}

	private void selectRumorCoordinates(Coordinates coordinates) {
		if (map.getSector(coordinates) instanceof DangerousSector){
			this.playerRumors.start();
			this.selectany = false;
			this.selector.makeNoise(coordinates);
			if (controller.getMyCharacter().getBagSize()<4){
				if(!isHuman && !hasAttacked && hasMoved){
					this.showAttackButton();
				}
				this.showEndButton();
			}
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

	@Override
	public void showPickedCards(ArrayList<Card> pickedCards) {
		Card card = pickedCards.get(0);
		if (!card.toString().equals("nothing")){
			Image img = hashCard.get(card.toString());
			this.printSingleCard(img, 1, 1, imgx, imgy,0);
		}
		if (pickedCards.size()>1){
			card = pickedCards.get(1);
			Image img = hashCard.get(card.toString());
			if (controller.getMyCharacter().getBagSize()<4)
				this.printSingleCard(img,3,controller.getMyCharacter().getBagSize(),imgx,imgy,0);
			else
				this.printSingleCard(img,2,1,imgx,imgy,0);
		}
		if (!pickedCards.get(0).toString().equals("anySector")){
			if (pickedCards.get(0).toString().equals("yourSector")){
				notArea.append("Hai pescato la carta: rumore nel tuo settore\n");
				this.playerRumors.start();
			}else if (!card.toString().equals("nothing")){
				notArea.append("Hai pescato la carta: Silenzio\n");
				this.playerSilence.start();
			}
			if (controller.getMyCharacter().getBagSize()<4){
				if(!isHuman && !hasAttacked && hasMoved ){
					this.showAttackButton();
				}
				this.showEndButton();
			}
			
		}
		if (pickedCards.size()>1){
			notArea.append("Hai pescato una carta oggetto\n");
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}


	private void showEndButton() {
		g.drawImage(this.imgFinishTurn,this.mapMarginWidth+this.mapWidth-200, 2, 120, 60,null);
	}

	private void showAttackButton() {
		g.drawImage(this.imgAttackButton,this.mapMarginWidth+this.mapWidth-400, 2, 120, 60,null);
	}

	@Override
	public void chooseObjectCard() {
	}

	@Override
	public void chooseAnyCoordinates(WatcherNoiseCoordinatesSelector selector) {
		this.selectany = true;
		this.selector = selector;
		notArea.append("Hai pescato la carta: rumore in qualunque settore\n");
		notArea.append("Seleziona la coordinata dove fare rumore\n");
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void showEscapeResult(boolean result, Coordinates coord) {
		g.setFont(new Font(frame.getFontName(), Font.BOLD, 16));
		if(controller.isMyTurn())
			if (result){
				notArea.append("Sei riuscito a scappare!\n");
				notArea.append("La tua partita è finita\n");
			}else{
				notArea.append("Non sei riuscito a scappare,\n");
				notArea.append("Prova un'altra scialuppa!\n");
			}
		else{
			String currentPlayerUsername = controller.getCurrentPlayer();
			if (result){
				notArea.append("Il giocatore " + currentPlayerUsername + " è riuscito a scappare!\n");
			}else{
				notArea.append("Il giocatore " + currentPlayerUsername + " non è riuscito a scappare\n");
			}
		}
		posEscape = posShallop.indexOf(coord);
		if (result)
			sitShallop.set(posEscape, 1);
		else
			sitShallop.set(posEscape,2);
		
		this.printSector(coord.getNumber()-1, Coordinates.getNumberFromLetter(coord.getLetter()), SectorName.SHALLOP, 0);
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}
	
	private class PlayerHandler implements ControllerListener{
		private Player playerMusic;
		private MediaLocator mediaLocator;
		
		public PlayerHandler(MediaLocator mediaLocator){
			try {
				playerMusic = Manager.createPlayer(mediaLocator);
				this.mediaLocator = mediaLocator;
				playerMusic.addControllerListener(this);
			} catch (NoPlayerException | IOException e) {
			}
		}
		
		public void start(){
			playerMusic.start();
		}

		@Override
		public void controllerUpdate(ControllerEvent e) {
			 if (e instanceof EndOfMediaEvent)
	         {
				 try {
					playerMusic = Manager.createPlayer(mediaLocator);
				} catch (NoPlayerException e1) {
				} catch (IOException e1) {
				}
				 playerMusic.addControllerListener(this);
	         }
	     }


		
	}


	@Override
	public void showNoise(Coordinates noiseCoord) {
		SectorName[][] graphicMap = map.getGraphicMap();
		int i = noiseCoord.getNumber()-1;
		int j = Coordinates.getNumberFromLetter(noiseCoord.getLetter());
		if (i!=-1){
			this.playerRumors.start();
			this.rumorsCoordinates = noiseCoord;
			this.printSector(i,j,graphicMap[i][j],3);
			notArea.append("Hai sentito un rumore nella coordinata " + noiseCoord +"\n");
		} else{
			this.playerSilence.start();
			notArea.append("Silenzio!\n");
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	

	@Override
	public void showSpottedPlayer(String username, Coordinates position) {
		if (username==null)
			notArea.append("nessuno\n");
		else if (username.equals(controller.getMyPlayerName()))
			notArea.append("te\n");
		else{
			notArea.append(username + " nella posizione: " + position + "\n");
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	public void startRapresenting() {
		Character character = controller.getMyCharacter();
		if (character.getSide().equals(SideName.HUMAN))
			this.isHuman = true;
		else 
			this.isHuman = false;
		this.printCharacter(controller.getMyCharacter());
	}

	@Override
	public void showUsedCard(ObjectCard card, Coordinates coord) {
		notArea.append("è stata usata la carta: " + card.toString() + "\n");
		if (card.toString().equals("spotlight")){
			SectorName[][] graphicMap = map.getGraphicMap();
			int i = coord.getNumber()-1;
			int j = Coordinates.getNumberFromLetter(coord.getLetter());
			this.printSector(i,j,graphicMap[i][j],5);
			notArea.append("Spotlight nel settore " + coord.toString() + "giocatori:\n");
			this.spottedCoordinates.add(coord);
		}
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void printTurnNumber(int turnNumber) {
	}
	
	private void discardCard(ObjectCard objectCard) {
		controller.getSession().signalDiscardedObjectCard(objectCard);
		controller.getMyCharacter().getBag().remove(objectCard);
		this.discard =false;
		if (!selectany){
			this.showEndButton();
			if (!isHuman){
				this.showAttackButton();
			}
		}
		this.clearCard();
		g.setColor(Color.BLACK);
		g.fillRect(this.mapMarginWidth+this.mapWidth+imgx-20, this.mapMarginHeight + 2*(separate+60), imgx, imgy);
	}

	@Override
	public void selectObjectToDiscard() {
		g.setColor(Color.BLACK);
		g.fillRect(this.mapMarginWidth+this.mapWidth-200, 2, 120, 60);
		notArea.append("Hai troppe carte, seleziona una carta da scartare\n");
		this.discard = true;
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void notifyDiscardedObject() {
		g.setColor(Color.WHITE);
		g.setFont(new Font(frame.getFontName(), Font.BOLD, 16));
		notArea.append("Il giocatore ha scartato una carta!");
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

		
	public void changedTurn() {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.mapMarginWidth+this.mapWidth, this.mapMarginHeight);
		if (controller.isMyTurn())
			managesMyTurn();
		else
			notifyOthersTurn(controller.getCurrentPlayer());
	}

	@Override
	public void showMatchResults(String[] command) {
		notArea.append("\nLa partita è finita, I vincitori sono:\n");
		for(int i=1; i<command.length; i++){
			notArea.append(command[i] + "\n");
		}
		notArea.append("premi fine per uscire dalla partita\n");
		this.showEndButton();
		this.isFinish = true;
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void run() {
		try{
			controller.callEndTurn();
		} 
		catch (NoSuchElementException e){
			this.returnToMenu();
			controller.connectionProblem();
		}
		catch (NullPointerException e){
			this.returnToMenu();
			controller.connectionProblem();
		}
	}

	@Override
	public void showAttackCoordinates(Coordinates attackCoordinates) {
		this.attackCoordinates = attackCoordinates;
		SectorName[][] graphicMap = map.getGraphicMap();
		int i = attackCoordinates.getNumber()-1;
		int j = Coordinates.getNumberFromLetter(attackCoordinates.getLetter());
		this.printSector(i,j,graphicMap[i][j],4);
		this.nKill = 0;
		this.nSurvived = 0;
		notArea.append("Attacco nel settore " + attackCoordinates + "\n");
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void showKill(String username, String character) {
		this.playerAttack.start();
		if (nKill==0){
			notArea.append("Morti:\n");
		}
		if(username == null)
			notArea.append("Nessuno morto\n");
		else if(username.equals(controller.getMyPlayerName())){
			notArea.append("Sei stato ucciso\n");
			controller.getMyCharacter().setCurrentPosition(null);
		} else{
			notArea.append(username+", nel ruolo di " + character + "\n");
		}
		this.nKill++;
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void showSurvived(String username) {
		if (this.nSurvived==0){
			notArea.append("Difesi:\n");
		}
		if(username.equals(controller.getMyPlayerName())){
			notArea.append("Sei riuscito a difenderti!\n");
			clearDefenseCard();
		}else 
			notArea.append(username + "\n");
		this.nSurvived++;
		notArea.setCaretPosition(notArea.getDocument().getLength());
	}

	@Override
	public void showPlayerExit(String username, String character) {
		notArea.append("Il giocatore " + username + " nel ruolo di " + character +" è uscito dalla partita\n");
	}


}
