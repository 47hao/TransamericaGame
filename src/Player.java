import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	Color color;
	String name;

	int score;

	Position markerPos;
	ArrayList<Rail> possibleRails;
	ArrayList<City> targetCities;
	int[] distancesToCities;

	Player(String n, Color c) {
		possibleRails = new ArrayList<Rail>();
		// XXX: just for testing
		possibleRails.add(new Rail(new Position(0, 0), new Position(0, 1)));
		possibleRails.add(new Rail(new Position(1, 1), new Position(2, 2)));
		possibleRails.add(new Rail(new Position(2, 2), new Position(3, 3)));
		possibleRails.add(new Rail(new Position(3, 3), new Position(4, 4)));
		name = n;
		targetCities = new ArrayList<City>();
		color = c;
		score = 0;
	}

	abstract String getName();

	public void setMarkerPos(Position pos) {
		markerPos = pos;
	}

	public Position getMarkerPos() {
		return markerPos;
	}

	public int getDistanceToCity(City c) {
		return distancesToCities[targetCities.indexOf(c)];
	}

	// abstract int getDistanceToCity(City c);

	// abstract void setDistanceToCity(City c, int n);

	abstract int getRail(Rail[] rails);

	abstract void addScore(int amount);

	public int getScore() {
		return score;
	}

	public ArrayList<City> getTargetCities() {
		return targetCities;
	}

	public ArrayList<Rail> getPossibleRails() {
		return possibleRails;
	}

	// public void calculateDistances() {
	// for (int i = 0; i < targetCities.size(); i++) {
	// distancesToCities[i] = this.getDistanceToCity(targetCities.get(i));
	// }
	// }

	public int[] getDistancesToCities() {
		return distancesToCities;
	}

	public void setDistancesToCities(int[] src) {
		distancesToCities = new int[6];
		System.arraycopy(src, 0, distancesToCities, 0, src.length);
	}

	public Color getColor() {
		return color;
	}

}