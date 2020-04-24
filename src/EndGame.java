import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import javax.swing.*;

public class EndGame {
	// final static int OFFSET_X = 20;
	final static int OFFSET_Y = 30;

	String title;
	JFrame frame;
	EndPanel panel;

	private Player[] players;

	// private Scoreboard scoreboard;

	// XXX: Scoreboard isn't needed because Game class will be able to provide
	// XXX: the player array directly (Game class is the highest level)
	public EndGame(Player[] p, String titleStr) {
		players = p;
		
		title = titleStr;
		// scoreboard = new Scoreboard(p);
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel = new EndPanel();
		// panel.setBackground(new Color(233, 233, 233));
		panel.setBackground(Color.WHITE);
		frame.setContentPane(panel);
		frame.setPreferredSize(new Dimension(600, 550));
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JLabel title = new JLabel("Game Over");
		// title.setFont(new Font("Arial", Font.BOLD, 36));
		// JLabel ranking = new JLabel("Final Ranking");
		// ranking.setForeground(Color.WHITE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		// panel.repaint();
	}

	class EndPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			System.out.println(getBackground());
			Graphics2D g2d = (Graphics2D) g;

			g2d.setColor(new Color(110, 70, 0));
			for (int i = 0; i < 20; i++) {
				if (i < 7 || i > 14) {
					g2d.fillRect(3 + (getWidth() * i / 20), 0, 10, 45);
				}
			}
			g2d.setColor(Color.BLACK);

			g2d.setFont(new Font("Arial", Font.BOLD, 36));
			if (title.equals("Game End")) {
				String name = "";
				int score = 13;
				for (Player player : players) {
					if (player.getScore() < score) {
						score = player.getScore();
						name = player.getName();
					}
				}
				
				g2d.drawString(name + " has won!", panel.getWidth() / 3 - 30, 3 + OFFSET_Y);
				g2d.drawString("Game Results:", panel.getWidth() / 3, 33 + OFFSET_Y);
			} else
				g2d.drawString("Round Results:", panel.getWidth() / 3, 3 + OFFSET_Y);

			Stroke stroke = g2d.getStroke();

			g2d.setStroke(new BasicStroke(3f));
			g2d.setColor(Color.GRAY);
			g2d.drawLine(0, 3, getWidth(), 3);
			g2d.drawLine(0, 40, getWidth(), 40);

			g2d.setStroke(stroke);

			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, OFFSET_Y + 30, panel.getWidth(), OFFSET_Y + 60 * 3);

			for (int i = 0; i < players.length; i++) {
				if (i == 0) {
					g2d.setFont(new Font("Arial", Font.BOLD, 24));
					g2d.setColor(new Color(233, 215, 0).brighter());
				} else if (i == 1) {
					g2d.setFont(new Font("Arial", Font.BOLD, 18));
					g2d.setColor(Color.BLACK);
					// fm = getFontMetrics(getFont());
					// g2d.fillRect(panel.getWidth() / 5 - 5, OFFSET_Y + 70 - 5,
					// fm.stringWidth(players[1].getName()),
					// fm.getHeight());
					g2d.setColor(new Color(192, 192, 192));
				} else if (i == 2) {
					g2d.setFont(new Font("Arial", Font.BOLD, 16));
					g2d.setColor(new Color(205, 127, 50));
				} else {
					g2d.setFont(new Font("Arial", Font.BOLD, 14));
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString((i + 1) + ". " + players[i].getName(), panel.getWidth() / 5, OFFSET_Y + 70 * (1 + i));
			}
		}
	}

	// public static void main(String[] args) {

	// SwingUtilities.invokeLater(() -> {
	// new EndGame(new Player[] { new HumanPlayer("Human", Color.CYAN), new
	// HumanPlayer("Human 2", Color.YELLOW),
	// new HumanPlayer("Human 3", Color.GREEN), new HumanPlayer("Loser",
	// Color.GREEN),
	// new HumanPlayer("Loser", Color.GREEN), new HumanPlayer("Loser", Color.GREEN)
	// });
	// });
	// }
}