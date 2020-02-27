import java.awt.Color;

class ComputerPlayer extends Player{
	String offname;
	Strategy s;
	
	public ComputerPlayer(String name, Color c, Strategy strat) {
		offname=name;
		s=strat;
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

	public int getDistanceToCity(City c) {
		return distanceToCities.get(targetCities.indexOf(c));
	}

	public void setDistanceToCity(City c, int distance) {
		distanceToCities.set(targetCities.indexOf(c), distance);
	}

	public int getRail(City[] targetCities, Rail[] possibleRails, Board board) {
		return s.returnRail(targetCities, possibleRails,board);
	}
}
