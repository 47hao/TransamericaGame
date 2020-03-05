
import java.awt.Color;
import java.util.ArrayList;
import java.util.Queue;

public class Board {
	private String gameState;
	private Queue<Rail> newRails;
	private Player activePlayer;
	private final Position[] positions = new Position[188];
	private final City[] cities = new City[35];
	private ArrayList<Rail> rails = new ArrayList<Rail>();
	private ArrayList<Position> possiblePlacements = new ArrayList<Position>(0);
	private ArrayList<Player> playerArray = new ArrayList<Player>(0);

		public Board() {
		// Orange: Boston, New York, Washington, Richmond, Winston, Charleston,
		// Jacksonville
		// Blue: Buffalo, Chicago, Cincinnati, Minneapolis, Helena, Duluth, Bismark
		// Yellow: Omaha, St.Louis, Kansas City, Oklahoma City, Sante Fe, Salt Lake
		// City, Denver
		// Red: Phoenix, El Paso, Dallas, Houston, Memphis, Atlanta, New Orleans
		// Green: Seattle, Portland, Sacremento, San Francisco, Los Angeles, San Diego, Medford
		
		cities[0]  = new City(new Position(17,2), "Boston", Color.ORANGE);
		cities[1]  = new City(new Position(17,4), "New York", Color.ORANGE);
		cities[2]  = new City(new Position(17,5), "Washington", Color.ORANGE);
		cities[3]  = new City(new Position(18,7), "Richmond", Color.ORANGE);
		cities[4]  = new City(new Position(17,9), "Winston", Color.ORANGE);
		cities[5]  = new City(new Position(20,10), "Charleston", Color.ORANGE);
		cities[6]  = new City(new Position(20,12), "Jacksonville", Color.ORANGE);

		cities[7]  = new City(new Position(15,2), "Buffalo", Color.BLUE);
		cities[8]  = new City(new Position(13,3), "Chicago", Color.BLUE);
		cities[9]  = new City(new Position(0, 0), "Cincinnati", Color.BLUE);
		cities[10] = new City(new Position(10,2), "Minneapolis", Color.BLUE);
		cities[11] = new City(new Position(3,1), "Helena", Color.BLUE);
		cities[12] = new City(new Position(10, 1), "Duluth", Color.BLUE);
		cities[13] = new City(new Position(7,1), "Bismark", Color.BLUE);

		cities[14] = new City(new Position(9, 4), "Omaha", Color.YELLOW);
		cities[15] = new City(new Position(13, 6), "St. Louis", Color.YELLOW);
		cities[16] = new City(new Position(11, 6), "Kansas City", Color.YELLOW);
		cities[17] = new City(new Position(11, 8), "Oklahoma City", Color.YELLOW);
		cities[18] = new City(new Position(8,8), "Sante Fe", Color.YELLOW);
		cities[19] = new City(new Position(4,4), "Salt Lake City", Color.YELLOW);
		cities[20] = new City(new Position(7, 5), "Denver", Color.YELLOW);

		cities[21] = new City(new Position(8, 9), "Phoenix", Color.RED);
		cities[22] = new City(new Position(11, 11), "El Paso", Color.RED);
		cities[23] = new City(new Position(14, 10), "Dallas", Color.RED);
		cities[24] = new City(new Position(15, 12), "Houston", Color.RED);
		cities[25] = new City(new Position(16, 9), "Memphis", Color.RED);
		cities[26] = new City(new Position(18, 10), "Atlanta", Color.RED);
		cities[27] = new City(new Position(17, 12), "New Orleans", Color.RED);

		cities[28] = new City(new Position(0, 0), "Seattle", Color.GREEN);
		cities[29] = new City(new Position(0, 1), "Portland", Color.GREEN);
		cities[30] = new City(new Position(2,5), "Sacremento", Color.GREEN);
		cities[31] = new City(new Position(2,6), "San Francisco", Color.GREEN);
		cities[32] = new City(new Position(5,9), "Los Angeles", Color.GREEN);
		cities[33] = new City(new Position(6,10), "San Diego", Color.GREEN);
		cities[34] = new City(new Position(1,3), "Medford", Color.GREEN);
		
		
		rails = new RailFactory().genRails();
		for(Rail r : rails)
		{
			r.setState(Rail.EMPTY);
		}
	}

	public void addPlayer(Player p) {
		playerArray.add(p);
	}

	public Rail getRail(Position start, Position end) {
		for (int i = 0; i < rails.size(); i++) {
			Position railStart = rails.get(i).startPos();
			Position railEnd = rails.get(i).endPos();
			if (((railStart.getX() == start.getX() && railStart.getY() == start.getY())
					|| (railEnd.getX() == end.getX() && railEnd.getY() == end.getY()))
					|| ((railStart.getX() == end.getX() && railStart.getY() == end.getY())
							|| (railEnd.getX() == start.getX() && railEnd.getY() == start.getY()))) {
				return rails.get(i);
			}
		}
		return null;
	}

	public Rail[] getRails(Position pos) {
		Rail[] returnRails = new Rail[0];
		for (int i = 0; i < rails.size(); i++) {
			Rail rail = rails.get(i);
			Position railStart = rail.startPos();
			Position railEnd = rail.endPos();
			if ((railStart.getX() == pos.getX() && railStart.getY() == pos.getY())
					|| (railEnd.getX() == pos.getX() && railEnd.getY() == pos.getY())) {
				Rail[] secondRail = new Rail[returnRails.length + 1];
				for (int j = 0; j < returnRails.length; j++) {
					secondRail[i] = returnRails[i];
				}
				secondRail[rails.size()] = rail;
				returnRails = secondRail;
			}
		}
		return returnRails;
	}

	public Rail[] computeConnectedRails(Player p) {

	}

	int getDistancetoCity(Player p, City c) {
		return distanceBetweenTwoPoints(p.getMarkerPos(), c.getPos()).size();
	}

	public ArrayList<Rail> distanceBetweenTwoPoints(Position a, Position b) {
		ArrayList<Rail> positionArray = new ArrayList<Rail>(0);
		Position currentPosition=a;
		int xDistance=-(a.getX()-b.getX());
		int yDistance=-(a.getY()-b.getY());
		while (!equals(a, b)) {
			if()
		}
		return positionArray;
	}
	public boolean isDoubleRail(Position startPos, Position endPos) {
		for(int i=0;i<rails.size();i++) {
			Rail r= rails.get(i);
			if(equals(r.startPos(),startPos) && equals(r.endPos(),endPos) ||
					equals(r.startPos(),endPos) && equals(r.endPos(),startPos) 	){
				if(rails.get(i).isDouble())
					return true;
				return false;
			}
		}
		return false;
	}
	public boolean equals(Position a, Position b) {
		if (a.getX() == b.getX() && a.getY() == b.getY())
			return true;
		return false;
	}

	public Rail thisRailOnRailsArray(Rail r) {
		for (int i = 0; i < rails.size(); i++) {
			if (equals(r.startPos(), rails.get(i).startPos()) && equals(r.endPos(), rails.get(i).endPos()) ||
					equals(r.endPos(), rails.get(i).startPos()) && equals(r.startPos(), rails.get(i).endPos()) 	) {
				return rails.get(i);
			}
		}
		System.out.print("Board Class: No Such Rails");
		return null;
	}

	public City getCity(Position location) {
		for (int i = 0; i < cities.length; i++) {
			Position cityLoc = cities[i].getPos();
			if (cityLoc.getX() == location.getX() && cityLoc.getY() == location.getY())
				return cities[i];
		}
		return null;
	}

	public ArrayList<Rail> computePossiblePlacements() {

	}

	public void setRailState(Rail r, String state) {
		r.setState(state);
	}

	public ArrayList<Player> getPlayerArray() {
		return playerArray;
	}

	public ArrayList<Rail> getRails() {
		return rails;
	}

	public City[] getCities() {
		return cities;
	}

	public void setActivePlayer(Player p) {
		activePlayer = p;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setGameState(String state) {
		gameState = state;
	}

	public String getGameState() {
		return gameState;
	}

}

