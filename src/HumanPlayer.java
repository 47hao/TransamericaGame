import java.awt.Color;

public class HumanPlayer extends Player {

	public HumanPlayer(String n, Position markerP, City[] cities, Color c)
	{
		super(n, markerP, cities, c);
	}
	
	public String getName() {
		return name;
	}
	
	public void addScore(int amount)
	{
		score+=amount;
	}
	
	public int getScore()
	{
		return score;
	}

	public int getDistanceToCity(City c) {
		return distanceToCities.get(targetCities.indexOf(c));
	}

	public void  setDistanceToCity(City c, int distance) {
		distanceToCities.set(targetCities.indexOf(c),distance);
	}

	public int getRail(Rail[] rails) {
		return 0;
	}
	
	
}
