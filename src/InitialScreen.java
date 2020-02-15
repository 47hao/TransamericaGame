import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class InitialScreen {
    //XXX: note that this class is just a proof of concept
    //this class DOES NOT include player name prompts or computer strategy radio buttons
    //TODO: change layout to GridBagLayout
    //https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
    
    //player labels (Player 1:, etc.)
    final JLabel[] playerPrompts = new JLabel[6];
    //6 groups of radio buttons for the 6 possible players
    final JRadioButton[] radioGroup1 = new JRadioButton[3];
    final JRadioButton[] radioGroup2 = new JRadioButton[3];
    final JRadioButton[] radioGroup3 = new JRadioButton[3];
    final JRadioButton[] radioGroup4 = new JRadioButton[3];
    final JRadioButton[] radioGroup5 = new JRadioButton[3];
    final JRadioButton[] radioGroup6 = new JRadioButton[3];
    //class to group radio buttons
    final ButtonGroup[] buttonGroups = new ButtonGroup[6];

    JFrame frame;
    JPanel panel;

    public InitialScreen() {
        frame = new JFrame("Transamerica Player Selection Screen");
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 4)); //gridlayout with 7 rows, 4 columns (will change later)
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        //create player labels
        for (int i = 0; i < playerPrompts.length; i++) {
            playerPrompts[i] = new JLabel("Player " + (i + 1) + ":");
        }

        //create a list of all groups of radio buttons
        JRadioButton[][] radioGroups = { radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6 };

        //create radio button groups (6 groups for 6 players)
        for (int i = 0; i < buttonGroups.length; i++) {
            buttonGroups[i] = new ButtonGroup();
        }

        //for every group of radio buttons, create human, computer, none
        //then, add each radio button to respective group
        for (int i = 0; i < radioGroups.length; i++) {
            radioGroups[i][0] = new JRadioButton("Human");
            radioGroups[i][1] = new JRadioButton("Computer");
            radioGroups[i][2] = new JRadioButton("None");
            for (int j = 0; j < radioGroups[i].length; j++) {
                buttonGroups[i].add(radioGroups[i][j]);
            }
        }

        //add player label followed by each radio button group
        for (int i = 0; i < radioGroups.length; i++) {
            panel.add(playerPrompts[i]);
            for (int j = 0; j < radioGroups[i].length; j++) {
                panel.add(radioGroups[i][j]);
            }
        }

        //add empty space before play and exit buttons to center them
        panel.add(new JLabel());
        //add play/exit buttons:
        panel.add(new JButton("Play"));
        panel.add(new JButton("Exit"));


        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InitialScreen();
        });
    }
}