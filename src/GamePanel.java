import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener {
	
	int xMousePos = 0;
	int yMousePos = 0;
	Point mousePos = new Point(xMousePos, yMousePos);
	
	void paintComponent() {
		// draws board and game information
	}

	void drawGrid() {

	}

	void drawScoreboard() {

	}

	void drawTrain(Point location) {

	}

	void drawCities(Player p) {

	}

	void drawMarkers(Player[] pArray, Player p) {

	}

	void drawRails(Rail[] rails) {
		// Looks at states, draws rails
	}

	void drawInterface(Player p) {

	}

	int getPixelX(int x) {
		// Returns x in pixel (screen space) position
	}

	int getPixelY(int y) {
		// Returns y in pixel (screen space) position
	}

	Point pixelLoc(Position p) {
		// Returns the x and y point in terms of screen space pixels
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
