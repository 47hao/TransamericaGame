import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game {

	private int games = 0;
	private boolean fast = false;

	private boolean gameOver = false;
	private boolean roundOver = false;

	private int turns;
	// private int railsPlaced = 0;
	private Player[] players;
	private Player currentPlayer;
	private Board board;
	private Scoreboard scoreboard;
	private JFrame frame;
	private GamePanel panel;

	// XXX: kinda temporary, just to test turns
	ArrayList<Rail> recentRails;

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
		new Thread(() -> {
			play(false);
		}).start();
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
		new Thread(() -> {
			play(true);
		}).start();
	}

	public Player getcurrentPlayer() {
		return currentPlayer;
	}

	// void placeMarkers(Player p) {
	// Position pos = new Position(0, 0);
	// // Find position of mouse click
	// //p.setMarkerPos(pos);
	// }

	Player[] play(boolean cpu) {

		synchronized (this) {

			// first, place the markers for each player
			board.setGameState(Board.GS_MARKER);
			for (Player p : players) {
				currentPlayer = p;
				while (currentPlayer.getMarkerPos() == null) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			panel.clearOutlinedPoint();
			turns = 0;
			while (!gameOver) {
				board.setGameState(Board.GS_ROUND);
				while (!roundOver) {
					for (Player p : players) {
						// each player places 2 rails per round
						board.setRemainingRails(2);
						currentPlayer = p;
						recentRails = new ArrayList<Rail>();
						while (board.getRemainingRails() > 0) {
							try {
								wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							// first rail placed
							if (recentRails.get(0).isDouble()) {
								board.setRemainingRails(0);
								// we can use a constant here because the loop always ends after
							} else {
								board.setRemainingRails(board.getRemainingRails() - 1);
							}

						}
						turns++;
					}
				}
				board.setGameState(Board.GS_ROUND_END);
				// TODO: show round end dialog
			}
			board.setGameState(Board.GS_GAME_END);
			// TODO: show game end dialog

		}
		return null;
	}

	public ArrayList<Rail> getRecentRails() {
		return recentRails;
	}

	// while (!gameOver) {
	// while (!roundOver) {
	// for (Player p : players) {
	// panel.repaint();
	// for (int i = 0; i < 2; i++) {
	// // FIXME: aaaaaa

	// // findValidRails(p);
	// // int railIndex = -1;
	// // do {
	// // railIndex = p.getRail(validRails);
	// // } while (railIndex == -1);
	// // if (p instanceof HumanPlayer) {
	// // validRails[railIndex].setState(Rail.NEW);
	// // }
	// // else {
	// // validRails[railIndex].setState(Rail.BLINKING);
	// // }
	// }
	// }
	// }

	// turns++;

	// for (Player p : players) {
	// roundOver = true;
	// for (City c : p.getTargetCities()) {
	// // if (p.getDistanceToCity(c) == 0)
	// // roundOver = false;
	// }
	// }
	// }

	// // end of round
	// turns = 0;

	// for (Player p : players) {
	// if (p.getScore() <= 0)
	// gameOver = true;
	// }
	// roundOver = false;
	// }

	// // game over

	// return players;

	// }

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
