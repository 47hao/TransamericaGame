import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener {
	
	Game gameInfo;
	int xMousePos = 0;
	int yMousePos = 0;
	Point mousePos = new Point(xMousePos, yMousePos);
	
	BufferedImage map;
	
	final int railLength = 20;
	final int shortLength;
	
	final static int gridStartX = 360;
	final static int gridStartY = 40;
	
	public GamePanel(Game game) {
		gameInfo = game;
		shortLength = (int)(railLength*0.1);
		try {
			map = ImageIO.read(new File("img/map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		drawBoard(g);
		drawGrid(g);
		drawScoreboard(g);
		/*
		for (Player p : gameInfo.getPlayers())
			drawTrain(g, new Point(getPixelX(p.score), 50));
		for (City c : gameInfo.getBoard().getCities())
			drawCity(g, new Point(getPixelX(c.getPos().getX()), getPixelY(c.getPos().getY())));
		for (Player p : gameInfo.getPlayers())
			drawMarker(g, new Point(getPixelX(p.getMarkerPos().getX()), getPixelY(p.getMarkerPos().getY())));
		for (Rail r : gameInfo.getBoard().getRails())
			drawRails(g, new Point(getPixelX(r.startPos().getX()), getPixelY(r.startPos().getY())), new Point(getPixelX(r.endPos().getX()), getPixelY(r.endPos().getY())));
		*/
		drawCityList(g, gameInfo.getBoard().getActivePlayer());
		// draws board and game information
	}

	private void drawBoard(Graphics g) {
		g.drawImage(map, 0, 0, this.getWidth(), this.getHeight(), Color.black, null);
	}
	
	private void drawGrid(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		for(Rail r : gameInfo.getBoard().getRails())
		{
			
		}
	}
	
	private void drawRail(Graphics g, Rail rail) {
		g.setColor(Color.BLACK);
		Point p = gridToPixel(rail.startPos());
		Point p2 = gridToPixel(rail.endPos());
		
		if(rail.getState() == Rail.EMPTY)
		{
			if(rail.isDouble())
			{
				if(p.getY() == p2.getY())//horizontal rail
				{
					g.drawLine((int)p.getX(),(int)p.getY(),(int)(p.getX()+shortLength),(int)p.getY());
					g.drawLine((int)p2.getX(),(int)p2.getY(),(int)(p2.getX()-shortLength),(int)p.getY());
				} else if (p.getX() < p2.getX())//Southwest rail
				{
				} else {
				}
			} else {
				g.drawLine((int)p.getX(),(int)p.getY(),(int)p2.getX(),(int)p2.getY());
			}
		}
			
	}


	private void drawScoreboard(Graphics g) {

	}

	private void drawTrain(Graphics g, Point location) {
		
	}

	private void drawCity(Graphics g, Point location) {

	}

	private void drawMarker(Graphics g, Point location) {

	}

	
	private void drawCityList(Graphics g, Player p) {

	}
	
	
	private Point gridToPixel(Position pos) {
		int yPos = (int)(pos.getY()*railLength*Math.cos(60)) + gridStartY;
		int xPos = (pos.getX()*railLength) - (int)(0.5*pos.getY()*railLength) + gridStartX;
		return new Point();
	}
	
	public void mouseMoved(MouseEvent e) {
		xMousePos = e.getLocationOnScreen().x;
		yMousePos = e.getLocationOnScreen().y;
		mousePos = new Point(xMousePos, yMousePos);
	}

	public Point clickPosition() {
		return mousePos;
	}

	public void mouseClicked(MouseEvent e) {
		mouseMoved(e);
		clickPosition();
	}

	public void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
	
}
