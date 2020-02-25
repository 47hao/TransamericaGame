import java.util.ArrayList;
import java.util.Queue;

public class Board {
	String gameState;
	Queue<Rail> newRails;
	Player activePlayer;
	final Position[] positions;
	final City[] cities = new City[20]; //temp
	final Rail[] rails = new Rail[100]; //temp
	ArrayList<Position> possiblePlacements;

	public Board() {
	}

	public void addPlayer(Player p) {

	}

	public Rail getRail(Position start, Position end) {

	}

	public Rail getRail(Position pos) {

	}

	public void setRailState(Rail r, String state) {
		r.setState(state);
	}

	public Rail[] getRails() {
		return rails;
	}
	
	public City[] getCities() {
		return cities;
	}
	
	public Rail[] computeConnectedRails(Player p) {

	}

	int getDistanceToCity(Player p, City c) {

	}
}
