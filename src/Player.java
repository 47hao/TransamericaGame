import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class Player {
	Color color;
	String name;
	boolean isComputer;

	int score;

	Position markerPos;
	ArrayList<Rail> validRails;
	ArrayList<City> targetCities;
	ArrayList<Boolean> targetsReached;
	int[] distancesToCities;

	int scoreboardOffset = 0;
	boolean largeIcon = false;

	// for initializing all the rails surrounding a point
	final int[] surroundingFromCoords = { 1, 0, 1, 1, 0, 1 }; // the three originating from it
	final int[] surroundingToCoords = { -1, 0, -1, -1, 0, -1 }; /// the three that don't start at it

	Player(String n, Color c) {
		validRails = new ArrayList<Rail>();
		name = n;
		targetCities = new ArrayList<City>();
		targetsReached = new ArrayList<Boolean>();
		color = c;
		score = 0;
	}

	abstract String getName();
	
	public boolean isComputer()
	{
		return isComputer;
	}

	public void initTargetCities()
	{
		for(int i=0; i<targetCities.size(); i++)
			targetsReached.add(false);
	}
	
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
	abstract Position getMarker(Board b);

	abstract int getRail(Rail[] rails, Board b);

	abstract void addScore(int amount);

	public int getScore() {
		return score;
	}

	public ArrayList<City> getTargetCities() {
		return targetCities;
	}

	public ArrayList<Rail> getValidRails() {
		return validRails;
	}

	public void setValidRails(ArrayList<Rail> rails) {
		validRails = rails;
	}
	// public void calculateDistances() {
	// for (int i = 0; i < targetCities.size(); i++) {
	// distancesToCities[i] = this.getDistanceToCity(targetCities.get(i));
	// }
	// }
	public void setCityReached(City c)
	{
		System.out.println("Reached " + c);
		int i = targetCities.indexOf(c);
		targetsReached.set(i, true);
	}
	
	public boolean checkAllCitiesReached()
	{
		System.out.println("Checckin " + targetsReached.size() + "targets");
		for(boolean reached : targetsReached)
		{
			if(reached==false)
				return false;
		}
		return true;
	}

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

	public void setOffset(int off) {
		scoreboardOffset = off;
	}

	public int getOffset() {
		return scoreboardOffset;
	}

	public void setLarge(boolean size) {
		largeIcon = size;
	}

	public boolean getLarge() {
		return largeIcon;
	}
	
	public void clearCities() {
		targetCities = new ArrayList<City>();
	}

	public void clearMarker() {
		markerPos = null;
	}
	
}