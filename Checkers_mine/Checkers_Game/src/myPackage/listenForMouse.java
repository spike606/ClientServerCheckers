package myPackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/*
 * Listener for mouse click, used when game is not running
 */
public class ListenForMouse implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		CheckersGame.infoLabel.setText("Click \"New Game\" to start a new game.");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
