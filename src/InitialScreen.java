import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class InitialScreen {

    // player labels (Player 1:, etc.)
    final JLabel[] playerPrompts = new JLabel[6];
    // 6 groups of radio buttons for the 6 possible players
    final JRadioButton[] radioGroup1 = new JRadioButton[3];
    final JRadioButton[] radioGroup2 = new JRadioButton[3];
    final JRadioButton[] radioGroup3 = new JRadioButton[3];
    final JRadioButton[] radioGroup4 = new JRadioButton[3];
    final JRadioButton[] radioGroup5 = new JRadioButton[3];
    final JRadioButton[] radioGroup6 = new JRadioButton[3];

    final JRadioButton[][] radioGroups = { radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5,
            radioGroup6 };

    // class to group radio buttons
    final ButtonGroup[] buttonGroups = new ButtonGroup[6];

    // create combo boxes for computer strategy choosers
    final JComboBox<String>[] strategyChooserBoxes = new JComboBox[6];

    // create player name input fields
    final JTextField[] nameTextFields = new JTextField[6];

    final static int TOP_PADDING = 10;
    final static int LEFT_PADDING = 15;
    final static int BOTTOM_PADDING = 15;
    final static int RIGHT_PADDING = 20;

    JFrame frame;
    JPanel panel;

    JButton playButton;

    public InitialScreen() {
        frame = new JFrame("Player Selection");
        panel = new JPanel(new GridBagLayout());
        frame.setContentPane(panel);
        frame.setPreferredSize(new Dimension(600, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create title label
        JLabel title = new JLabel("Transamerica");
        title.setFont(new Font("Arial", Font.BOLD, 36));

        // create player labels
        for (int i = 0; i < playerPrompts.length; i++) {
            playerPrompts[i] = new JLabel("Player " + (i + 1) + ":");
        }

        // XXX: the coloring system is subject to change!

        // set each label opaque to allow background colors
        for (int i = 0; i < playerPrompts.length; i++) {
            playerPrompts[i].setOpaque(true);
        }
        playerPrompts[0].setBackground(Color.CYAN);
        playerPrompts[1].setBackground(Color.YELLOW);
        playerPrompts[2].setBackground(Color.RED);
        playerPrompts[3].setBackground(Color.GREEN);
        playerPrompts[4].setBackground(Color.MAGENTA);
        playerPrompts[5].setBackground(Color.ORANGE);

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
            // set all players to default to none
            radioGroups[i][2].setSelected(true);

            for (int j = 0; j < radioGroups[i].length; j++) {
                buttonGroups[i].add(radioGroups[i][j]);
            }
        }

        // add radioListener to every radio button
        for (int i = 0; i < radioGroups.length; i++) {
            for (int j = 0; j < radioGroups[i].length; j++) {
                radioGroups[i][j].addItemListener(new RadioListener());
            }
        }

        // set each combo box to hold two choices: basic/expert strategy
        for (int i = 0; i < strategyChooserBoxes.length; i++) {
            strategyChooserBoxes[i] = new JComboBox<String>(new String[] { "Basic", "Expert" });
            // hide all initially because computer player is not selected
            strategyChooserBoxes[i].setVisible(false);
        }

        // set player text fields to default: player $i:
        for (int i = 0; i < nameTextFields.length; i++) {
            nameTextFields[i] = new JTextField("Player " + (i + 1), 7);
            // set invisible by default because human not selected
            nameTextFields[i].setVisible(false);
        }

        // create constraint object to be edited:
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        // c.fill = GridBagConstraints.VERTICAL;
        c.ipadx = 5;
        c.ipady = 5;

        // make title take up two spaces
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(5, 1, 20, 1);
        panel.add(title, c);

        // reset grid width
        c.gridwidth = 1;

        // add player label followed by each radio button group
        for (int i = 0; i < radioGroups.length * 2; i += 2) {
            // if (i == 0) {
            // c.insets = new Insets(VERTICAL_PADDING_FIRST, LEFT_PADDING, 10, 20);
            // } else {
            // if not first row, have less vertical padding
            c.insets = new Insets(TOP_PADDING, LEFT_PADDING, RIGHT_PADDING, BOTTOM_PADDING);
            // }
            c.gridx = 0;
            c.gridy = (i + 1); // offset from title label
            panel.add(playerPrompts[i / 2], c);
            for (int j = 0; j < radioGroups[i / 2].length; j++) {
                // if (i == 0) {
                // c.insets = new Insets(VERTICAL_PADDING_FIRST, LEFT_PADDING, 10, 20);
                // } else {
                // if not first row, have less vertical padding
                c.insets = new Insets(TOP_PADDING, LEFT_PADDING, RIGHT_PADDING, BOTTOM_PADDING);
                // }
                c.gridx = j + 1;
                c.gridy = (i + 1); // offset from title label
                panel.add(radioGroups[i / 2][j], c);

                // if radio button is for player, add text box below it
                if (j == 0) {
                    c.gridy = (i + 1) + 1; // next row + offset from title label
                    c.insets = new Insets(0, 0, 0, 0);
                    c.fill = GridBagConstraints.NONE;
                    panel.add(nameTextFields[i / 2], c);
                    c.fill = GridBagConstraints.HORIZONTAL;
                }

                // if radio button is for computer, add combo box below it
                if (j == 1) {
                    c.gridy = (i + 1) + 1; // next row + offset from title label
                    c.insets = new Insets(0, 0, 0, 0);
                    c.fill = GridBagConstraints.NONE;
                    panel.add(strategyChooserBoxes[i / 2], c);
                    c.fill = GridBagConstraints.HORIZONTAL;
                }
            }
        }

        // add empty space before play and exit buttons to center them
        panel.add(new JLabel());

        // create play/exit buttons:
        playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");

        // make play button disabled by default
        playButton.setEnabled(false);

        // set action commands:
        playButton.setActionCommand("Play");
        exitButton.setActionCommand("Exit");

        // create button listener to handle button presses
        ControlButtonListener controlListener = new ControlButtonListener();

        // add actionlistener to buttons
        playButton.addActionListener(controlListener);
        exitButton.addActionListener(controlListener);

        // set constraints for play/exit buttons:
        c.gridx = 1;
        c.gridy = radioGroups.length * 2 + 1; // offset from title label
        c.insets = new Insets(5, LEFT_PADDING, RIGHT_PADDING, BOTTOM_PADDING);

        // add buttons to panel
        panel.add(playButton, c);
        c.gridx = 2; // makes exit button next to play button
        panel.add(exitButton, c);

        frame.pack();
        frame.setVisible(true);
    }

    class RadioListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
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
            if (button == 0) { // human
                // show player name input
                nameTextFields[group].setVisible(true);
                strategyChooserBoxes[group].setVisible(false);
            } else if (button == 1) { // computer
                // hide player name input
                nameTextFields[group].setVisible(false);
                strategyChooserBoxes[group].setVisible(true);
            } else { // none
                     // hide player name input
                nameTextFields[group].setVisible(false);
                strategyChooserBoxes[group].setVisible(false);
            }

            int playerCount = 0;

            // count number of players to enable/disable play button
            for (int i = 0; i < radioGroups.length; i++) {
                for (int j = 0; j < radioGroups[i].length; j++) {
                    if (radioGroups[i][j].isSelected()) {
                        if (!radioGroups[i][j].getText().equals("None")) {
                            playerCount++;
                        }
                    }
                }
            }

            // if less than 2 players, disable play button
            if (playerCount < 2) {
                playButton.setEnabled(false);
            } else {
                playButton.setEnabled(true);
            }
            frame.pack();
        }

    }

    class ControlButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Exit")) {
                // dialog box to confirm exit?
                System.exit(1);
            } else {
                ArrayList<String> players = new ArrayList<String>();
                for (int i = 0; i < radioGroups.length; i++) {
                    for (int j = 0; j < radioGroups[i].length; j++) {
                        if (radioGroups[i][j].isSelected()) {
                            // if player is human, take their name input
                            if (radioGroups[i][j].getText().equals("Human")) {
                                players.add(nameTextFields[i].getText());
                            }
                            // TODO: update naming scheme
                            // if computer player, name them $difficulty CPU $playerPos
                            // ex: expert CPU 2 (in second player position)
                            else if (radioGroups[i][j].getText().equals("Computer")) {
                                players.add(strategyChooserBoxes[i].getSelectedItem().toString() + " CPU " + (i + 1));
                            } else {
                                // none is selected, don't add to player list
                            }
                        }
                    }
                }
                // XXX: the stubs below are proof of concept code
                System.out.println("Players: ");
                for (String s : players) {
                    if (s.isEmpty()) {
                        players.set(players.indexOf(s), "Player " + (players.indexOf(s) + 1));
                    }
                }
                for (int i = 0; i < players.size(); i++) {
                    System.out.println(players.get(i));
                }
                
                boolean computersOnly = true;
                for (String s : players) {
                    if (!s.contains("CPU")) {
                        computersOnly = false;
                    }
                }
                if (computersOnly) {
                    System.out.println("Strategy Evaluation mode is on!");
                    StrategyEvalPanel p = new StrategyEvalPanel();
                    JFrame evalFrame = new JFrame("Strategy Evaluator");
                    evalFrame.setContentPane(p);
                    evalFrame.setPreferredSize(new Dimension(400, 200));
                    evalFrame.pack();
                    evalFrame.setVisible(true);
                }
            }
        }
    }

    class StrategyEvalPanel extends JPanel {
        JTextField numGamesField;
        JRadioButton fastButton;
        JRadioButton slowButton;
        JLabel numPrompt;
        JLabel speedPrompt;
        ButtonGroup g;
        JButton confirmButton;

        public StrategyEvalPanel() {
            g = new ButtonGroup();
            setLayout(new GridLayout(0, 2));
            numPrompt = new JLabel("Enter number of games:");
            speedPrompt = new JLabel("Enter a game speed: ");
            numGamesField = new JTextField();
            fastButton = new JRadioButton("Fast");
            slowButton = new JRadioButton("Slow");
            g.add(fastButton);
            g.add(slowButton);

            confirmButton = new JButton("Confirm");
            confirmButton.addActionListener(new ConfirmButtonListener());
            
            add(numPrompt);
            add(numGamesField);
            add(speedPrompt);
            add(new JLabel());
            add(fastButton);
            add(slowButton);

            add(new JLabel());
            add(confirmButton);
        }

        class ConfirmButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                int numGames = -1;
                try {
                    numGames = Integer.parseInt(numGamesField.getText());
                    if (numGames < 1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Enter a valid number of games", "Error", JOptionPane.ERROR_MESSAGE, null);
                    return;
                }
                if (!fastButton.isSelected() && !slowButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Choose a game speed", "Error", JOptionPane.ERROR_MESSAGE, null);
                    return;
                }
                String speed = fastButton.isSelected() ? "fast" : "slow";

                System.out.println("numGames: " + numGames);
                System.out.println("mode: " + speed);

            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InitialScreen();
        });
    }
}