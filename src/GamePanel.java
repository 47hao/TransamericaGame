import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class GamePanel extends JPanel implements MouseInputListener {

	Game gameInfo;
	int xMousePos = 0;
	int yMousePos = 0;
	Point mousePos = new Point(xMousePos, yMousePos);

	BufferedImage map;

	Rail lastHovering;
	
	final Color railColor = new Color(30,30,30);
	public static final int railLength = 40;
	final int doubleSpacing = 2;
	final int shortLength;
	
	final boolean displayCoords = true;

	final static int gridStartX = 90;
	final static int gridStartY = 52;

	public GamePanel(Game game) {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		gameInfo = game;
		shortLength = (int) (railLength * 0.1);
		try {
			map = ImageIO.read(new File("img/map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Paints the components (board, grid, scoreboard, cityList, rails)
	public void paintComponent(Graphics g) {
		// System.out.println("repainted");
		drawBoard(g);
		drawGrid(g);
		drawScoreboard(g);
		
		  //for (Player p : gameInfo.getPlayers()) 
			  //drawTrain(g, p.score); 
		  //for (City c : gameInfo.getBoard().getCities()) 
			  //drawCity(g, newPoint(getPixelX(c.getPos().getX()), getPixelY(c.getPos().getY()))); 
		  //for (Player p : gameInfo.getPlayers()) 
			  //drawMarker(g, new Point(getPixelX(p.getMarkerPos().getX()), getPixelY(p.getMarkerPos().getY()))); 
		drawCityList(g, gameInfo.getBoard().getActivePlayer());

		for (Rail r : gameInfo.getBoard().getRails()) {
			drawRail((Graphics2D) g, r);
		}
		// draws board and game information
	}

	private void drawBoard(Graphics g) {
		g.drawImage(map, 0, 0, this.getWidth(), this.getHeight(), Color.black, null);
	}

	private void drawGrid(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (Rail r : gameInfo.getBoard().getRails()) {
			drawRail(g2d, r);
		}
	}

	private void drawRail(Graphics2D g, Rail rail) {
		g.setColor(railColor);
		Point p = gridToPixel(rail.startPos());
		Point p2 = gridToPixel(rail.endPos());

		// if (rail.startPos().equals(new Position(15, 8)) && rail.endPos().equals(new Position(15, 9))) {
		// 	//XXX: drawing hitbox
		// 	g.setColor(Color.GREEN);
		// 	g.drawPolygon(rail.getHitbox());
		// 	return;
		// }

		if (rail.getState() == Rail.EMPTY || rail.getState() == Rail.HOVERING) 
		{
			g.setStroke(new BasicStroke(Rail.THICKNESS));
			if (rail.isDouble()) {
				if (p.getY() == p2.getY())// horizontal rail
				{
					g.drawLine((int) p.getX(), (int) p.getY(), (int) (p.getX() + shortLength), (int) p.getY());
					g.drawLine((int) p2.getX(), (int) p2.getY(), (int) (p2.getX() - shortLength), (int) p.getY());
					g.drawLine((int) (int) (p2.getX() + shortLength +doubleSpacing ), (int) p.getY()+doubleSpacing, 
									(int) (p2.getX() - shortLength -doubleSpacing ), (int) p.getY()+doubleSpacing);
					g.drawLine((int) (int) (p2.getX() + shortLength +doubleSpacing ), (int) p.getY()-doubleSpacing, 
									(int) (p2.getX() - shortLength -doubleSpacing ), (int) p.getY()-doubleSpacing);
					
				} else if (p.getX() < p2.getX())// Southwest rail
				{
				} else {
				}
			} else {
				//XXX: drawing hitbox
				// g.drawPolygon(rail.getHitbox());
				g.drawLine((int) p.getX(), (int) p.getY(), (int) p2.getX(), (int) p2.getY());
			}
		} else if(rail.getState() == Rail.PLACED)
		{
			g.setStroke(new BasicStroke(Rail.THICKNESS*3f));
			g.drawLine((int) p.getX(), (int) p.getY(), (int) p2.getX(), (int) p2.getY());
		}
		if(rail.getState() == Rail.HOVERING)
		{
			g.setStroke(new BasicStroke(Rail.THICKNESS*3f));
			g.setColor(new Color(0,0,0,50));
			// g.setColor(Color.RED);

			//XXX: drawing hitbox
			// g.drawPolygon(rail.getHitbox());

			// System.out.println("HOVER RAIL");
			//XXX: normal drawing: 
			g.drawLine((int) p.getX(), (int) p.getY(), (int) p2.getX(), (int) p2.getY());
		}
		
		if(displayCoords)
		{
			g.drawString(rail.startPos().getX() + ", " + rail.startPos().getY(), (int)p.getX(), (int)p.getY());
		}

	}

	public static Point gridToPixel(Position pos) {
		// System.out.println(pos.getY());
		int yPos = (int) (pos.getY() * railLength * Math.cos(Math.toRadians(30))) + gridStartY;

		// System.out.println(yPos);
		int xPos = (pos.getX() * railLength) - (int) (0.5 * pos.getY() * railLength) + gridStartX;
		return new Point(xPos,yPos);
	}

	private void drawScoreboard(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int xPos = 60;
		double cellSize = 56.6666666667;
		g.setColor(Color.WHITE);
		g.fillRect(xPos, 20, 60, 30);
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(7));
		g.drawLine(60, 10, 60, 60);
		g2.setStroke(new BasicStroke(5));
		
		for (int i = 1; i < 12; i++) {
			xPos = 60 + (int)(i * cellSize);
			g.setColor(Color.WHITE);
			g.fillRect(xPos, 20, 59, 30);
			g.setColor(Color.BLACK);
			g.drawLine(xPos, 20, xPos, 50);
			g.drawString(i + "", xPos - (int)(cellSize / 2) - (g.getFontMetrics().stringWidth(i + "") / 2), 35);
		}
		g2.setStroke(new BasicStroke(5));
		g.drawLine(60, 20, this.getWidth() - 60, 20);
		g.drawLine(60, 50, this.getWidth() - 60, 50);
		
		xPos = (int) Math.round(xPos + cellSize) - 1;
		g.setColor(Color.WHITE);
		g.fillRect(xPos, 15, 40, 40);
		g.setColor(Color.BLACK);
		g.drawRect(xPos, 15, 40, 40);
		g.drawString(12 + "", xPos - (int)(cellSize / 2) - (g.getFontMetrics().stringWidth(12 + "") / 2), 35);
	}

	private void drawTrain(Graphics g, Player player) {
		int xPos = 60;
		BufferedImage train = null; //temo
		double cellSize = 56.6666666667;
		g.drawImage(train, xPos + (player.getScore() * (int)cellSize) - (train.getWidth() / 2), 45, 55, 25, Color.black, null);
	}

	private void drawCity(Graphics g, Point location) {
		
	}

	private void drawMarker(Graphics g, Player player) {
		g.drawOval(player.getMarkerPos().getX() - 5, player.getMarkerPos().getY() - 5, 10, 10);
	}

	private void drawCityList(Graphics g, Player player) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.fillRoundRect(0, this.getHeight() - 200, 125, 200, 50, 50);
		g2.setStroke(new BasicStroke(7));
		g.setColor(Color.BLACK);
		g.drawRoundRect(0, this.getHeight() - 200, 125, 200, 50, 50);
	}
	public void mouseMoved(MouseEvent e) {
		// System.out.println("moved");
		// xMousePos = e.getLocationOnScreen().x;
		// yMousePos = e.getLocationOnScreen().y;
		// mousePos = new Point(xMousePos, yMousePos);
		mousePos = e.getPoint();
		for (Rail r : gameInfo.getBoard().getRails()) {
			if (r.getHitbox().contains(mousePos)) {
				// System.out.println("on rail");
				if (lastHovering != null && !lastHovering.equals(r)) {
					lastHovering.setState(Rail.EMPTY);
				}
				lastHovering = r;
				r.setState(Rail.HOVERING);
				repaint();

				//stop before setting more than 1 to hovering
				return;
			}
		}
	}

	public Point clickPosition() {
		return mousePos;
	}

	public void mouseClicked(MouseEvent e) {
		// System.out.println("click");
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

	@Override
	public void mouseDragged(MouseEvent e) {

	}

}
