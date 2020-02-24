import java.util.ArrayList;
import java.util.Queue;

public class Board {
	String gameState;
	Queue<Rail> newRails;
	TurnOrder[] turnOrder;
	Player activePlayer;
	final Position[] positions;
	final City[] cities;
	final Rail[] rails;
	ArrayList<possiblePlacement> possiblePlacements;
	
	Board() {
		rails = new Rail[1];
	}

	public void addPlayer(Player p) {

	}

	public Rail getRail(Position start, Position end){
		
	}

	public Rail getRail(Position pos) {

	}

	public void setRailState(Rail r, String state) {
		r.setState(state);
	}

	public Rail[] getRails() {
		return rails;
	}

	public Rail[] computeConenctedRails(Player p) {

	}

	int getDistancetoCity(Player p, City c) {

	}
}
