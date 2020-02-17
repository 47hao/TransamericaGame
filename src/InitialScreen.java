import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * 
 * Note: Add an extra row between every existing player choice row Extra row
 * will include: Player name input (pre-filled with Player $n) Computer strategy
 * choice box
 * 
 * 
 */

public class InitialScreen {
    // XXX: note that this class is just a proof of concept
    // this class DOES NOT include player name prompts or computer strategy radio
    // buttons
    // TODO: include computer strategy JComboBox to switch difficulty
    // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

    // player labels (Player 1:, etc.)
    final JLabel[] playerPrompts = new JLabel[6];
    // 6 groups of radio buttons for the 6 possible players
    final JRadioButton[] radioGroup1 = new JRadioButton[3];
    final JRadioButton[] radioGroup2 = new JRadioButton[3];
    final JRadioButton[] radioGroup3 = new JRadioButton[3];
    final JRadioButton[] radioGroup4 = new JRadioButton[3];
    final JRadioButton[] radioGroup5 = new JRadioButton[3];
    final JRadioButton[] radioGroup6 = new JRadioButton[3];
    // class to group radio buttons
    final ButtonGroup[] buttonGroups = new ButtonGroup[6];

    // create combo boxes for computer strategy choosers
    final JComboBox<String>[] strategyChooserBoxes = new JComboBox[6];

    //create player name input fields
    final JTextField[] nameTextFields = new JTextField[6];

    final static int LEFT_PADDING = 15;
    // vertical padding high is used for space from first row to top
    final static int VERTICAL_PADDING_FIRST = 50;
    // veritcal padding last is used for space between last row and buttons
    final static int VERTICAL_PADDING_LAST = 10;
    // vertical padding low is used for padding between rows in the middle
    final static int VERTICAL_PADDING_LOW = 5;

    JFrame frame;
    JPanel panel;

    public InitialScreen() {
        frame = new JFrame("Player Selection");
        panel = new JPanel(new GridBagLayout());
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create player labels
        for (int i = 0; i < playerPrompts.length; i++) {
            playerPrompts[i] = new JLabel("Player " + (i + 1) + ":");
        }

        // create a list of all groups of radio buttons
        JRadioButton[][] radioGroups = { radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6 };

        // create radio button groups (6 groups for 6 players)
        for (int i = 0; i < buttonGroups.length; i++) {
            buttonGroups[i] = new ButtonGroup();
        }

        // for every group of radio buttons, create human, computer, none
        // then, add each radio button to respective group
        for (int i = 0; i < radioGroups.length; i++) {
            radioGroups[i][0] = new JRadioButton("Human");
            radioGroups[i][1] = new JRadioButton("Computer");
            radioGroups[i][2] = new JRadioButton("None");
            //set all players to default to none
            radioGroups[i][2].setSelected(true);

            for (int j = 0; j < radioGroups[i].length; j++) {
                buttonGroups[i].add(radioGroups[i][j]);
            }
        }

        //add radioListener to every radio button
        for (int i = 0; i < radioGroups.length; i++) {
            for (int j = 0; j < radioGroups[i].length; j++) {
                radioGroups[i][j].addItemListener(new RadioListener());
            }
        }

        // set each combo box to hold two choices: basic/expert strategy
        for (int i = 0; i < strategyChooserBoxes.length; i++) {
            strategyChooserBoxes[i] = new JComboBox<String>(new String[] { "Basic", "Expert" });
            //hide all initially because computer player is not selected
            strategyChooserBoxes[i].setVisible(false);
        }

        //set player text fields to default: player $i:
        for (int i = 0; i < nameTextFields.length; i++) {
            nameTextFields[i] = new JTextField("Player " + (i+1), 7);
            //set invisible by default because human not selected
            nameTextFields[i].setVisible(false);
        }

        // create constraint object to be edited:
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        // c.fill = GridBagConstraints.VERTICAL;
        c.ipadx = 5;
        c.ipady = 5;

        // add player label followed by each radio button group
        for (int i = 0; i < radioGroups.length * 2; i += 2) {
            if (i == 0) {
                c.insets = new Insets(VERTICAL_PADDING_FIRST, LEFT_PADDING, 10, 20);
            } else {
                // if not first row, have less vertical padding
                c.insets = new Insets(VERTICAL_PADDING_LOW, LEFT_PADDING, 10, 20);
            }
            c.gridx = 0;
            c.gridy = i;
            panel.add(playerPrompts[i / 2], c);
            for (int j = 0; j < radioGroups[i / 2].length; j++) {
                if (i == 0) {
                    c.insets = new Insets(VERTICAL_PADDING_FIRST, LEFT_PADDING, 10, 20);
                } else {
                    // if not first row, have less vertical padding
                    c.insets = new Insets(VERTICAL_PADDING_LOW, LEFT_PADDING, 10, 20);
                }
                c.gridx = j + 1;
                c.gridy = i;
                panel.add(radioGroups[i / 2][j], c);

                //if radio button is for player, add text box below it
                if (j == 0) {
                    c.gridy = i + 1; //next row
                    c.insets = new Insets(0, 0, 0, 0);
                    c.fill = GridBagConstraints.NONE;
                    panel.add(nameTextFields[i / 2], c);
                    c.fill = GridBagConstraints.HORIZONTAL;
                }

                // if radio button is for computer, add combo box below it
                if (j == 1) {
                    c.gridy = i + 1; //next row
                    c.insets = new Insets(0, 0, 0, 0);
                    c.fill = GridBagConstraints.NONE;
                    panel.add(strategyChooserBoxes[i / 2], c);
                    c.fill = GridBagConstraints.HORIZONTAL;
                }
            }
        }

        // add empty space before play and exit buttons to center them
        panel.add(new JLabel());

        // set constraints for play/exit buttons:
        c.gridx = 1;
        c.gridy = radioGroups.length * 2;
        c.insets = new Insets(VERTICAL_PADDING_LAST, LEFT_PADDING, 10, 10);

        // add play/exit buttons:
        panel.add(new JButton("Play"), c);
        c.gridx = 2;
        panel.add(new JButton("Exit"), c);

        frame.pack();
        frame.setVisible(true);
    }

    class RadioListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JRadioButton[][] radioGroups = { radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5,
                    radioGroup6 };
            Object o = e.getSource();
            // group refers to which radioGroup contains the source
            int group = -1;
            // button refers to the index of the button that was clicked
            int button = -1;
            for (int i = 0; i < radioGroups.length; i++) {
                for (int j = 0; j < radioGroups[i].length; j++) {
                    if (radioGroups[i][j] == o) {
                        group = i;
                        button = j;
                    }
                }
            }
            if (button == 0) { //human
                //show player name input
                nameTextFields[group].setVisible(true);
                strategyChooserBoxes[group].setVisible(false);
            }
            else if (button == 1) { //computer
                //hide player name input
                nameTextFields[group].setVisible(false);
                strategyChooserBoxes[group].setVisible(true);
            }
            else { //none
                //hide player name input
                nameTextFields[group].setVisible(false);
                strategyChooserBoxes[group].setVisible(false);
            }
            frame.pack();
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InitialScreen();
        });
    }
}