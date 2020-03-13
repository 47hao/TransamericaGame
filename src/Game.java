import java.awt.Dimension;

import javax.swing.JFrame;

public class Game {

	private int games = 0;
	private boolean fast = false;

	private boolean gameOver = false;
	private boolean roundOver = false;

	private int turns = 0;
	// private int railsPlaced = 0;
	private Player[] players;
	private Board board;
	private Scoreboard scoreboard;
	private JFrame frame;
	private GamePanel panel;

	public Game(Player[] players) {
		frame = new JFrame();
		panel = new GamePanel(this);
		board = new Board();
		scoreboard = new Scoreboard();
		this.players = players;
		fast = false;
		games = 0;
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
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
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		play(true);
	}

	void placeMarkers(Player p) {
		Position pos = new Position(0, 0);
		// Find position of mouse click
		//p.setMarkerPos(pos);
	}

	Player[] play(boolean cpu) {
		
		while (!gameOver) {
			while (!roundOver) {
				for (Player p : players) {
					panel.repaint();

					if (turns == 0)
						placeMarkers(p);
					else {
						for (int i = 0; i < 2; i++) {
							//FIXME: aaaaaa
							
							// findValidRails(p);
							// int railIndex = -1;
							// do {
							// 	railIndex = p.getRail(validRails);
							// } while (railIndex == -1);
							// if (p instanceof HumanPlayer) {
							// 	validRails[railIndex].setState(Rail.NEW);
							// }
							// else {
							// 	validRails[railIndex].setState(Rail.BLINKING);
							// }
						}
					}
				}

				turns++;

				for (Player p : players) {
					roundOver = true;
					for (City c : p.getTargetCities()) {
						// if (p.getDistanceToCity(c) == 0)
						// 	roundOver = false;
					}
				}
			}

			// end of round
			turns = 0;

			for (Player p : players) {
				if (p.getScore() <= 0)
					gameOver = true;
			}
			roundOver = false;
		}

		// game over

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
