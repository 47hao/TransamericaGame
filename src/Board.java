import java.util.ArrayList;
import java.util.Queue;

public class Board {
	String gameState;
	Queue<Rail> newRails;
	Player activePlayer;
	final Position[] positions;
	final City[] cities;
	final Rail[] rails;
	ArrayList<Position> possiblePlacements;
	
	Board() {
		rails = new Rail[1];
	}

	public void addPlayer(Player p) {

	}

	public Rail getRail(Position start, Position end){
		for(int i=0;i<rails.length;i++) {
			if (rails[i].startPoint==start&&rails[i].endPoint==end) {
				return rails[i];
			}
		}
		return null;
	}

	public Rail getRail(Position pos) {
		for(int i=0;i<rails.length;i++) {
			if (rails[i].startPoint==pos||rails[i].endPoint==pos) {
				return rails[i];
			}
		}
		return null;
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
