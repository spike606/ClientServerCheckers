package myPackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class listenForMouse implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
//		if (Game.gameRunning == false)
			CheckersGame.infoLabel.setText("Click \"New Game\" to start a new game.");
//		else {
//			int col = (e.getX() - 2) / 20;
//			int row = (e.getY() - 2) / 20;
//			if (col >= 0 && col < 8 && row >= 0 && row < 8)
//				doClickSquare(row, col);
//		}		
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
