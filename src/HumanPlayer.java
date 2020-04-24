import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player {

	public HumanPlayer(String n, Color c) {
		super(n, c);
	}

	public String getName() {
		return name;
	}

	public void addScore(int amount) {
		score += amount;
	}

	public int getScore() {
		return score;
	}

	// public void setDistanceToCity(City c, int distance) {
	// distancesToCities.set(targetCities.indexOf(c), distance);
	// distanc
	// }

	Position getMarker(Board b, ArrayList<Position> otherMarkers) {
		return null;
	}

	int getRail(Rail[] rails, Board b) {
		// TODO Auto-generated method stub
		return -1;
	}

}
