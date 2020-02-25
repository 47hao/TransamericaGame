import javax.swing.JFrame;

public class Game {

	int turns = 0;
	int railsPlaced = 0;
	Player[] players;
	Board board;
	Scoreboard scoreboard;
	JFrame frame;
	GamePanel panel;

	public Game(Player[] players) {
		frame = new JFrame();
		panel = new GamePanel(this);
		board = new Board();
		scoreboard = new Scoreboard();
		this.players = players;
		
	}

	public Game(Player[] players, boolean fast, int turns) {

	}

	void placeMarkers() {
		
	}
	
	Player[] Play() {
		return players;
	}

	Board getBoard() {
		return board;
	}
	
	Scoreboard getSB() {
		return scoreboard;
	}
	
	Player[] getPlayers() {
		return players;
	}
	
}
