import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player {
	Color color;
	String name;

	int score;

	Position markerPos;
	ArrayList<City> targetCities;
	ArrayList<Integer> distanceToCities;

	Player(String n, City[] cities, Color c) {
		name = n;
		targetCities = new ArrayList<City>(Arrays.asList(cities));
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

	abstract int getDistanceToCity(City c);

	abstract void setDistanceToCity(City c, int n);

	abstract int getRail(Rail[] rails);

	abstract void addScore(int amount);

	public int getScore() {
		return score;
	}

	public ArrayList<City> getTargetCities() {
		return targetCities;
	}

	public Color getColor() {
		return color;
	}

}
