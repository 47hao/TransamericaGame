import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.text.AttributeSet.ColorAttribute;

public class GamePanel extends JPanel implements MouseInputListener {

	Game gameInfo;

	BufferedImage map;

	Ellipse2D outlinedPoint;

	Rail clickedRail;

	Rail lastHovering;
	String previousState;

	final static int resolutionWidth = 1200;
	final static int resolutionHeight = 900;

	// rail display details
	final Color railColor = new Color(0, 0, 0);
	public static final int railLength = 60;
	final static float railThickness = 2f;
	final int doubleSpacing = 3;
	final int shortLength = 6;

	final static int gridStartX = 120;
	final static int gridStartY = 70;

	// universal colors
	public final static Color Red = new Color(200, 35, 25);
	public final static Color Blue = new Color(15, 90, 220);
	public final static Color Green = new Color(10, 200, 0);
	public final static Color Yellow = new Color(235, 190, 0);
	public final static Color Orange = new Color(230, 120, 0);
	public final static Color Purple = new Color(125, 60, 190);
	// final int[] starPointsX = { 0, 8, 5, 13, 21, 18, 26, 16, 13, 10, 0 };
	// final int[] starPointsY = { 10, 15, 25, 19, 25, 15, 10, 10, 0, 10, 10 };
	// final int[] starPointsX = { 0, 4, 2, 7, 11, 9, 13, 8, 7, 5, 0 };
	// final int[] starPointsY = { 5, 8, 13, 9, 13, 8, 5, 5, 0, 5, 5 };

	// marker parameters
	// final int outerMarkerDiam = 18;
	// final int innerMarkerDiam = 14;

	final double starScale = 1.8;
	final int[] starTemplateX = { 0, 5, 3, 8, 12, 10, 14, 9, 8, 6, 0 };
	final int[] starTemplateY = { 6, 9, 15, 10, 14, 9, 6, 6, 0, 6, 6 };

	final int[] starPointsX;
	final int[] starPointsY;
	int starRangeX;
	int starRangeY;

	// city parameters
	final int cityInnerDiam = 18;
	final int cityStrokeDiam = 32;
	final int cityStroke = 4;

	final boolean displayCoords = true;

	final int labelOffsetY = 15;
	final int labelOffsetX = 15;
	final int labelHeight = 22;
	final float labelStroke = 1f;
	final int labelFontSize = 16;

	// these keep track of the pulsing colored rails
	private int pulse;
	private int direction;
	boolean once = true;

	public GamePanel(Game game) {
		pulse = 1;
		direction = 1;

		gameInfo = game;

		// initializing geometry coords for player markers
		starPointsX = new int[starTemplateX.length];
		starPointsY = new int[starTemplateY.length];

		for (int i = 0; i < starTemplateX.length; i++) // scaling star coords based on template
		{
			starPointsX[i] = (int) (starTemplateX[i] * starScale);
			starPointsY[i] = (int) (starTemplateY[i] * starScale);
		}

		int xMax = 0;
		for (int i = 0; i < starPointsX.length - 1; i++) {
			xMax = Math.max(xMax, starPointsX[i]);
		}
		int yMin = 100; // arbitrary large value
		int yMax = 0;
		for (int i = 0; i < starPointsY.length; i++) {
			yMin = Math.min(yMin, starPointsY[i]);
			yMax = Math.max(yMax, starPointsY[i]);
		}
		starRangeX = xMax;
		starRangeY = yMax - yMin;

		addMouseListener(this);
		addMouseMotionListener(this);

		try {
			map = ImageIO.read(new File("img/map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			for (;;) {
				pulse += direction;
				if (pulse >= 255 || pulse <= 0) {
					direction *= -1;
					// pulse += direction;
				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}
		}).start();
	}

	// Paints the components (board, grid, scoreboard, cityList, rails)
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// System.out.println("repainted");
		Graphics2D g2d = (Graphics2D) g;

		drawBoard(g);
		drawGrid(g);
		drawScoreboard(g);

		// TODO: draw trains on scoreboard
		// for (Player p : gameInfo.getPlayers()) {
		// drawTrain(g, p);
		// }

		// for (Player p : gameInfo.getPlayers())
		// drawTrain(g, p.score);
		// for (City c : gameInfo.getBoard().getCities())
		// drawCity(g, newPoint(getPixelX(c.getPos().getX()),
		// getPixelY(c.getPos().getY())));
		// for (Player p : gameInfo.getPlayers())
		// drawMarker(g, new Point(getPixelX(p.getMarkerPos().getX()),
		// getPixelY(p.getMarkerPos().getY())));

		drawCityList(g, gameInfo.getCurrentPlayer());

		for (Rail r : gameInfo.getBoard().getRails()) {
			drawRail((Graphics2D) g, r);
		}

		// for (Player p : gameInfo.getPlayers()) {
		// boolean active = p.equals(gameInfo.getcurrentPlayer());
		// for (City c : p.getTargetCities()) {
		// drawCity(g2d, c, active);
		// }
		// }

		drawCities(g, gameInfo.getBoard().getCities());
		for (City c : gameInfo.getCurrentPlayer().getTargetCities()) {
			drawCity(g2d, c, true);
		}

		// draws board and game information

		// g2d.setColor(Color.RED);
		// for (Ellipse2D e : gameInfo.getBoard().getPositionHitboxes()) {
		// g2d.fill(e);
		// // System.out.println("(" + e.getX() + ", " + e.getY() + ")");
		// // System.out.println("width: " + e.getWidth() + " height: " +
		// e.getHeight());
		// }

		for (Player player : gameInfo.getPlayers()) {
			drawMarker((Graphics2D) g, player);
		}

		if (outlinedPoint != null) {
			g2d.setColor(Color.WHITE);
			g2d.setStroke(new BasicStroke(1.5f));
			g2d.draw(outlinedPoint);
		}

		// g2d.setColor(Color.GREEN);
		// g2d.draw(new Polygon(starPointsX, starPointsY, 11));
	}

	private void drawBoard(Graphics g) {
		g.drawImage(map, 0, 0, getWidth(), getHeight(), Color.black, null);
	}

	private void drawGrid(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (Rail r : gameInfo.getBoard().getRails()) {
			drawRail(g2d, r);
		}
	}

	private void drawRail(Graphics2D g, Rail rail) {
		g.setColor(railColor);

		for (ArrayList<Rail> railList : gameInfo.getRecentRails()) {
			for (Rail r : railList) {
				if (rail.equals(r)) {
					// the rail was recently placed
					g.setColor(new Color(pulse, pulse, pulse));
				}
			}
		}

		Point p = gridToPixel(rail.startPos());
		Point p2 = gridToPixel(rail.endPos());

		// if (rail.startPos().equals(new Position(15, 8)) && rail.endPos().equals(new
		// Position(15, 9))) {
		// //XXX: drawing hitbox
		// g.setColor(Color.GREEN);
		// g.drawPolygon(rail.getHitbox());
		// return;
		// }
		// if (rail.getState().equals(Rail.PLACED)) {
		// System.out.println("placed rail found");
		// }

		// if (gameInfo.getCurrentPlayer().getValidRails().contains(rail)) {
		// g.setColor(Color.WHITE);
		// }

		if (rail.getState().equals(Rail.EMPTY) || rail.getState().equals(Rail.HOVERING)) {
			// System.out.println("empty/hovering");
			g.setStroke(new BasicStroke(railThickness));
			if (rail.isDouble()) {
				if (p.getY() == p2.getY())// horizontal rail
				{
					g.drawLine((int) p.getX(), (int) p.getY(), (int) (p.getX() + shortLength), (int) p.getY());
					g.drawLine((int) p2.getX(), (int) p2.getY(), (int) (p2.getX() - shortLength), (int) p.getY());
					g.drawLine((int) (p.getX() + shortLength + doubleSpacing), (int) p.getY() + doubleSpacing,
							(int) (p2.getX() - shortLength - doubleSpacing), (int) p.getY() + doubleSpacing);
					g.drawLine((int) (p.getX() + shortLength + doubleSpacing), (int) p.getY() - doubleSpacing,
							(int) (p2.getX() - shortLength - doubleSpacing), (int) p.getY() - doubleSpacing);

				} else if (p.getX() < p2.getX())// Southeast rail
				{
					double cos60 = Math.cos(Math.toRadians(60));
					double sin60 = Math.sin(Math.toRadians(60));
					g.drawLine((int) p.getX(), (int) p.getY(), (int) (p.getX() + shortLength * cos60),
							(int) (p.getY() + shortLength * sin60));
					g.drawLine((int) p2.getX(), (int) p2.getY(), (int) (p2.getX() - shortLength * cos60),
							(int) (p2.getY() - shortLength * sin60));
					g.drawLine((int) (p.getX() + shortLength * cos60 + doubleSpacing * sin60),
							(int) (p.getY() + shortLength * sin60 - doubleSpacing * cos60),
							(int) (p2.getX() - shortLength * cos60 + doubleSpacing * sin60),
							(int) (p2.getY() - shortLength * sin60 - doubleSpacing * cos60));
					g.drawLine((int) (p.getX() + shortLength * cos60 - doubleSpacing * sin60),
							(int) (p.getY() + shortLength * sin60 + doubleSpacing * cos60),
							(int) (p2.getX() - shortLength * cos60 - doubleSpacing * sin60),
							(int) (p2.getY() - shortLength * sin60 + doubleSpacing * cos60));
				} else {
					double cos60 = Math.cos(Math.toRadians(60));
					double sin60 = Math.sin(Math.toRadians(60));
					g.drawLine((int) p.getX(), (int) p.getY(), (int) (p.getX() - shortLength * cos60),
							(int) (p.getY() + shortLength * sin60));
					g.drawLine((int) p2.getX(), (int) p2.getY(), (int) (p2.getX() + shortLength * cos60),
							(int) (p2.getY() - shortLength * sin60));
					g.drawLine((int) (p.getX() - shortLength * cos60 - doubleSpacing * sin60),
							(int) (p.getY() + shortLength * sin60 - doubleSpacing * cos60),
							(int) (p2.getX() + shortLength * cos60 - doubleSpacing * sin60),
							(int) (p2.getY() - shortLength * sin60 - doubleSpacing * cos60));
					g.drawLine((int) (p.getX() - shortLength * cos60 + doubleSpacing * sin60),
							(int) (p.getY() + shortLength * sin60 + doubleSpacing * cos60),
							(int) (p2.getX() + shortLength * cos60 + doubleSpacing * sin60),
							(int) (p2.getY() - shortLength * sin60 + doubleSpacing * cos60));
				}
			} else {
				// XXX: drawing hitbox
				// g.setColor(Color.WHITE);
				// if (gameInfo.getCurrentPlayer().getValidRails().contains(rail)) {
				// g.drawPolygon(rail.getHitbox());
				// }
				// g.setColor(Color.BLACK);
				g.drawLine((int) p.getX(), (int) p.getY(), (int) p2.getX(), (int) p2.getY());
			}
		} else if (rail.getState().equals(Rail.PLACED)) {
			// g.setColor(Color.RED);
			g.setStroke(new BasicStroke(railThickness * 3f));
			g.drawLine((int) p.getX(), (int) p.getY(), (int) p2.getX(), (int) p2.getY());
			// g.setColor(Color.BLACK);
		}
		if (rail.getState().equals(Rail.HOVERING)) {
			g.setStroke(new BasicStroke(railThickness * 3f));
			g.setColor(new Color(255, 255, 255, 50));
			// g.setColor(Color.RED);

			// XXX: drawing hitbox
			// g.drawPolygon(rail.getHitbox());

			// System.out.println("HOVER RAIL");
			// XXX: normal drawing:
			g.drawLine((int) p.getX(), (int) p.getY(), (int) p2.getX(), (int) p2.getY());
		}

		if (displayCoords) {
			g.drawString(rail.startPos().getX() + ", " + rail.startPos().getY(), (int) p.getX(), (int) p.getY());
		}

	}

	public static Point gridToPixel(Position pos) {
		// System.out.println(pos.getY());
		int yPos = (int) (pos.getY() * railLength * Math.cos(Math.toRadians(30))) + gridStartY;

		// System.out.println(yPos);
		int xPos = (pos.getX() * railLength) - (int) (0.5 * pos.getY() * railLength) + gridStartX;
		return new Point(xPos, yPos);
	}

	private void drawScoreboard(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int xPos = 60;
		int yPos = 15;
		int cellSize = 87;
		List<List<Player>> trains = new ArrayList<List<Player>>(13);
		
		/*
		if (once) {
			for (Player p : gameInfo.getPlayers()) {
				p.addScore((int) (Math.random() * 13));
			}
			once = false;
		}
		*/

		// Track how many players are in the same spot and offset them
		trains = new ArrayList<List<Player>>(13);
		for (int i = 0; i < 13; i++)
			trains.add(new ArrayList<Player>());
		for (Player p : gameInfo.getPlayers()) {
			p.setOffset(0);
			trains.get(p.getScore()).add(p);
		}

		for (int i = 0; i < trains.size(); i++) {
			List<Player> playersEdit = trains.get(i);
			int players = trains.get(i).size();
			for (int j = 0; j < players; j++)
				playersEdit.get(j).setLarge(false);
			if (i == 0) {
				if (players != 1) {
					if (players == 2) {
						playersEdit.get(0).setOffset(-5);
						playersEdit.get(0).setLarge(true);
						playersEdit.get(1).setOffset(5);
						playersEdit.get(1).setLarge(true);
					} else if (players == 3) {
						playersEdit.get(0).setOffset(-6);
						playersEdit.get(1).setOffset(0);
						playersEdit.get(2).setOffset(6);
					} else if (players == 4) {
						playersEdit.get(0).setOffset(-6);
						playersEdit.get(1).setOffset(-2);
						playersEdit.get(2).setOffset(2);
						playersEdit.get(3).setOffset(6);
					} else if (players == 5) {
						playersEdit.get(0).setOffset(-10);
						playersEdit.get(1).setOffset(-5);
						playersEdit.get(2).setOffset(0);
						playersEdit.get(3).setOffset(5);
						playersEdit.get(4).setOffset(10);
					} else if (players == 6) {
						playersEdit.get(0).setOffset(-10);
						playersEdit.get(1).setOffset(-6);
						playersEdit.get(2).setOffset(-2);
						playersEdit.get(3).setOffset(2);
						playersEdit.get(4).setOffset(6);
						playersEdit.get(5).setOffset(10);
					}
				} else
					playersEdit.get(0).setLarge(true);
			} else {
				if (players != 1) {
					if (players == 2) {
						playersEdit.get(0).setOffset(-10);
						playersEdit.get(0).setLarge(true);
						playersEdit.get(1).setOffset(10);
						playersEdit.get(1).setLarge(true);
					} else if (players == 3) {
						playersEdit.get(0).setOffset(-12);
						playersEdit.get(1).setOffset(0);
						playersEdit.get(2).setOffset(12);
					} else if (players == 4) {
						playersEdit.get(0).setOffset(-12);
						playersEdit.get(1).setOffset(-4);
						playersEdit.get(2).setOffset(4);
						playersEdit.get(3).setOffset(12);
					} else if (players == 5) {
						playersEdit.get(0).setOffset(-20);
						playersEdit.get(1).setOffset(-10);
						playersEdit.get(2).setOffset(0);
						playersEdit.get(3).setOffset(10);
						playersEdit.get(4).setOffset(20);
					} else if (players == 6) {
						playersEdit.get(0).setOffset(-20);
						playersEdit.get(1).setOffset(-12);
						playersEdit.get(2).setOffset(-4);
						playersEdit.get(3).setOffset(4);
						playersEdit.get(4).setOffset(12);
						playersEdit.get(5).setOffset(20);
					}
				} else
					playersEdit.get(0).setLarge(true);
			}
		}

		// Draw final box
		g2d.setColor(Color.WHITE);
		g2d.fillRect(xPos, yPos, cellSize, 30);
		g2d.setColor(Color.BLACK);
		for (Player p : gameInfo.getPlayers()) {
			if (p.getScore() == 12)
				drawTrain(g2d, p, xPos + p.getOffset(), yPos, p.getLarge());
		}

		// Draw thicker line beyond final box
		g2d.setStroke(new BasicStroke(7));
		g2d.drawLine(60, yPos - 5, 60, yPos + 35);

		// Reset stroke size
		g2d.setStroke(new BasicStroke(5));

		// draw boxes 1 - 12
		for (int i = 1; i < 12; i++) {
			xPos = 60 + (int) (i * cellSize);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(xPos, yPos, cellSize, 30);
			g2d.setColor(Color.BLACK);
			g2d.drawLine(xPos, yPos, xPos, yPos + 30);
			for (Player p : gameInfo.getPlayers()) {
				if (p.getScore() == 12 - i)
					drawTrain(g2d, p, xPos + p.getOffset(), yPos, p.getLarge());
			}
			g2d.drawString(i + "", xPos - (cellSize / 2) - (g.getFontMetrics().stringWidth(i + "") / 2), yPos + 20);
		}

		// Draw lines above and below scoreboard
		g2d.setStroke(new BasicStroke(5));
		g2d.drawLine(60, yPos, getWidth() - cellSize, yPos);
		g2d.drawLine(60, yPos + 30, getWidth() - cellSize, yPos + 30);

		// Draw station (blank square)
		xPos = (int) Math.round(xPos + cellSize) - 1;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(xPos, yPos - 5, 40, 40);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(xPos, yPos - 5, 40, 40);
		for (Player p : gameInfo.getPlayers()) {
			if (p.getScore() == 0)
				drawTrain(g2d, p, xPos, yPos + p.getOffset(), p.getLarge());
		}

		// Draw label for the 12 box (cause it doesn't draw properly)
		g2d.drawString(12 + "", xPos - (cellSize / 2) - (g.getFontMetrics().stringWidth(12 + "") / 2), yPos + 20);
	}

	private void drawTrain(Graphics g, Player player, int xPos, int yPos, boolean big) {
		int cellSize = 87;
		BufferedImage train = null;
		
		try {
			train = ImageIO.read(new File("img/train.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int height = train.getHeight();
		int width = train.getWidth();
		
		//change train color
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((train.getRGB(x, y) >> 24) != 0x00)
					train.setRGB(x, y, player.getColor().getRGB());
			}
		}
		
		//scale train according to number of trains
		if (big) {
			if (player.getScore() == 0) {
				g.drawImage(train, xPos + (width / 8) - 2, yPos + 9, width / 2, height / 2, null, null);
			} else {
				g.drawImage(train, xPos + (cellSize / 2) - width / 2, yPos + 3, width,
						height, null, null);
			}
		} else {
			if (player.getScore() == 0) {
				g.drawImage(train, xPos + (width / 8) - 2, yPos + 9, width / 2, height / 2, null, null);
			} else {
				g.drawImage(train, xPos + (cellSize / 2) - (width / 3), yPos + 8, (int)(width / 1.5),
						(int)(height / 1.5), null, null);
			}
		}
	}

	private void drawCities(Graphics g, City[] cityList, Player activePlayer) {
		Graphics2D g2d = (Graphics2D) g;
		for (City c : cityList) {
			if (activePlayer.getTargetCities().contains(c)) // POTENTIAL ERROR IN CONTAINS
				drawCity(g2d, c, true);
			else
				drawCity(g2d, c, false);
		}
	}

	private void drawCities(Graphics g, City[] cityList) // without player
	{
		Graphics2D g2d = (Graphics2D) g;
		for (City c : cityList) {
			drawCity(g2d, c, false);
		}
	}

	private void drawCity(Graphics2D g2d, City c, boolean active) {
		Point pixelLoc = gridToPixel(c.getPos());
		g2d.setColor(c.getColor());
		g2d.setStroke(new BasicStroke(cityStroke));
		int strokeOffset = cityStrokeDiam - cityStroke;
		if (active)
			g2d.drawOval((int) (pixelLoc.getX() - strokeOffset / 2), (int) (pixelLoc.getY() - strokeOffset / 2),
					strokeOffset, strokeOffset);
		g2d.fillOval((int) (pixelLoc.getX() - cityInnerDiam / 2), (int) (pixelLoc.getY() - cityInnerDiam / 2),
				cityInnerDiam, cityInnerDiam);
		g2d.setStroke(new BasicStroke(railThickness));
		g2d.setColor(railColor);
		g2d.drawOval((int) (pixelLoc.getX() - cityInnerDiam / 2), (int) (pixelLoc.getY() - cityInnerDiam / 2),
				cityInnerDiam, cityInnerDiam);

		drawCityLabel(g2d, c);
	}

	private void drawCityLabel(Graphics g2d, City c) {
		Point pixelLoc = gridToPixel(c.getPos());

		pixelLoc.x -= labelOffsetX;
		pixelLoc.y += labelOffsetY;
		g2d.setColor(Color.white);
		g2d.fillRoundRect((int) pixelLoc.getX(), (int) pixelLoc.getY(),
				c.getName().length() * labelFontSize / 2 + labelHeight, labelHeight, labelHeight / 4, labelHeight / 4);
		g2d.setColor(railColor);
		g2d.setFont(new Font("Arial", Font.BOLD, labelFontSize));
		g2d.drawString(c.getName(), (int) (pixelLoc.getX() + (labelFontSize / 2)),
				(int) (pixelLoc.getY() + labelHeight / 2 + labelFontSize / 3));
	}

	private void drawMarker(Graphics2D g2d, Player player) {
		if (player.getMarkerPos() != null) {
			Point p = gridToPixel(player.getMarkerPos());
			g2d.setColor(player.getColor());
			// g.drawOval((int) (p.getX() - outerMarkerDiam/2), (int) (p.getY() -
			// outerMarkerDiam/2), outerMarkerDiam,outerMarkerDiam);

			g2d.setColor(Color.white);
			// g.fillOval((int) (p.getX() - innerMarkerDiam/2), (int) (p.getY() -
			// innerMarkerDiam/2), innerMarkerDiam,innerMarkerDiam);

			g2d.setColor(player.getColor());
			int[] markerPointsX = new int[starPointsX.length];
			int[] markerPointsY = new int[starPointsY.length];
			for (int i = 0; i < markerPointsX.length; i++) {
				markerPointsX[i] = (int) p.getX() - (starRangeX / 2) + starPointsX[i];
				markerPointsY[i] = (int) p.getY() - (starRangeY / 2) + starPointsY[i];
			}

			g2d.fillPolygon(markerPointsX, markerPointsY, markerPointsX.length);
			g2d.setStroke(new BasicStroke(railThickness / 2));
			g2d.setColor(railColor);
			g2d.drawPolygon(markerPointsX, markerPointsY, markerPointsX.length);
		}
	}

	private void drawCityList(Graphics g, Player player) {
		Graphics2D g2d = (Graphics2D) g;
		int width = 125;
		int height = 175;
		int arc = 50;

		g2d.setColor(Color.WHITE);
		g2d.fillRoundRect(0, getHeight() - height, width, height, arc, arc);
		g2d.setStroke(new BasicStroke(5f));
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(0, getHeight() - height, width, height, arc, arc);

		// draw "Cities" label
		Font f = g2d.getFont();
		g2d.setFont(new Font("Arial", Font.BOLD, 18));
		g2d.drawString("Cities", width / 3, getHeight() - 150);

		if (player != null) {

			// draw active player's name
			g2d.setColor(player.getColor());
			g2d.setFont(new Font("Arial", Font.BOLD, 12));
			g2d.drawString(player.getName(), width / 3, getHeight() - 135);

			// draw the list
			g2d.setColor(Color.BLACK);
			g2d.setFont(f);

			if (player.getTargetCities() != null) {
				for (int i = 0; i < player.getTargetCities().size(); i++) {
					g2d.drawString(player.getTargetCities().get(i).getName(), 7, getHeight() - 120 + (25 * i));
					// TODO: draw the distances
					g2d.drawString(
							"" + (player.getDistancesToCities() == null ? "-" : player.getDistancesToCities()[i]),
							width - 20, getHeight() - 120 + (25 * i));
				}
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
		// FIXME: there's a bug where rails stay stuck as hovering
		// try moving the mouse very quickly over the first diagonal right rail
		// (leftmost, near california)
		System.out.println("lastHovering: " + lastHovering);
		System.out.println("(" + e.getPoint().getX() + ", " + e.getPoint().getY() + ")");

		if (gameInfo.getBoard().getGameState().equals(Board.GS_GAME_END)
				|| gameInfo.getBoard().getGameState().equals(Board.GS_GAME_END)) {
			// disallow user input to the board on round end/game end
			return;
		} else if (gameInfo.getBoard().getGameState().equals(Board.GS_MARKER)) {
			ArrayList<Ellipse2D> hitboxes = gameInfo.getBoard().getPositionHitboxes();
			for (Ellipse2D h : hitboxes) {
				if (h.contains(e.getPoint())) {
					outlinedPoint = h;
					repaint();
					return;
				}
			}
			outlinedPoint = null;
		} else if (gameInfo.getBoard().getGameState().equals(Board.GS_ROUND)) {
			for (Rail r : gameInfo.getBoard().getRails()) {
				if (r.getHitbox().contains(e.getPoint())) {
					if (r.getState().equals(Rail.EMPTY)) {
						lastHovering = r;
						lastHovering.setState(Rail.HOVERING);
					}
					repaint();
					System.out.println("hitbox contained");
					return;
				}
				if (lastHovering != null && !lastHovering.equals(r) && lastHovering.getState().equals(Rail.HOVERING)) {
					lastHovering.setState(Rail.EMPTY);
					System.out.println("reset1");
				}
			}

			if (lastHovering != null && lastHovering.getState().equals(Rail.HOVERING)) {
				lastHovering.setState(Rail.EMPTY);
				System.out.println("reset2");
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		synchronized (gameInfo) {
			if (gameInfo.getBoard().getGameState().equals(Board.GS_GAME_END)
					|| gameInfo.getBoard().getGameState().equals(Board.GS_ROUND_END)) {
				// no user input
				return;
			} else if (gameInfo.getBoard().getGameState().equals(Board.GS_MARKER)) {
				for (Ellipse2D ellipse : gameInfo.getBoard().getPositionHitboxes()) {
					if (ellipse.contains(e.getPoint())) {
						gameInfo.getCurrentPlayer().setMarkerPos(gameInfo.getBoard().getPositions()
								.get(gameInfo.getBoard().getPositionHitboxes().indexOf(ellipse)));
						gameInfo.notify();
					}
				}
				repaint();
			} else if (gameInfo.getBoard().getGameState().equals(Board.GS_ROUND)) {
				for (Rail r : gameInfo.getBoard().getRails()) {
					// IFF the rail is in the list of possible rails for the current player and
					// the rail's state is empty or hovering (not placed) and
					// the click is within the rail's boundary
					if (gameInfo.getCurrentPlayer().getValidRails().contains(r)
							&& (r.getState().equals(Rail.EMPTY) || r.getState().equals(Rail.HOVERING))
							&& r.getHitbox().contains(e.getPoint())) {
						clickedRail = r;
						clickedRail.setState(Rail.PLACED);
						gameInfo.getTurnRails().add(r);
						gameInfo.notify();
						repaint();
						return;
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {

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

	public void clearOutlinedPoint() {
		outlinedPoint = null;
	}

}
