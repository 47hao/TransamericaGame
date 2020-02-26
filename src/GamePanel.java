import java.awt.Color;
import java.awt.Graphics;
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
	
	public GamePanel(Game g) {
		gameInfo = g;
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

	void drawBoard(Graphics g) {
		g.drawImage(map, 0, 0, this.getWidth(), this.getHeight(), Color.black, null);
	}
	
	void drawGrid(Graphics g) {

	}

	void drawScoreboard(Graphics g) {

	}

	void drawTrain(Graphics g, Point location) {
		
	}

	void drawCity(Graphics g, Point location) {

	}

	void drawMarker(Graphics g, Point location) {

	}

	void drawRails(Graphics g, Point startPos, Point endPos) {
		// Looks at states, draws rails
	}

	void drawCityList(Graphics g, Player p) {

	}
	
	/*
	int getPixelX(int x) {
		// Returns x in pixel (screen space) position
	}

	int getPixelY(int y) {
		// Returns y in pixel (screen space) position
	}

	Point pixelLoc(Position p) {
		// Returns the x and y point in terms of screen space pixels
	}
	*/
	
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
