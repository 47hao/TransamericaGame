import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Instructions {

	JFrame frame;
	JPanel panel;

	public Instructions() {
		frame = new JFrame("Instructions");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.setContentPane(panel);
		frame.setPreferredSize(new Dimension(600, 700));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Instructions();
		});
	}
}