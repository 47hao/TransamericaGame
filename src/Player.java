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

	Player(String n, Position markerP, City[] cities, Color c) {
		name = n;
		markerPos = markerP;
		targetCities = new ArrayList<City>(Arrays.asList(cities));
		color = c;
		score = 0;
	}

	abstract String getName();

	Position getMarkerPos() {
		return markerPos;
	}
	
	abstract int getDistanceToCity(City c);

	abstract void setDistanceToCity(City c, int n);

	abstract int getRail(Rail[] rails);

	abstract void addScore(int amount);

	int getScore() {
		return score;
	}

	ArrayList<City> getTargetCities() {
		return targetCities;
	}
	
}
