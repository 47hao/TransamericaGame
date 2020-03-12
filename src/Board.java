
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
		//XXX: setting default gamestate as "round"
		gameState = "round";
		// Orange: Boston, New York, Washington, Richmond, Winston, Charleston,
		// Jacksonville
		// Blue: Buffalo, Chicago, Cincinnati, Minneapolis, Helena, Duluth, Bismark
		// Yellow: Omaha, St.Louis, Kansas City, Oklahoma City, Sante Fe, Salt Lake
		// City, Denver
		// Red: Phoenix, El Paso, Dallas, Houston, Memphis, Atlanta, New Orleans
		// Green: Seattle, Portland, Sacremento, San Francisco, Los Angeles, San Diego,
		// Medford
		Color c= new Color(252,102,3);
		cities[0] = new City(new Position(17, 2), "Boston", c);
		cities[1] = new City(new Position(17, 4), "New York", c);
		cities[2] = new City(new Position(17, 5), "Washington", c);
		cities[3] = new City(new Position(18, 7), "Richmond", c);
		cities[4] = new City(new Position(17, 8), "Winston", c);
		cities[5] = new City(new Position(19, 10), "Charleston", c);
		cities[6] = new City(new Position(19, 12), "Jacksonville", c);

		cities[7] = new City(new Position(15, 2), "Buffalo", Color.BLUE);
		cities[8] = new City(new Position(13, 3), "Chicago", Color.BLUE);
		cities[9] = new City(new Position(0, 0), "Cincinnati", Color.BLUE);
		cities[10] = new City(new Position(10, 2), "Minneapolis", Color.BLUE);
		cities[11] = new City(new Position(3, 1), "Helena", Color.BLUE);
		cities[12] = new City(new Position(10, 1), "Duluth", Color.BLUE);
		cities[13] = new City(new Position(7, 1), "Bismark", Color.BLUE);

		cities[14] = new City(new Position(9, 4), "Omaha", Color.YELLOW);
		cities[15] = new City(new Position(13, 6), "St. Louis", Color.YELLOW);
		cities[16] = new City(new Position(11, 6), "Kansas City", Color.YELLOW);
		cities[17] = new City(new Position(11, 8), "Oklahoma City", Color.YELLOW);
		cities[18] = new City(new Position(8, 8), "Sante Fe", Color.YELLOW);
		cities[19] = new City(new Position(4, 4), "Salt Lake City", Color.YELLOW);
		cities[20] = new City(new Position(7, 5), "Denver", Color.YELLOW);

		cities[21] = new City(new Position(7, 9), "Phoenix", Color.RED);
		cities[22] = new City(new Position(10, 11), "El Paso", Color.RED);
		cities[23] = new City(new Position(13, 10), "Dallas", Color.RED);
		cities[24] = new City(new Position(14, 12), "Houston", Color.RED);
		cities[25] = new City(new Position(15, 9), "Memphis", Color.RED);
		cities[26] = new City(new Position(17, 10), "Atlanta", Color.RED);
		cities[27] = new City(new Position(16, 12), "New Orleans", Color.RED);

		cities[28] = new City(new Position(0, 0), "Seattle", Color.GREEN);
		cities[29] = new City(new Position(0, 1), "Portland", Color.GREEN);
		cities[30] = new City(new Position(2, 5), "Sacremento", Color.GREEN);
		cities[31] = new City(new Position(2, 6), "San Francisco", Color.GREEN);
		cities[32] = new City(new Position(5, 9), "Los Angeles", Color.GREEN);
		cities[33] = new City(new Position(6, 10), "San Diego", Color.GREEN);
		cities[34] = new City(new Position(1, 3), "Medford", Color.GREEN);

		rails = new RailFactory().genRails();
		
	}

	public void addPlayer(Player p) {
		playerArray.add(p);
	}
	// 	public ArrayList quickestPath(Position initialNode, Position endNode) {
	// 	int leftLimit, rightLimit, topLimit, botLimit;
	// 	ArrayList<Rail> shortestPath= new ArrayList<Rail>(0);
		
	// 	if(initialNode.getX()<endNode.getX()) {
	// 		//The plus two is just in case there are quicker routes that are beyond the original borders
	// 		leftLimit=initialNode.getX()+2;
	// 		rightLimit=endNode.getX()+2;
	// 	}else {
	// 			leftLimit=endNode.getX()+2;	
	// 			rightLimit=initialNode.getX()+2;
	// 	}
		
	// 	if(initialNode.getY()<endNode.getY()+2) {
	// 		topLimit=initialNode.getY()+2;
	// 		botLimit=endNode.getY()+2;
	// 	}else {
	// 		topLimit=endNode.getY()+2;
	// 		botLimit=initialNode.getY()+2;
	// 	}
		
	// }

	public Rail getRail(Position start, Position end) {
		for (int i = 0; i < rails.size(); i++) {
			if (equals(start, rails.get(i).startPos()) && equals(end, rails.get(i).endPos())
					|| equals(end, rails.get(i).startPos()) && equals(start, rails.get(i).endPos())) {
				return rails.get(i);
			}
		}
		System.out.print("Board Class: No Such Rails");
		return null;
	}

	public ArrayList<Rail> getAroundRails(Position pos) {
		ArrayList<Rail> returnRails = new ArrayList<Rail>();
		for (Rail rail : rails) {
			Position railStart = rail.startPos();
			Position railEnd = rail.endPos();
			if ((railStart.getX() == pos.getX() && railStart.getY() == pos.getY())
					|| (railEnd.getX() == pos.getX() && railEnd.getY() == pos.getY())) {
				returnRails.add(rail);
			}
		}
		return returnRails;
	}
	
	public ArrayList<Rail> computeConnectedRails(Player p) {
		ArrayList<Rail> returnVal = new ArrayList<Rail>();
		ArrayList<Position> current = new ArrayList<Position>();
		boolean stop = false;
		current.add(p.getMarkerPos());
		while (!stop) {
			for (Position aroundPos : current) {
				for (Rail check : getAroundRails(aroundPos)) {
					for (Rail r : rails) {
						boolean exists = false;
						for (Rail previous : returnVal) {
							if (previous.equals(r)) {
								exists = true;
							}
						}
						if (!exists && r.getState() == Rail.PLACED && r.equals(check)) {
							returnVal.add(r);
							current.add(r.endPos());
							stop = false;
						} else {
							current.remove(aroundPos);
						}
					}
				}
			}
			if (current.size() == 0) {
				stop = true;
			}
		}
		return returnVal;
	}

	int getDistancetoCity(Player p, City c) {
		return quickestPath(p.getMarkerPos(), c.getPos()).size();
	}

	public ArrayList<Rail> quickestPath(Position a, Position b) {
		ArrayList<Rail> positionArray = new ArrayList<Rail>(0);
		Position currentPosition=a;
		int xDistance=-(a.getX()-b.getX());
		int yDistance=-(a.getY()-b.getY());
		while (!equals(a, b)) {
			if(xDistance>0) {
				while(!isDoubleRail(currentPosition, new Position(currentPosition.getX()+1,currentPosition.getY()))&&currentPosition.getX()<b.getX()){
					positionArray.add(new Rail(currentPosition, new Position(currentPosition.getX()+1,currentPosition.getY())));
					currentPosition= new Position(currentPosition.getX()+1,currentPosition.getY());
				}
			}else {
				while(!isDoubleRail(currentPosition, new Position(currentPosition.getX()-1,currentPosition.getY()))&&currentPosition.getX()>b.getX()){
					positionArray.add(new Rail(currentPosition, new Position(currentPosition.getX()-1,currentPosition.getY())));
					//this rail on rail array
					currentPosition= new Position(currentPosition.getX()-1,currentPosition.getY());
				}
			}
			if(yDistance>0) {
				positionArray.add(new Rail(currentPosition, new Position(currentPosition.getX(),currentPosition.getY()+1)));		
				currentPosition= new Position(currentPosition.getX()-1,currentPosition.getY());

			}else {
				positionArray.add(new Rail(currentPosition, new Position(currentPosition.getX(),currentPosition.getY()+1)));	
				currentPosition= new Position(currentPosition.getX()-1,currentPosition.getY());

			}
		}
		return positionArray;
	}

	public boolean isDoubleRail(Position startPos, Position endPos) {
		for (int i = 0; i < rails.size(); i++) {
			Rail r = rails.get(i);
			if (equals(r.startPos(), startPos) && equals(r.endPos(), endPos)
					|| equals(r.startPos(), endPos) && equals(r.endPos(), startPos)) {
				if (rails.get(i).isDouble())
					return true;
				return false;
			}
		}
		return false;
	}

	public boolean equals(Position a, Position b) {
		// only compares the x and y values, excludes state boolean
		if (a.getX() == b.getX() && a.getY() == b.getY())
			return true;
		return false;
	}

	public City getCity(Position location) {
		for (int i = 0; i < cities.length; i++) {
			Position cityLoc = cities[i].getPos();
			if (cityLoc.getX() == location.getX() && cityLoc.getY() == location.getY())
				return cities[i];
		}
		return null;
	}

	public ArrayList<Rail> computePossiblePlacements(Player p) {
		ArrayList<Rail> returnVal = new ArrayList<Rail>();
		ArrayList<Rail> currentRails = computeConnectedRails(p);
		for (Rail r : currentRails) {
			for (Rail r2 : getAroundRails(r.startPos())) {
				boolean duplicate = false;
				for (Rail check : returnVal) {
					if (check.equals(r2)) {
						duplicate = true;
					}
				}
				for (Rail check : currentRails) {
					if (check.equals(r2)) {
						duplicate = true;
					}
				}
				if (!duplicate && r2.getState() == Rail.EMPTY) {
					returnVal.add(r2);
				}
			}
			for (Rail r2 : getAroundRails(r.endPos())) {
				boolean duplicate = false;
				for (Rail check : returnVal) {
					if (check.equals(r2)) {
						duplicate = true;
					}
				}
				for (Rail check : currentRails) {
					if (check.equals(r2)) {
						duplicate = true;
					}
				}
				if (!duplicate && r2.getState() == Rail.EMPTY) {
					returnVal.add(r2);
				}
			}
		}
		return returnVal;
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
