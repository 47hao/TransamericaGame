
import java.awt.Color;
import java.util.ArrayList;
import java.util.Queue;

public class Board {
	private String gameState;
	private Queue<Rail> newRails;
	private Player activePlayer;
	private final Position[] positions = new Position[100]; // temp
	private final City[] cities = new City[35];
	private final Rail[] rails = new Rail[100]; // temp
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
		
		cities[1] = new City(new Position(0, 0), "Boston", Color.orange);
		cities[2] = new City(new Position(0, 0), "New York", Color.orange);
		cities[3] = new City(new Position(0, 0), "Washington", Color.orange);
		cities[4] = new City(new Position(0, 0), "Richmond", Color.orange);
		cities[5] = new City(new Position(0, 0), "Winston", Color.orange);
		cities[6] = new City(new Position(0, 0), "Charleston", Color.orange);
		cities[7] = new City(new Position(0, 0), "Jacksonville", Color.orange);

		cities[15] = new City(new Position(0, 0), "Buffalo", Color.blue);
		cities[16] = new City(new Position(0, 0), "Chicago", Color.blue);
		cities[17] = new City(new Position(0, 0), "Cincinnati", Color.blue);
		cities[18] = new City(new Position(0, 0), "Minneapolis", Color.blue);
		cities[19] = new City(new Position(0, 0), "Helena", Color.blue);
		cities[20] = new City(new Position(0, 0), "Duluth", Color.blue);
		cities[21] = new City(new Position(0, 0), "Bismark", Color.blue);

		cities[8] = new City(new Position(0, 0), "Omaha", Color.yellow);
		cities[9] = new City(new Position(0, 0), "St. Louis", Color.yellow);
		cities[10] = new City(new Position(0, 0), "Kansas City", Color.yellow);
		cities[11] = new City(new Position(0, 0), "Oklahoma City", Color.yellow);
		cities[12] = new City(new Position(0, 0), "Sante Fe", Color.yellow);
		cities[13] = new City(new Position(0, 0), "Salt Lake City", Color.yellow);
		cities[14] = new City(new Position(0, 0), "Denver", Color.yellow);

		cities[22] = new City(new Position(0, 0), "Phoenix", Color.red);
		cities[23] = new City(new Position(0, 0), "El Paso", Color.red);
		cities[24] = new City(new Position(0, 0), "Dallas", Color.red);
		cities[25] = new City(new Position(0, 0), "Houston", Color.red);
		cities[26] = new City(new Position(0, 0), "Memphis", Color.red);
		cities[27] = new City(new Position(0, 0), "Atlanta", Color.red);
		cities[28] = new City(new Position(0, 0), "New Orleans", Color.red);

		cities[29] = new City(new Position(0, 0), "Seattle", Color.green);
		cities[30] = new City(new Position(0, 0), "Portland", Color.green);
		cities[31] = new City(new Position(0, 0), "Sacremento", Color.green);
		cities[32] = new City(new Position(0, 0), "San Francisco", Color.green);
		cities[33] = new City(new Position(0, 0), "Los Angeles", Color.green);
		cities[34] = new City(new Position(0, 0), "San Diego", Color.green);
		cities[35] = new City(new Position(0, 0), "Medford", Color.green);

	}

	public void addPlayer(Player p) {
		playerArray.add(p);
	}

	public Rail getRail(Position start, Position end) {
		for (int i = 0; i < rails.length; i++) {
			Position railStart = rails[i].startPos();
			Position railEnd = rails[i].endPos();
			if (((railStart.getX() == start.getX() && railStart.getY() == start.getY())
					|| (railEnd.getX() == end.getX() && railEnd.getY() == end.getY()))
					|| ((railStart.getX() == end.getX() && railStart.getY() == end.getY())
							|| (railEnd.getX() == start.getX() && railEnd.getY() == start.getY()))) {
				return rails[i];
			}
		}
		return null;
	}

	public Rail[] getRails(Position pos) {
		Rail[] returnRails = new Rail[0];
		for (int i = 0; i < rails.length; i++) {
			Rail rail = rails[i];
			Position railStart = rail.startPos();
			Position railEnd = rail.endPos();
			if ((railStart.getX() == pos.getX() && railStart.getY() == pos.getY())
					|| (railEnd.getX() == pos.getX() && railEnd.getY() == pos.getY())) {
				Rail[] secondRail = new Rail[returnRails.length + 1];
				for (int j = 0; j < returnRails.length; j++) {
					secondRail[i] = returnRails[i];
				}
				secondRail[rails.length] = rail;
				returnRails = secondRail;
			}
		}
		return returnRails;
	}

	public Rail[] computeConnectedRails(Player p) {

	}

	int getDistancetoCity(Player p, City c) {

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
		for(int i=0;i<rails.length;i++) {
			Rail r= rails[i];
			if(equals(r.startPos(),startPos) && equals(r.endPos(),endPos) ||
					equals(r.startPos(),endPos) && equals(r.endPos(),startPos) 	){
				if(rails[i].isDouble())
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
		for (int i = 0; i < rails.length; i++) {
			if (equals(r.startPos(), rails[i].startPos()) && equals(r.endPos(), rails[i].endPos()) ||
					equals(r.endPos(), rails[i].startPos()) && equals(r.startPos(), rails[i].endPos()) 	) {
				return rails[i];
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

	public Rail[] computePossiblePlacements() {

	}

	public void setRailState(Rail r, String state) {
		r.setState(state);
	}

	public ArrayList<Player> getPlayerArray() {
		return playerArray;
	}

	public Rail[] getRails() {
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

