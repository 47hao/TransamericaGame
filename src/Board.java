  
import java.util.ArrayList;
import java.util.Queue;

public class Board {
	String gameState;
	Queue<Rail> newRails;
	Player activePlayer;
	final Position[] positions = new Position[100]; //temp
	final City[] cities = new City[20]; // temp
	final Rail[] rails = new Rail[100]; // temp
	ArrayList<Position> possiblePlacements= new ArrayList<Position>(0);
	ArrayList<Player> playerArray= new ArrayList<Player>(0);

	public Board() {
		
	}

	public void addPlayer(Player p) {
		playerArray.add(p);
	}

	public Rail getRail(Position start, Position end){
		for(int i=0;i<rails.length;i++) {
			if (rails[i].startPoint.equals(start)&&rails[i].endPoint.equals(end)) {
				return rails[i];
			}
		}
		return null;
	}
	public Rail[] computeConnectedRails(Player p) {

	}

	int getDistancetoCity(Player p, City c) {

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
		activePlayer=p;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}

	public void setGameState(String state) {
		gameState=state;
	}
	
	public String getGameState() {
		return gameState;
	}
	
}
