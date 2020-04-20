import java.awt.Color;

class ComputerPlayer extends Player {
	private String offname;
	private Strategy s;

	public ComputerPlayer(String name, Color c, Strategy strat) {
		super(name, c);
		offname = name;
		s = strat;
		isComputer = true;
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
		Rail returnRail = s.returnRail(targetCities.toArray(new City[targetCities.size()]), possibleRails, b);
		for(int i=0; i<possibleRails.length; i++)
		{
			if(possibleRails[i].equals(returnRail))
			{
				return i;
			}
		}
		return -1;
	}
	
	public Position getMarker(Board b) {
		int range = b.getPositions().size();
		return b.getPositions().get((int)(Math.random()*range));
	}
}
