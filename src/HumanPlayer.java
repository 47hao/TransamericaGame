import java.awt.Color;

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

	public int getRail(Rail[] rails) {
		return 0;
	}

}
