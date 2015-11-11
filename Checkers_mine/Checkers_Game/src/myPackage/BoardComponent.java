package myPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

/*
 * Class which creates board
 */
public class BoardComponent extends JComponent implements ActionListener, MouseListener {

	// board size
	private static final int PREF_W = 400;
	private static final int PREF_H = 400;

	public BoardComponent() {
		addMouseListener(this);
		CheckersGame.startButton.addActionListener(this);
		CheckersGame.stopButton.addActionListener(this);

	}

	@Override
	public Dimension getPreferredSize() {// Necessary method to display
											// component!
		/**
		 * Try overriding public Dimension getPreferredSize() in your class so
		 * that GridBagLayout knows how big to make your panel.
		 */
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	public void paintComponent(Graphics g) {

		// border around canvas
		setBorder(new LineBorder(Color.black));

		// draw square fields
		setBackground(Color.decode("#00cccc"));
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row % 2 == 0 && col % 2 == 0 || row % 2 != 0 && col % 2 != 0) {// light
																					// fields

					g.setColor(Color.decode("#EED5B7"));
					g.fillRect(col * 50, row * 50, 50, 50);

				} else {// dark fields
					g.setColor(Color.decode("#5F4F31"));
					g.fillRect(col * 50, row * 50, 50, 50);

				}
				// draw current checkers on board
				switch (GameFlow.boardData.getFieldOnBoard(row, col)) {

				case GameData.WHITE:
					g.setColor(Color.WHITE);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
					break;
				case GameData.WHITE_QUEEN:
					g.setColor(Color.WHITE);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
					g.setColor(Color.BLACK);
					g.drawString("Q", (col * 50) + 20, (row * 50) + 30);// draw
																		// queen
					break;
				case GameData.BLACK:
					g.setColor(Color.BLACK);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
					break;
				case GameData.BLACK_QUEEN:
					g.setColor(Color.BLACK);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
					g.setColor(Color.WHITE);
					g.drawString("Q", (col * 50) + 20, (row * 50) + 30);// draw
																		// queen
					break;

				}

			}

		}

		if (GameFlow.gameRunning) {

			/*
			 * Draw a border around the pieces that can be moved.
			 */
			g.setColor(Color.yellow);

			for (int i = 0; i < GameFlow.possibleMoves.length; i++) {

				g.drawRect(GameFlow.possibleMoves[i].getMoveFromCol() * 50, GameFlow.possibleMoves[i].getMoveFromRow() * 50, 49,
						49);
			}
			/*
			 * When checker is selected then draw a green border (selectedRow >=
			 * 0) and red border around fields where can be moved
			 */

			if (GameFlow.chosenRow >= 0) {
				g.setColor(Color.green);
				g.drawRect(GameFlow.chosenCol * 50, GameFlow.chosenRow * 50, 49, 49);
				g.setColor(Color.RED);
				for (int i = 0; i < GameFlow.possibleMoves.length; i++) {
					if (GameFlow.possibleMoves[i].getMoveFromCol() == GameFlow.chosenCol
							&& GameFlow.possibleMoves[i].getMoveFromRow() == GameFlow.chosenRow) {
						g.drawRect(GameFlow.possibleMoves[i].getMoveToCol() * 50, GameFlow.possibleMoves[i].getMoveToRow() * 50,
								49, 49);
					}
				}
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * Get coordinates of chosen checker and  react
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (GameFlow.gameRunning == false)
			CheckersGame.infoLabel.setText("Click START button to start a new game");
		else {
			int col = (e.getX() / 50);
			int row = (e.getY() / 50);
			if (col >= 0 && col < 8 && row >= 0 && row < 8)
				GameFlow.makeClick(row, col);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * Handle actions for buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == CheckersGame.startButton)
			GameFlow.startNewGame();
		else if (e.getSource() == CheckersGame.stopButton)
			GameFlow.resignGame();
	}

}