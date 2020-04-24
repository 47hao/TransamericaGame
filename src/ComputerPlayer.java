import java.awt.Color;
import java.util.ArrayList;

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
	
	public Position getMarker(Board b, ArrayList<Position> otherMarkers) {
		int range = b.getPositions().size();
		Position selectedPos = null;
		boolean occupied = false;
		do
		{
			occupied = false;
			selectedPos = b.getPositions().get((int)(Math.random()*range));
			for(Position otherPos : otherMarkers)
			{
				if(otherPos != null)
				{
					if(otherPos.equals(selectedPos))
					{
						occupied = true;
						System.out.println("DUPLICATE" + otherPos + " = " + selectedPos);
					}
				}
				
			}
			
		} while(occupied);
		return selectedPos;
	}
}
