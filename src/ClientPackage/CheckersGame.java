package ClientPackage;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Main class for the game. Rules:
 * 1)White start a game
 * 2)Beating is mandatory
 * 3)Normal checker can't move back or beat to back
 * 4)Queen can move any direction, any number of fields
 * 5)Queen can be beaten by normal checker
 */
@SuppressWarnings("unused")
public class CheckersGame {

	private JPanel thePanel = new JPanel();
	static JButton startButton = new JButton("Start"); // Button to start a new
														// // game.
	static JButton stopButton = new JButton("Stop"); // Button to stop the game.
	static JLabel infoLabel = new JLabel();// label to display
															// // messages

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				JFrame window = new JFrame("Checkers Game");
				window.setPreferredSize(new Dimension(550, 550));
				window.setContentPane(new CheckersGame().getThePanel());
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.pack();
				window.setResizable(false);
				window.setLocationRelativeTo(null);
				window.setVisible(true);

			}
		});

	}

	protected JPanel getThePanel() {
		return thePanel;
	}

	/**
	 * Constructor creates the Board and view
	 */
	public CheckersGame() {

		thePanel.setLayout(new GridBagLayout());

		GameFlowClient game = new GameFlowClient();

		BoardComponent boardComponent = new BoardComponent();// drawing board

		Font font = new Font("Helvetica", Font.PLAIN, 18);
		infoLabel.setFont(font);

		Box theBox = Box.createVerticalBox();
		theBox.add(startButton);
		theBox.add(Box.createVerticalStrut(30));// okreslony odstep
		theBox.add(stopButton);

		addComp(thePanel, infoLabel, 0, 0, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE);
		addComp(thePanel, boardComponent, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		addComp(thePanel, theBox, 0, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);

	}

	// Sets the rules for a component destined for a GridBagLayout
	// and then add it
	private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place,
			int stretch) {

		GridBagConstraints gridConstraints = new GridBagConstraints();

		gridConstraints.gridx = xPos;
		gridConstraints.gridy = yPos;
		gridConstraints.gridwidth = compWidth;
		gridConstraints.gridheight = compHeight;
		gridConstraints.weightx = 50;
		gridConstraints.weighty = 50;
		gridConstraints.insets = new Insets(0, 20, 25, 20);// top,left,bottom,right
		gridConstraints.anchor = place;
		gridConstraints.fill = stretch;

		thePanel.add(comp, gridConstraints);

	}
}
