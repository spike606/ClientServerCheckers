package myPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class BoardComponent extends JComponent implements ActionListener, MouseListener{

	private static final int PREF_W = 400;
	private static final int PREF_H = 400;

	public  BoardComponent() {
		addMouseListener(this);	
		CheckersGame.startButton.addActionListener(this);
		CheckersGame.stopButton.addActionListener(this);

	}
	
	@Override
	public Dimension getPreferredSize() {//METODA KONIECZNA BO BEZ NIEJ NIE WYSWIETLA KOMPONENTU!!!
		/**
		 * Try overriding public Dimension getPreferredSize() in your 
		 *  class so that GridBagLayout knows how big to make your panel.
		 */
		return new Dimension(PREF_W, PREF_H);
	}

	public void paintComponent(Graphics g) {

		// border around canvas
		//g.setColor(Color.black);
		setBorder( new LineBorder(Color.black) );


		// draw square fields
		setBackground(Color.decode("#00cccc"));
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row % 2 == 0 && col % 2 == 0 || row % 2 != 0 && col % 2 != 0) {//jasne pola
					g.setColor(Color.decode("#EED5B7"));
					g.fillRect(col * 50, row * 50, 50, 50);
				


				} else{//ciemne pola
					g.setColor(Color.decode("#5F4F31"));
					g.fillRect(col * 50, row * 50, 50, 50);

				}
				//draw current checkers on board 
				switch(Game.boardData.getFieldOnBoard(row, col)){
				
				case GameData.WHITE:
					g.setColor(Color.WHITE);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);		
					break;
				case GameData.WHITE_QUEEN:
					g.setColor(Color.WHITE);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);		
					g.setColor(Color.BLACK);
					g.drawString("Q", (col * 50) + 20, (row * 50) + 30);
					break;
				case GameData.BLACK:
					g.setColor(Color.BLACK);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);		
					break;
				case GameData.BLACK_QUEEN:
					g.setColor(Color.BLACK);
					g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);		
					g.setColor(Color.WHITE);
					g.drawString("Q", (col * 50) + 20, (row * 50) + 30);
					break;
				
				
				}

			}

		}

		//higlight legal moves
		if(Game.gameRunning){

			/*
			 * First, draw a 2-pixel cyan border around the pieces that can
			 * be moved.
			 */
			g.setColor(Color.yellow);

			//g.drawRect(0, 250, 50, 50);

			for (int i = 0; i < Game.possibleMoves.length; i++) {

				g.drawRect(Game.possibleMoves[i].getMoveFromCol() * 50, Game.possibleMoves[i].getMoveFromRow() * 50, 49, 49);
			}
			/*
			 * If a piece is selected for moving (i.e. if selectedRow >= 0),
			 * then draw a 2-pixel white border around that piece and draw
			 * green borders around each square that that piece can be moved
			 * to.
			 */
			if (Game.chosenRow >= 0) {
				g.setColor(Color.yellow);
				g.drawRect(Game.chosenCol * 50,  Game.chosenRow * 50, 49, 49);
				g.setColor(Color.RED);
				for (int i = 0; i < Game.possibleMoves.length; i++) {
					if (Game.possibleMoves[i].getMoveFromCol() == Game.chosenCol && Game.possibleMoves[i].getMoveFromRow() ==  Game.chosenRow) {
						g.drawRect(Game.possibleMoves[i].getMoveToCol() * 50, Game.possibleMoves[i].getMoveToRow() * 50, 49, 49);
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

	@Override
	public void mousePressed(MouseEvent e) {
		if (Game.gameRunning == false)
			CheckersGame.infoLabel.setText("Click START button to start a new game");
		else {
			int col = (e.getX() / 50);
			int row = (e.getY() / 50);
			if (col >= 0 && col < 8 && row >= 0 && row < 8)
				Game.makeClick(row, col);
			//System.out.println(col.toString() + " " + row.toString());
		}		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == CheckersGame.startButton)
			Game.startNewGame();
		else if (e.getSource() ==  CheckersGame.stopButton)
			Game.resignGame();
	}

}
