  
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
			if (rails[i].startPoint.equals(start)&&rails[i].endPoint.equals(end)) {
				return rails[i];
			}
		}
		return null;
	}

	public Rail[] getRails(Position pos) {
		Rail[] rl= new Rail[0];
		for(int i=0;i<rails.length;i++) {
			if (rails[i].startPoint.equals(pos)||rails[i].endPoint.equals(pos)) {
				Rail[] newRL= new Rail[rl.length+1];
				for(int j=0;j<rl.length;j++) {
					newRL[j]=rl[j];
				}
				newRL[rl.length+1]=rails[i];
				rl= newRL;
			}
		}
		return rl;
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
