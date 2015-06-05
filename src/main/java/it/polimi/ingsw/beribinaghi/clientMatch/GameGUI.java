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

import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * This class manages the gui in match
 *
 */
public class GameGUI implements GameInterface,MouseListener {
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
	private HashMap<String,Image> hashCharacter = new HashMap<String,Image>();
	private HashMap<String,Image> hashCard = new HashMap<String,Image>();
	private HashMap<String,Image> hashLogo = new HashMap<String,Image>();
	private Image imgAttackButton;
	private Image imgFinishTurn;
	private ArrayList<Coordinates> posShallop = new ArrayList<Coordinates>();
	private boolean hasMoved;
	private boolean selectany;
	private WatcherNoiseCoordinatesSelector selector;
	private boolean hasAttacked;
	private boolean isHuman;
	
	
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
			hashCard.put("attack", ImageIO.read(new File("media/objectCard/attack.png").toURI().toURL()));
			hashCard.put("teleport", ImageIO.read(new File("media/objectCard/teleport.png").toURI().toURL()));
			hashCard.put("sedatives", ImageIO.read(new File("media/objectCard/sedatives.png").toURI().toURL()));
			hashCard.put("spotlight", ImageIO.read(new File("media/objectCard/spotlight.png").toURI().toURL()));
			hashCard.put("defense", ImageIO.read(new File("media/objectCard/defense.png").toURI().toURL()));
			hashCard.put("adrenalin", ImageIO.read(new File("media/objectCard/adrenalin.png").toURI().toURL()));
			hashCard.put("silence", ImageIO.read(new File("media/sectorCard/silence.png").toURI().toURL()));
			hashCard.put("anySector", ImageIO.read(new File("media/sectorCard/noiseInEverySector.png").toURI().toURL()));
			hashCard.put("yourSector", ImageIO.read(new File("media/sectorCard/rumorsInYourSector.png").toURI().toURL()));
			hashLogo.put("character", ImageIO.read(new File("media/logoCard/characterLogo.png").toURI().toURL()));
			hashLogo.put("escape", ImageIO.read(new File("media/logoCard/escapeLogo.png").toURI().toURL()));
			hashLogo.put("object", ImageIO.read(new File("media/logoCard/objectLogo.png").toURI().toURL()));
			hashLogo.put("sector", ImageIO.read(new File("media/logoCard/sectorLogo.png").toURI().toURL()));
			imgAttackButton = ImageIO.read(new File("media/attack.png").toURI().toURL());
			imgFinishTurn = ImageIO.read(new File("media/end.png").toURI().toURL());
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
		this.hasMoved = false;
		this.hasAttacked = false;
		this.selectany = false;
		g.setColor(Color.WHITE);
		g.setFont(new Font(frame.getFontName(), Font.BOLD, 24));
		g.drawString("E' il tuo turno, turno numero " + controller.getTurnNumber(), 20,30);
		this.printSelectableCoordinates(this.controller.getMyCharacter().getCurrentPosition(),controller.getMyCharacter().getPercorrableDistance());
	}
	
	private void printSelectableCoordinates(Coordinates currentPosition, int percorrableDistance) {
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(currentPosition, percorrableDistance);
		SectorName[][] graphicMap = map.getGraphicMap();
		for (Coordinates coordinates:selectableCoordinates){	
			int i = coordinates.getNumber()-1;
			int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
			this.printSector(i,j,graphicMap[i][j],1);
		}
	}
	
	private void chooseMove(Coordinates coordinatesSelected) {
		ArrayList<Coordinates> selectableCoordinates = controller.getMap().getReachableCoordinates(controller.getMyPosition(), controller.getMyCharacter().getPercorrableDistance());
		if (selectableCoordinates.contains(coordinatesSelected)){
			hasMoved  = true;
			SectorName[][] graphicMap = map.getGraphicMap();
			Coordinates myOldCoordinates = controller.getMyPosition();
			this.printSector(myOldCoordinates.getNumber()-1,Coordinates.getNumberFromLetter(myOldCoordinates.getLetter()),graphicMap[myOldCoordinates.getNumber()-1][Coordinates.getNumberFromLetter(myOldCoordinates.getLetter())],0);
			for (Coordinates coordinates:selectableCoordinates){	
				int i = coordinates.getNumber()-1;
				int j = Coordinates.getNumberFromLetter(coordinates.getLetter());
				if (coordinates.equals(coordinatesSelected))
					this.printSector(i,j,graphicMap[i][j],3);
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

	private void printLogos() {
		this.printSingleCard(hashLogo.get("character"), 0, 0,separate,separate,(imgy-separate)/2);
		this.printSingleCard(hashLogo.get("sector"), 1, 0,separate, separate,(imgy-separate)/2);
		this.printSingleCard(hashLogo.get("escape"), 2, 0,separate, separate,(imgy-separate)/2);
		this.printSingleCard(hashLogo.get("object"), 3, 0,separate, separate,(imgy-separate)/2);
	}

	private void printSingleCard(Image img, int i,int j,int dimensionx,int dimensiony,int marginy) {
		if (i!=4)
			g.drawImage(img, this.mapMarginWidth+this.mapWidth+j*(dimensionx+20)-20, this.mapMarginHeight + i*(separate+60)+marginy,dimensionx,dimensiony, null);
		else
			g.drawImage(img, this.mapMarginWidth+this.mapWidth+j*(dimensionx+20)-20, this.mapMarginHeight + i*(separate+60)-60,dimensionx,dimensiony, null);
	}

	public void notifyOthersTurn(String playerTurn) {
		g.setColor(Color.WHITE);
		g.setFont(new Font(frame.getFontName(), Font.BOLD, 24));
		g.drawString("Turno di " + playerTurn + ", turno numero " + controller.getTurnNumber(), 20,30);
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
					printSector(i,j,graphicMap[i][j],3);
				else
					printSector(i,j,graphicMap[i][j],0);
			}
		}
	}

	private void printSector(int i, int j, SectorName sectorName, int type) {
		Image img;
		if (type==0)
			img = hashSector.get(sectorName);
		else if (type==1)
			img = hashSelectedSector.get(sectorName);
		else
			img = hashMySector.get(sectorName);
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
			Coordinates coordinates = new Coordinates(Coordinates.getLetterFromNumber(i),j+1);
			if (!posShallop.contains(coordinates))
				posShallop.add(coordinates);
			g.setColor(Color.WHITE);
			g.setFont(new Font(frame.getFontName(), Font.BOLD, 18));
			g.drawString(""+(posShallop.indexOf(coordinates)+1),mapMarginWidth+j*3*lw+2*lw-3 , mapMarginHeight+lh*(i*2+j%2)+lh+5);
		}	
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (controller.isMyTurn()){
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
				if (!hasMoved)
					this.chooseMove(new Coordinates(Coordinates.getLetterFromNumber(q),r+1));
				else if (selectany)
					this.selectRumordCoordinates(new Coordinates(Coordinates.getLetterFromNumber(q),r+1));
			}
		}
	}


	private void selectRumordCoordinates(Coordinates coordinates) {
		if (map.getSector(coordinates).equals(SectorName.DANGEROUS)){
			this.selectany = false;
			this.selector.makeNoise(coordinates);
			if(!isHuman && !hasAttacked && hasMoved){
				this.showAttackButton();
			}
			this.showEndButton();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
			this.printSingleCard(img,3,controller.getMyCharacter().getBagSize(),imgx,imgy,0);
		}
		if (!pickedCards.get(0).toString().equals("anySector")){
			if(!isHuman && !hasAttacked && hasMoved ){
				this.showAttackButton();
			}
			this.showEndButton();
		}
	}


	private void showEndButton() {
		g.drawImage(this.imgFinishTurn,this.mapMarginWidth+this.mapWidth-100, 15, 40, 40,null);
	}

	private void showAttackButton() {
		g.drawImage(this.imgAttackButton,this.mapMarginWidth+this.mapWidth-140, 15, 40, 40,null);
	}

	@Override
	public void chooseObjectCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chooseAnyCoordinates(WatcherNoiseCoordinatesSelector selector) {
		this.selectany = true;
		this.selector = selector;
	}

	@Override
	public void showEscapeResult(boolean result, Coordinates coord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showNoise(Coordinates noiseCoord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAttackResult(Coordinates attackCoordinates, ArrayList<String> killed, ArrayList<String> survived) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSpottedPlayer(String username, Coordinates position) {
		// TODO Auto-generated method stub
		
	}

	public void start() {
		Character character = controller.getMyCharacter();
		if (character.getSide().equals(SideName.HUMAN))
			this.isHuman = true;
		else 
			this.isHuman = false;
		this.printCharacter(controller.getMyCharacter());
	}

	@Override
	public void showUsedCard(ObjectCard card, Coordinates coord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printTurnNumber(int turnNumber) {
	}

	@Override
	public void selectObjectToDiscard() {
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyDiscardedObject() {
		
	}

		
	public void changedTurn() {
		if (controller.isMyTurn())
			managesMyTurn();
		else
			notifyOthersTurn(controller.getCurrentPlayer());
	}


}
