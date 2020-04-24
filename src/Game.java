import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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

			ArrayList<City> orangeCities = new ArrayList<City>();
			ArrayList<City> blueCities = new ArrayList<City>();
			ArrayList<City> yellowCities = new ArrayList<City>();
			ArrayList<City> redCities = new ArrayList<City>();
			ArrayList<City> greenCities = new ArrayList<City>();
			ArrayList[] cityLists = { orangeCities, blueCities, yellowCities, redCities, greenCities };

			System.out.println("FINISHED THE THINGS, ABOUT TO CALC");
			totalTurns = 0;
			while (!gameOver) {
				turns = 0;

				board = new Board();

				// reset board
				for (int i = 0; i < cityLists.length; i++) {
					for (int j = i * 7; j < 7 * (i + 1); j++) {
						cityLists[i].add(Board.cities[j]);
					}
				}

				panel.clearOutlinedPoint();

				for (Player player : players) {
					player.clearCities();
				}

				Random r = new Random();
				for (Player player : players) {
					for (int i = 0; i < cityLists.length; i++) {
						player.getTargetCities().add((City) cityLists[i].remove(r.nextInt(cityLists[i].size())));
					}
					player.initTargetCities();
				}

				for (Player player : players)
					player.clearMarker();

				board.setGameState(Board.GS_MARKER);
				for (Player p : players) {
					currentPlayer = p;
					if (p.isComputer) {
						ArrayList<Position> otherPlayerMarkers = new ArrayList<Position>();
						for (Player otherPlayer : players) {
							otherPlayerMarkers.add(otherPlayer.getMarkerPos());
						}
						p.setMarkerPos(p.getMarker(board, otherPlayerMarkers));
						System.out.println("cpu marker placed");
					} else {
						while (currentPlayer.getMarkerPos() == null) {
							try {
								// TODO: code strategy to notify() after marker placed
								wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					p.getConnectedPoints().add(p.getMarkerPos());
				}
				panel.clearOutlinedPoint();

				// TODO: fix distance calculation
				calculateDistances();

				board.setGameState(Board.GS_ROUND);

				while (!roundOver) {

					for (Player p : players) {
						turns++;
						System.out.println("turn #" + turns);
						// p.calculateDistances();

						checkCitiesReached(currentPlayer);

						// each player places 2 rails per round
						board.setRemainingRails(2);
						currentPlayer = p;
						System.out.println("current player: " + currentPlayer.getName());
						turnRails = new ArrayList<Rail>();
						while (board.getRemainingRails() > 0) {

							for (Player player : players) {
								if (player.checkAllCitiesReached()) {
									roundOver = true;
									board.setGameState(Board.GS_ROUND_END);
									board.setRemainingRails(0);
								}
							}

							if (!roundOver) {
								currentPlayer.setValidRails(board.computePossiblePlacements(currentPlayer));

								if (p.isComputer()) {

									int railIndex = p.getRail(
											p.getValidRails().toArray(new Rail[p.getValidRails().size()]), board);
									Rail selectedRail = p.getValidRails().get(railIndex);
									turnRails.add(selectedRail);
									board.setRailState(selectedRail, Rail.PLACED);
								} else {
									try {
										wait();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								// p.getConnectedPoints().addRailPositions();
								addRailPositions(p);
								// turnRails.add()
								calculateDistances();
								addTurnRails(turnRails);
								/*
								 * roundOver = true;
								 * 
								 * int[] distances = p.getDistancesToCities(); for (int i = 0; i <
								 * distances.length; i++) { if (distances[i] > 0) { roundOver = false; } }
								 */
							}
						}
					}
				}
				System.out.println("round over");
				// TODO: show round end dialog
				// TODO: increment scoreboard
				totalTurns += turns;
				int[] scores = new int[players.length];
				for (int i = 0; i < scores.length; i++) {
					// scores[i] = distance thing
					scores[i] = (int) (Math.random() * 20);
				}
				scoreboard.addScores(scores);
				// maybe find a way to not activate when game end? if (!gameOver) doesn't work
				new EndGame(getPlayers(), "Round End");
				roundOver = false;
			}

			ArrayList<String> endResults = scoreboard.gameOver();
		}
	}

	public void checkCitiesReached(Player p) {
		for (City c : p.getTargetCities())
			for (Rail r : board.computeConnectedRails(p))
				if (c.getPos().equals(r.startPos()) || c.getPos().equals(r.endPos())) {
					p.setCityReached(c);
					System.out.println("brokeloop");
					break;
				}
	}

	public void gameOver() {
		gameOver = true;
		board.setGameState(Board.GS_GAME_END);
		for (Player p : players) {
			if (p.getScore() > 13)
				p.setScore(0);
		}
	}

	public void addRailPositions(Player p) {
		for (Player player : players) {
			if (!(player == p)) {
				for (Position pos : p.getConnectedPoints()) {
					if (player.getConnectedPoints().contains(pos)) {
						p.getConnectedPoints().addAll(player.getConnectedPoints());
						player.getConnectedPoints().addAll(p.getConnectedPoints());
					}
				}
			}
		}
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public void calculateDistances() {
		for (Player player : players) {
			int[] dist = new int[6];
			for (int i = 0; i < player.getTargetCities().size(); i++) {
				dist[i] = board.getDistancetoCity(player, player.getTargetCities().get(i));
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
