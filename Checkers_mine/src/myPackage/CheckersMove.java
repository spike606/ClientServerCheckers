package myPackage;

public class CheckersMove {

	//class which represent move in the game
	
	
	private int moveFromRow, moveFromCol; // coordinates of the checker to be moved 
	private int moveToRow, moveToCol; // coordinates where the checker will be moved
	
	public CheckersMove(int moveFromRow, int moveFromCol, int moveToRow, int moveToCol) {
		// Constructor. Just set the values of the instance variables.
		this.moveFromRow = moveFromRow;
		this.moveFromCol = moveFromCol;
		this.moveToRow = moveToRow;
		this.moveToCol = moveToCol;
	}
	
	public boolean isMoveBeating() {
		//if current move is beating return true
		//return (fromRow - toRow == 2 || fromRow - toRow == -2); ORG
		return (moveFromCol - moveToCol == 2 || moveFromCol - moveToCol == -2);
	}
	public boolean isQueensMove(){
		//does current move is queenqs move
		if(moveFromCol - moveToCol > 2 || moveFromCol - moveToCol < -2)
			return true;
		else return false;

	}

	public int getMoveFromRow() {
		return moveFromRow;
	}

	public void setMoveFromRow(int moveFromRow) {
		this.moveFromRow = moveFromRow;
	}

	public int getMoveFromCol() {
		return moveFromCol;
	}

	public void setMoveFromCol(int moveFromCol) {
		this.moveFromCol = moveFromCol;
	}

	public int getMoveToRow() {
		return moveToRow;
	}

	public void setMoveToRow(int moveToRow) {
		this.moveToRow = moveToRow;
	}

	public int getMoveToCol() {
		return moveToCol;
	}

	public void setMoveToCol(int moveToCol) {
		this.moveToCol = moveToCol;
	}
	
}
