import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import javax.swing.*;

public class EndGame extends JPanel{
	JFrame frame;
	JPanel panel;
	JLabel title;
	JLabel ranking;
	private Scoreboard scoreboard;
	EndGame(){
		frame = new JFrame("Game Over");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel = new JPanel(new GridBagLayout());
		frame.setContentPane(panel);
		frame.setPreferredSize(new Dimension(600, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel title = new JLabel("Game Over");
		title.setFont(new Font("Arial", Font.BOLD, 36));
		JLabel ranking = new JLabel("Final Ranking");
		ranking.setForeground(Color.white);
		EndGame a = new EndGame();
		frame.add(a);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public void paintComponent(Graphics g) {
		int temp=scoreboard.getWinnerOrder().size();
		int yCoord =350; 
		for (int i=0; i<temp; i++) {
			g.drawString(scoreboard.getWinnerOrder().get(i), 300, yCoord+30);
		}
		
	}
	
	public static void main(String args[]) {
		
	}

}
