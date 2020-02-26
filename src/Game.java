import java.awt.Dimension;

import javax.swing.JFrame;

public class Game {

	int games = 0;
	boolean fast = false;

	boolean gameOver = false;
	boolean roundOver = false;

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
		fast = false;
		games = 0;
		frame.setContentPane(panel);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		play(false);
	}

	public Game(Player[] players, boolean fast, int games) {
		frame = new JFrame();
		panel = new GamePanel(this);
		board = new Board();
		scoreboard = new Scoreboard();
		this.players = players;
		this.fast = fast;
		this.games = games;
		play(true);
	}

	void placeMarkers() {

	}

	Player[] play(boolean cpu) {

		while (!gameOver) {
			while (!roundOver) {
				for (Player p : players) {
					panel.repaint();
				}
				for (Player p : players) {
					if (p.getTargetCities().size() == 0)
						roundOver = true;
				}
			}
			for (Player p : players) {
				if (p.score <= 0)
					gameOver = true;
			}
			roundOver = false;
		}

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
