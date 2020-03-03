
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
		//Orange: Boston, New York, Washington, Richmond, Winston, Charleston, Jacksonville
		//Blue: Buffalo, Chicago, Cincinnati, Minneapolis, Helena, Duluth, Bismark
		//Yellow: Omaha, St.Louis, Kansas City, Oklahoma city, Sante Fe, Salt Lake City, Denver
		//Red: Phoenix, El Paso, Dallas, Houston, Memphis, Atlanta, New Orleans
		//Green: Seattle, Portland, Sacremento, San Francisco, Los Angeles, San Diego, Medford
		
		
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
	
	public City getCity(Position location) {
		for(int i=0;i<cities.length;i++) {
			Position cityLoc= cities[i].getPos();
				if(cityLoc.getX()==location.getX() && cityLoc.getY()==location.getY()) 
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
