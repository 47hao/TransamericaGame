import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player {
	Color color;
	String name;

	int score;

	Position markerPos;
	ArrayList<City> targetCities;
	int[] distancesToCities;

	Player(String n, Color c) {
		name = n;
		targetCities = new ArrayList<City>();
		distancesToCities = new int[6];
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

	// public void calculateDistances() {
	// for (int i = 0; i < targetCities.size(); i++) {
	// distancesToCities[i] = this.getDistanceToCity(targetCities.get(i));
	// }
	// }

	public int[] getDistancesToCities() {
		return distancesToCities;
	}

	public void setDistancesToCities(int[] src) {
		System.arraycopy(src, 0, distancesToCities, 0, src.length);
	}

	public Color getColor() {
		return color;
	}

}
