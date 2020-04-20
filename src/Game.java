import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game {

	private int games = 0;
	private boolean fast = false;

	private boolean gameOver = false;
	private boolean roundOver = false;

	private int turns;
	private int totalTurns;
	// private int railsPlaced = 0;
	private Player[] players;
	private Player currentPlayer;
	private Board board;
	private Scoreboard scoreboard;
	private JFrame frame;
	private GamePanel panel;

	// XXX: kinda temporary, just to test turns
	// turnRails stores the rails that make up a player's turn
	ArrayList<Rail> turnRails;
	// recentRails is a queue that holds all recent rails placed
	// where recent refers to new rails placed since that player has had a turn
	LinkedList<ArrayList<Rail>> recentRails;

	// TODO: look at a better way to organize these constructors (don't repeat code)

	public Game(Player[] players) {
		gameOver = false;
		roundOver = false;

		recentRails = new LinkedList<ArrayList<Rail>>();
		turnRails = new ArrayList<Rail>();

		frame = new JFrame();
		panel = new GamePanel(this);
		board = new Board();
		scoreboard = new Scoreboard(players);
		this.players = players;
		fast = false;
		// games = 0;
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(GamePanel.resolutionWidth, GamePanel.resolutionHeight));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

		new Thread(() -> {
			play(false);
		}).start();
	}

	public Game(Player[] players, boolean fast, int games) {
		gameOver = false;
		roundOver = false;

		frame = new JFrame();
		panel = new GamePanel(this);
		board = new Board();
		scoreboard = new Scoreboard(players);
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

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	// void placeMarkers(Player p) {
	// Position pos = new Position(0, 0);
	// // Find position of mouse click
	// //p.setMarkerPos(pos);
	// }

	// XXX:changed return type to void: Game is root, not returning to anywhere
	public void play(boolean cpu) {

		synchronized (this) {

			// first, place the markers for each player
			board.setGameState(Board.GS_MARKER);
			for (Player p : players) {
				currentPlayer = p;
				while (currentPlayer.getMarkerPos() == null) {
					try {
						// TODO: code strategy to notify() after marker placed
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("FINISHED THE THINGS, ABOUT TO CALC");
			// TODO: fix distance calculation
			calculateDistances();
			totalTurns = 0;
			panel.clearOutlinedPoint();
			while (!gameOver) {
				board.setGameState(Board.GS_ROUND);
				turns = 0;
				
				while (!roundOver) {

					for (Player p : players) {
						turns++;
						System.out.println("turn #" + turns);
						// p.calculateDistances();

						// each player places 2 rails per round
						board.setRemainingRails(2);
						currentPlayer = p;
						System.out.println("current player: " + currentPlayer.getName());
						turnRails = new ArrayList<Rail>();
						while (board.getRemainingRails() > 0) {

							currentPlayer.setValidRails(board.computePossiblePlacements(currentPlayer));
							
							try {
								wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							// turnRails.add()
							calculateDistances();
							// rail placed
							if (turnRails.get(0).isDouble()) {
								board.setRemainingRails(0);
								// we can use a constant here because the loop always ends after
							} else {
								board.setRemainingRails(board.getRemainingRails() - 1);
							}
							// p.calculateDistances();
							checkCitiesReached(currentPlayer);
							if(currentPlayer.checkAllCitiesReached())
							{
								System.out.println("haha hyea");
								roundOver = true;
							}
						}
						addTurnRails(turnRails);
						/*
						roundOver = true;
						
						int[] distances = p.getDistancesToCities();
						for (int i = 0; i < distances.length; i++) {
							if (distances[i] > 0) {
								roundOver = false;
							}
						}
						*/
						
					}
				}
				// System.out.println("round over");
				board.setGameState(Board.GS_ROUND_END);
				// TODO: show round end dialog
				// TODO: increment scoreboard
				// TODO:
				// if (scoreboard.isGameOver()) {
				//gameOver = true;
				// }
				// else {
				new EndGame(getPlayers());
				totalTurns += turns;
				roundOver = false;
				// }
			}
			board.setGameState(Board.GS_GAME_END);
			// Player[] endResults = scoreboard.getEndResults();
		}
	}
	
	public void checkCitiesReached(Player p)
	{
		for(City c: p.getTargetCities())
			for(Rail r: board.computeConnectedRails(p) )
				if(c.getPos().equals(r.startPos()) || c.getPos().equals(r.endPos()))
				{
					p.setCityReached(c);
					System.out.println("brokeloop");
					break;
				}
	}

	public void calculateDistances() {
		for (Player player : players) {
			int[] dist = new int[6];
			for (int i = 0; i < player.getTargetCities().size(); i++) {
				//dist[i] = board.getDistancetoCity(player, player.getTargetCities().get(i));
				dist[i] = 1;
			}
			player.setDistancesToCities(dist);
		}
	}

	public void addTurnRails(ArrayList<Rail> turn) {
		recentRails.add(turn);
		// if queue is too large (turns have wrapped around), trim queue
		while (recentRails.size() > players.length - 1) {
			recentRails.poll();
		}
	}

	public ArrayList<Rail> getTurnRails() {
		return turnRails;
	}

	public LinkedList<ArrayList<Rail>> getRecentRails() {
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
