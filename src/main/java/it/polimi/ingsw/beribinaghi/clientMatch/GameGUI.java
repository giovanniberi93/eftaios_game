/**
 * 
 */
package it.polimi.ingsw.beribinaghi.clientMatch;

import it.polimi.ingsw.beribinaghi.clientSetup.GUI;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card;
import it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.ObjectCard;
import it.polimi.ingsw.beribinaghi.gameNames.SectorName;
import it.polimi.ingsw.beribinaghi.gameNames.SideName;
import it.polimi.ingsw.beribinaghi.mapPackage.Coordinates;
import it.polimi.ingsw.beribinaghi.mapPackage.Map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * This class manages the gui in match
 *
 */
public class GameGUI implements GameInterface,MouseListener {

	private GUI frame;
	private Graphics g;
	private int mapWidth;
	private int mapHeight;
	private int mapMarginWidth;
	private int mapMarginHeight;
	private Map map;
	private int lw,lh;
	private Image imgDangerous;
	private Image imgSafe;
	private Image imgHuman;
	private Image imgAlien;
	private Image imgShallop;
	private Image imgBlank;
	private ArrayList<Coordinates> posShallop = new ArrayList<Coordinates>();
	
	
	public GameGUI(GUI frame) {
		super();
		this.frame = frame;
		g = frame.getGraphics();
		printBegin();
		frame.addMouseListener(this);
	}
	
	private void printBegin() {
		mapWidth = frame.getWidth()/4*3;
		lw = mapWidth/(Map.WIDTH*3+1);
		lh =  (int) (lw*(Math.sqrt(3)));
		mapHeight = lh*2*Map.HEIGHT;
		mapMarginWidth = (frame.getWidth()-mapWidth)/2;
		mapMarginHeight = (frame.getHeight()-mapHeight)/2;
		try {
			imgDangerous = ImageIO.read(new File("media/dangerousSector.png").toURI().toURL());
			imgSafe = ImageIO.read(new File("media/safeSector.png").toURI().toURL());
			imgHuman = ImageIO.read(new File("media/humanBase.png").toURI().toURL());;
			imgAlien = ImageIO.read(new File("media/alienBase.png").toURI().toURL());;
			imgShallop = ImageIO.read(new File("media/shallopSector.png").toURI().toURL());;
			imgBlank = ImageIO.read(new File("media/blankSector.png").toURI().toURL());;
		} catch (IOException e) {
		}
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientMatch.GameInterface#setController(it.polimi.ingsw.beribinaghi.clientMatch.MatchController)
	 */
	@Override
	public void setController(MatchController matchController) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientMatch.GameInterface#printCharacter(java.lang.String, java.lang.String, it.polimi.ingsw.beribinaghi.gameNames.SideName)
	 */
	@Override
	public void printCharacter(String name, String role, SideName side) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientMatch.GameInterface#managesMyTurn()
	 */
	@Override
	public void managesMyTurn() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientMatch.GameInterface#notifyOthersTurn(java.lang.String)
	 */
	@Override
	public void notifyOthersTurn(String playerTurn) {
		// TODO Auto-generated method stub

	}

	
	
	
	@Override
	public void printMap(Map map, Coordinates myCoordinates) {
		int j;
		this.map = map;
		g.setColor(Color.WHITE);
		SectorName[][] graphicMap = map.getGraphicMap();
		for (int i=0;i<graphicMap.length;i++)
		{
			for (j=0;j<graphicMap[i].length;j++){
				printSector(i,j,graphicMap[i][j]);
			}
		}
	}

	private void printSector(int i, int j, SectorName sectorName) {
		Image img;
		Boolean letter = false;
		Boolean shallop = false;
		if (sectorName.equals(SectorName.DANGEROUS))
		{
			img = imgDangerous;
			letter = true;
		}
		else if (sectorName.equals(SectorName.SAFE))
		{
			img = imgSafe;
			letter = true;
		}
		else if (sectorName.equals(SectorName.ALIENBASE))
			img = imgAlien;
		else if (sectorName.equals(SectorName.HUMANBASE))
			img = imgHuman;
		else if (sectorName.equals(SectorName.SHALLOP))
		{
			this.posShallop.add(new Coordinates(Coordinates.getLetterFromNumber(i),j+1));
			img = imgShallop;
			shallop = true;
		}
		else 
			img = imgBlank;
		g.drawImage(img, mapMarginWidth+j*3*lw, mapMarginHeight+lh*(i*2+j%2), 4*lw, 2*lh, null);
		if (letter){
			String num = String.valueOf(i+1);
			if (num.length()==1)
				num = "0"+num;
			g.setColor(Color.GRAY);
			g.setFont(new Font(frame.getFontName(), Font.BOLD, 14));
			g.setFont(frame.getFont());
			g.drawString((Coordinates.getLetterFromNumber(j)+"" + num).toUpperCase(),mapMarginWidth+j*3*lw+2*lw-12 , mapMarginHeight+lh*(i*2+j%2)+lh+5);
		} else if (shallop){
			g.setColor(Color.WHITE);
			g.setFont(new Font(frame.getFontName(), Font.BOLD, 18));
			g.drawString(""+this.posShallop.size(),mapMarginWidth+j*3*lw+2*lw-3 , mapMarginHeight+lh*(i*2+j%2)+lh+5);
		}
			
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientMatch.GameInterface#manageSectorCard(it.polimi.ingsw.beribinaghi.decksPackage.cardsPackage.Card)
	 */
	@Override
	public void manageSectorCard(Card card) {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see it.polimi.ingsw.beribinaghi.clientMatch.GameInterface#manageUsedObjectCard(java.util.ArrayList)
	 */
	@Override
	public void manageUsedObjectCard(ArrayList<String> command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX()-this.mapMarginWidth;
		int y = e.getY()-this.mapMarginHeight;
	    int q = (int) (x/lw)/3;
	    int r = (int) (y/lh-q%2)/2;
	    if (x>0 && x<mapWidth && y>0 && y<mapHeight)
	    {
	    	if ((x/lw)%3==0) 
	    		if (((y/lh)%2==1 && q%2==0) || ((y/lh)%2==0 && q%2==1))
	    		{
	    			if ((float)(y%lh)/(x%lw)>Math.sqrt(3))
	    			{
	    				q--;
	    				r+=1-q%2;
	    			}
	    		}
	    		else{
	    			if ((float)(y%lh)/(x%lw)>Math.sqrt(3))
	    			{
	    				q--;
	    				r-=q%2;
	    			}	
	    		}
	    	JOptionPane.showMessageDialog(null,Coordinates.getLetterFromNumber(q) + " " + (r*1+1));
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void chooseObjectCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Coordinates chooseAnyCoordinates() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void showUsedCard(ObjectCard usedCard, Coordinates destinationCoord) {
		// TODO Auto-generated method stub
		
	}

}
