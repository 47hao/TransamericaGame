import java.awt.Color;

class ComputerPlayer extends Player {
	private String offname;
	private Strategy s;
	private Board board;

	public ComputerPlayer(String name, Color c, Strategy strat) {
		super(name, c);
		offname = name;
		s = strat;
	}

	public String getName() {
		return offname;
	}

	public void addScore(int amount) {
		score += amount;
	}

	public int getScore() {
		return score;
	}

	// public void setDistanceToCity(City c, int distance) {
	// distancesToCities[targetCities.indexOf(c)] = distance;
	// }

	// public Rail getRail(City[] targetCities, Rail[] possibleRails, Board board) {
	// return s.returnRail(targetCities, possibleRails, board);
	// }

	// XXX: a note: only this getrail method should be used outside of this class
	// (this allows for having the board state)
	public int getRail(Rail[] possibleRails, Board b) {
		board = b;
		return getRail(possibleRails);
	}

	public int getRail(Rail[] possibleRails) {
		// can use instance of board here

		// Rail r = s.returnRail(targetCities, possibleRails, board);
		getTargetCities();

		Rail r = null;
		int index = -1;
		for (int i = 0; i < possibleRails.length; i++) {
			if (possibleRails[i].equals(r)) {
				index = i;
			}
		}
		return index;
	}
}
