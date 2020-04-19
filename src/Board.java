
import java.awt.Color;
import java.util.ArrayList;
import java.util.Queue;
import java.awt.geom.*;

public class Board {
	final static String GS_MARKER = "marker";
	final static String GS_ROUND = "round";
	final static String GS_ROUND_END = "roundEnd";
	final static String GS_GAME_END = "gameEnd";

	private String gameState;
	private Queue<Rail> newRails;
	// private Player activePlayer;
	// private final Position[] positions = new Position[188];

	public final static City[] cities = { new City(new Position(17, 2), "Boston", GamePanel.cityOrange),
			new City(new Position(17, 4), "New York", GamePanel.cityOrange),
			new City(new Position(17, 5), "Washington", GamePanel.cityOrange),
			new City(new Position(18, 7), "Richmond", GamePanel.cityOrange),
			new City(new Position(17, 8), "Winston", GamePanel.cityOrange),
			new City(new Position(19, 10), "Charleston", GamePanel.cityOrange),
			new City(new Position(19, 12), "Jacksonville", GamePanel.cityOrange),

			new City(new Position(15, 2), "Buffalo", GamePanel.cityBlue),
			new City(new Position(13, 3), "Chicago", GamePanel.cityBlue),
			new City(new Position(15, 5), "Cincinnati", GamePanel.cityBlue),
			new City(new Position(10, 2), "Minneapolis", GamePanel.cityBlue),
			new City(new Position(3, 1), "Helena", GamePanel.cityBlue),
			new City(new Position(10, 1), "Duluth", GamePanel.cityBlue),
			new City(new Position(7, 1), "Bismark", GamePanel.cityBlue),

			new City(new Position(9, 4), "Omaha", GamePanel.cityYellow),
			new City(new Position(13, 6), "St. Louis", GamePanel.cityYellow),
			new City(new Position(11, 6), "Kansas City", GamePanel.cityYellow),
			new City(new Position(11, 8), "Oklahoma City", GamePanel.cityYellow),
			new City(new Position(8, 8), "Sante Fe", GamePanel.cityYellow),
			new City(new Position(4, 4), "Salt Lake City", GamePanel.cityYellow),
			new City(new Position(7, 5), "Denver", GamePanel.cityYellow),

			new City(new Position(7, 9), "Phoenix", GamePanel.cityRed),
			new City(new Position(10, 11), "El Paso", GamePanel.cityRed),
			new City(new Position(13, 10), "Dallas", GamePanel.cityRed),
			new City(new Position(14, 12), "Houston", GamePanel.cityRed),
			new City(new Position(15, 9), "Memphis", GamePanel.cityRed),
			new City(new Position(17, 10), "Atlanta", GamePanel.cityRed),
			new City(new Position(16, 12), "New Orleans", GamePanel.cityRed),

			new City(new Position(0, 0), "Seattle", GamePanel.cityGreen),
			new City(new Position(0, 1), "Portland", GamePanel.cityGreen),
			new City(new Position(2, 5), "Sacremento", GamePanel.cityGreen),
			new City(new Position(2, 6), "San Francisco", GamePanel.cityGreen),
			new City(new Position(5, 9), "Los Angeles", GamePanel.cityGreen),
			new City(new Position(6, 10), "San Diego", GamePanel.cityGreen),
			new City(new Position(1, 3), "Medford", GamePanel.cityGreen) };

	private static ArrayList<Rail> rails = new ArrayList<Rail>();
	private ArrayList<Position> positions;
	private ArrayList<Ellipse2D> positionHitboxes;
	private ArrayList<Position> possiblePlacements = new ArrayList<Position>(0);
	private ArrayList<Player> playerArray = new ArrayList<Player>(0);

	private int remainingRails;

	public Board() {
		RailFactory rf = new RailFactory();
		rails = rf.genRails();
		positions = rf.getPositions();
		positionHitboxes = rf.getPositionHitboxes();

		// newRails = new AbstractQueue<Rail>();
	}

	public void setRemainingRails(int r) {
		remainingRails = r;
	}

	public int getRemainingRails() {
		return remainingRails;
	}

	public ArrayList<Ellipse2D> getPositionHitboxes() {
		return positionHitboxes;
	}

	public ArrayList<Position> getPositions() {
		return positions;
	}

	public void addPlayer(Player p) {
		playerArray.add(p);
	}

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

	public ArrayList<Rail> getSurroundingRails(Position pos) {
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
				for (Rail check : getSurroundingRails(aroundPos)) {
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

	public int getDistancetoCity(Player p, City c) {
		return quickestPath(p.getMarkerPos(), c.getPos()).size();
	}

	public ArrayList<Rail> quickestPath(Position a, Position b) {
		int leftBound, rightBound, topBound, botBound;
		if (a.getX() < b.getX()) {
			leftBound = a.getX() - 2;// -2+2
			rightBound = b.getX() + 2;
		} else {
			if (a.getX() > b.getX()) {
				rightBound = a.getX() + 2;// +2-2
				leftBound = b.getX() - 2;
			} else {
				rightBound = a.getX() + 2;// +2-2
				leftBound = a.getX() - 2;
				// they would be on a the same vertical line;
			}
		}

		if (a.getY() < b.getY()) {
			topBound = a.getY() - 2;// same as above
			botBound = b.getY() + 2;
		} else {
			if (a.getY() > b.getY()) {
				botBound = a.getY() + 2;
				topBound = b.getY() - 2;
			} else {
				botBound = a.getY() + 2;
				topBound = a.getY() - 2;
				// they would be on a the same horizontal line;
			}
		}

		// Beware that these bounds can be negative
		ArrayList<ArrayList<Rail>> paths = new ArrayList<ArrayList<Rail>>();
		Rail r = getThisRail(rails, a, new Position(a.getX() - 1, a.getY()));
		if (r != null) {
			paths.add(new ArrayList<Rail>());
			paths.get(paths.size() - 1).add(r);
		}
		r = getThisRail(rails, a, new Position(a.getX() + 1, a.getY()));
		if (r != null) {
			paths.add(new ArrayList<Rail>());
			paths.get(paths.size() - 1).add(r);
		}
		r = getThisRail(rails, a, new Position(a.getX(), a.getY() - 1));
		if (r != null) {
			paths.add(new ArrayList<Rail>());
			paths.get(paths.size() - 1).add(r);
		}
		r = getThisRail(rails, a, new Position(a.getX(), a.getY() + 1));
		if (r != null) {
			paths.add(new ArrayList<Rail>());
			paths.get(paths.size() - 1).add(r);
		}
		r = getThisRail(rails, a, new Position(a.getX() - 1, a.getY() - 1));
		if (r != null) {
			paths.add(new ArrayList<Rail>());
			paths.get(paths.size() - 1).add(r);
		}
		r = getThisRail(rails, a, new Position(a.getX() + 1, a.getY() + 1));
		if (r != null) {
			paths.add(new ArrayList<Rail>());
			paths.get(paths.size() - 1).add(r);
		}

		for (int i = 0; i < paths.size(); i++) {
			Rail correctStartPoint = setStartCoordinateTo(paths.get(i).get(0), a);
			paths.get(i).remove(0);
			paths.get(i).add(correctStartPoint);
		}

		// ^gives the first moves
		ArrayList<Rail> restrictedRails = new ArrayList<Rail>();
		for (int i = 0; i < rails.size(); i++) {
			if (rails.get(i).startPos().getX() >= leftBound && rails.get(i).startPos().getX() <= rightBound
					&& rails.get(i).endPos().getX() >= leftBound && rails.get(i).endPos().getX() <= rightBound
					&& rails.get(i).startPos().getY() >= topBound && rails.get(i).startPos().getY() <= botBound
					&& rails.get(i).endPos().getY() >= topBound && rails.get(i).endPos().getY() <= botBound) {
				restrictedRails.add(rails.get(i));
			}
		}
		// restricts the rails array to ones within the bounds\
		ArrayList<ArrayList<Rail>> returnPaths= new ArrayList<ArrayList<Rail>>();
		paths = makePaths(paths, b, restrictedRails, returnPaths, 35);
		//
		System.out.println(paths.size());
		int shortestScore = 1000000;
		int shortestScoreIndex = 1000000;
		for (int l = 0; l < paths.size(); l++) {
			int currentScore = 0;
			for (int c = 0; c < paths.get(l).size(); c++) {
				System.out.print(paths.get(l).get(c).startPos().toString());//
				System.out.print(getThisRail(rails,paths.get(l).get(c).startPos(),paths.get(l).get(c).endPos()).isDouble());
				if (getThisRail(rails, paths.get(l).get(c).startPos(), paths.get(l).get(c).endPos()).isDouble()) 
					// maybe this wok
					currentScore += 2;
				else
					currentScore++;
			}
			if (currentScore < shortestScore) {
				shortestScore = currentScore;
				shortestScoreIndex = l;
			}
			System.out.println(currentScore);//
		}
		// sometimes returns 10000000
		return paths.get(shortestScoreIndex);
	}

	public ArrayList<ArrayList<Rail>> makePaths(ArrayList<ArrayList<Rail>> paths, Position b,
			ArrayList<Rail> restrictedRails, ArrayList<ArrayList<Rail>>path, int num) {
		ArrayList<ArrayList<Rail>> addedArrays = new ArrayList<ArrayList<Rail>>();
		for (int i = 0; i < paths.size(); i++) {
			ArrayList<Rail> currentPath = paths.get(i);
			Rail currentRail = currentPath.get(currentPath.size() - 1);
			ArrayList<Rail> around = new ArrayList<Rail>();
			around.add(new Rail(currentRail.endPos(),
					new Position(currentRail.endPos().getX() + 1, currentRail.endPos().getY())));
			around.add(new Rail(currentRail.endPos(),
					new Position(currentRail.endPos().getX() - 1, currentRail.endPos().getY())));
			around.add(new Rail(currentRail.endPos(),
					new Position(currentRail.endPos().getX(), currentRail.endPos().getY() + 1)));
			around.add(new Rail(currentRail.endPos(),
					new Position(currentRail.endPos().getX(), currentRail.endPos().getY() - 1)));
			around.add(new Rail(currentRail.endPos(),
					new Position(currentRail.endPos().getX() + 1, currentRail.endPos().getY() + 1)));
			around.add(new Rail(currentRail.endPos(),
					new Position(currentRail.endPos().getX() - 1, currentRail.endPos().getY() - 1)));
			for (int j = 0; j < around.size(); j++) {
				boolean repeat = repeat(currentPath, around.get(j));
				boolean inLimits = getThisRail(restrictedRails, around.get(j).startPos(),
						around.get(j).endPos()) != null;
				boolean endPointIsB = around.get(j).endPos().equals(b);
				boolean beginingPointIsB = equals(around.get(j).startPos(), b);
				if (endPointIsB) {
					//This never gets triggered
					ArrayList<Rail> nextGenPath = new ArrayList<Rail>();
					for (int k = 0; k < currentPath.size(); k++) {
						nextGenPath.add(currentPath.get(k));
					}
					nextGenPath.add(around.get(j));
					addedArrays.add(nextGenPath);
					path.add(nextGenPath);
					if(path.size()>=num) {
						return path;
					}
				} else {
					if (!repeat && inLimits && !beginingPointIsB) {
						ArrayList<Rail> nextGenPath = new ArrayList<Rail>();
						for (int k = 0; k < currentPath.size(); k++) {
							nextGenPath.add(currentPath.get(k));
						}
						nextGenPath.add(around.get(j));
						addedArrays.add(nextGenPath);
					} else {
						if (repeat || !inLimits) {

						} else {
							if (beginingPointIsB && j == 0) {
								ArrayList<Rail> nextGenPath = new ArrayList<Rail>();
								for (int k = 0; k < currentPath.size(); k++) {
									nextGenPath.add(currentPath.get(k));
								}
								addedArrays.add(nextGenPath);
							}
						}
					}
				}

			}
		}

		//System.out.println(paths.size()+" "+addedArrays.size());
		return makePaths(addedArrays, b, restrictedRails, path, num);
	}

	public boolean repeat(ArrayList<Rail> array, Rail r) {
		boolean repeat = false;
		for (int i = 0; i < array.size(); i++) {
			if (equals(array.get(i), r))
				repeat = true;
		}
		return repeat;
	}

	public Rail setStartCoordinateTo(Rail r, Position p) {
		Position start = r.startPos();
		Position end = r.endPos();
		if (p.equals(r.startPos()))
			return r;
		else
			return new Rail(end, start);
	}

	public Rail getThisRail(ArrayList<Rail> rails, Position A, Position B) {
		// returns the object in the rails array with these end/start points
		for (int i = 0; i < rails.size(); i++) {
			Rail r = rails.get(i);
			if (r.endPos().equals(B) && r.startPos().equals(A)) {
				return rails.get(i);
			}
			if (r.endPos().equals(A) && r.startPos().equals(B)) {
				return rails.get(i);
			}

		}
		return null;
	}

	public boolean equals(Rail one, Rail two) {
		/*if (one.startPos().equals(two.startPos()) && one.endPos().equals(two.endPos()))
			return true;
		if (one.startPos().equals(two.endPos()) && one.endPos().equals(two.startPos()))
			return true;
		return false;*/
		return one.equals(two);
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
			for (Rail r2 : getSurroundingRails(r.startPos())) {
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
			for (Rail r2 : getSurroundingRails(r.endPos())) {
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

	// public void setActivePlayer(Player p) {
	// activePlayer = p;
	// }

	// public Player getActivePlayer() {
	// return activePlayer;
	// }

	public void setGameState(String state) {
		gameState = state;
	}

	public String getGameState() {
		return gameState;
	}

}

