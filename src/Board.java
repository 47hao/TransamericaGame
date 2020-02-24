import java.util.Queue;

public class Board {
	final Rail[] rails;
	String gameState;
	Queue<Rail> newRails;

	Board() {
		rails = new Rail[1];
	}

	public void addPlayer(Player p) {

	}

	public Rail getRail(Position start, Position end) {
		return new Rail(start, end);
	}

	public Rail getRail(Position pos) {

	}

	public void setRailState(Rail r, String state) {
		r.setState(state);
	}

	public Rail[] getRails() {

	}

	public Rail[] computeConenctedRails(Player p) {

	}

	int getDistanceToCity(Player p, City c) {

	}
}
