package myPackage;

/*
 * Class used to manage the game
 */
public class Game {

	
	static boolean gameRunning = false;//flag, does game is in progress
	
	static GameData boardData;
	static int currentPlayer;//contain current player (BLACK or WHITE)
	static int chosenRow =-1;// coordinates of selected checker
	static int chosenCol = -1;//-1 means no selected row or column
	
	static CheckersMove[] possibleMoves;//legal moves for current player
	
	public Game(){
		
		initializeGame();

		
	}

	private void initializeGame() {
		if (gameRunning == true) {
			// This should not be possible, but it doens't hurt to check.
			CheckersGame.infoLabel.setText("Finish the current game first!");
			return;
		}			
		boardData = new GameData();//game data

		boardData.setElementsOnStart();
		
		
		
		
	}

	static  void startNewGame() {

		if (gameRunning == true) {
			// This should not be possible, but it doens't hurt to check.
			CheckersGame.infoLabel.setText("Finish the current game first!");
			return;
		}		
		boardData = new GameData();//game data

		boardData.setElementsOnStart();
		
		currentPlayer = GameData.WHITE;
		possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
		CheckersGame.infoLabel.setText("White: make your move.");
		gameRunning = true;
		CheckersGame.startButton.setEnabled(false);
		CheckersGame.stopButton.setEnabled(true);

		//repaint();

	}

	static void resignGame() {
		if (gameRunning == false) {
			CheckersGame.infoLabel.setText("There is no game in progress!");
			return;
		}
		if (currentPlayer == GameData.WHITE)
			gameIsOver("WHITE resigns.  BLACK wins.");
		else
			gameIsOver("BLACK resigns.  WHITE wins.");		
	}

	private static void gameIsOver(String string) {
		CheckersGame.infoLabel.setText(string);		
		CheckersGame.startButton.setEnabled(true);
		CheckersGame.stopButton.setEnabled(false);
		gameRunning = false;
	}

	static void makeClick(int row, int col) {
		
		///some test change
		for (int i = 0; i < possibleMoves.length; i++)
			if (possibleMoves[i].getMoveFromRow() == row && possibleMoves[i].getMoveFromCol() == col) {
				chosenRow = row;
				chosenCol = col;
				if (currentPlayer == GameData.WHITE)
					CheckersGame.infoLabel.setText("White:  Make your move.");
				else
					CheckersGame.infoLabel.setText("Black:  Make your move.");
				//repaint();
				return;
			}
		
		
		if (chosenRow < 0) {
			CheckersGame.infoLabel.setText("Click the piece you want to move.");
			return;
		}
		
		
		for (int i = 0; i < possibleMoves.length; i++)
			if (possibleMoves[i].getMoveFromRow() == chosenRow && possibleMoves[i].getMoveFromCol() == chosenCol
					&& possibleMoves[i].getMoveToRow() == row && possibleMoves[i].getMoveToCol() == col) {
				performMove(possibleMoves[i]);
				return;
			}
		CheckersGame.infoLabel.setText("Click the square you want to move to.");

	}

	private static void performMove(CheckersMove checkerMove) {

		//make a move
		boardData.makeMove(checkerMove);
		
		//if second beating
		if ((checkerMove.isMoveBeating() && !checkerMove.isMovePerformedByQueen()) || checkerMove.isBeatingPerformedByQueen()) {
			System.out.println("get seconf beating");
			possibleMoves = boardData.getPossibleSecondBeating(currentPlayer,
					checkerMove.getMoveToRow(), checkerMove.getMoveToCol());
			if (possibleMoves != null) {
				if (currentPlayer == GameData.WHITE)
					CheckersGame.infoLabel.setText("White:  You must beat.");
				else if(currentPlayer == GameData.BLACK)
					CheckersGame.infoLabel.setText("Black:  You must beat.");
				chosenRow = checkerMove.getMoveToRow(); // Since only one piece can be
											// moved, select it.
				chosenCol = checkerMove.getMoveToCol();
				//repaint();
				return;
			}
		}
		checkerMove.setMovePerformedByQueen(false);
		checkerMove.setBeatingPerformedByQueen(false);

		//change player
		
		if (currentPlayer == GameData.WHITE) {
			currentPlayer = GameData.BLACK;
			possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
			if (possibleMoves == null)
				gameIsOver("BLACK has no moves.  WHITE wins.");
			else if (possibleMoves[0].isMoveBeating())
				CheckersGame.infoLabel.setText("BLACK:  Make your move.  You must jump.");
			else
				CheckersGame.infoLabel.setText("BLACK:  Make your move.");
		} else {
			currentPlayer = GameData.WHITE;
			possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
			if (possibleMoves == null)
				gameIsOver("WHITE has no moves.  BLACK wins.");
			else if (possibleMoves[0].isMoveBeating())
				CheckersGame.infoLabel.setText("WHITE:  Make your move.  You must jump.");
			else
				CheckersGame.infoLabel.setText("WHITE:  Make your move.");
		}
		/*
		 * Set selectedRow = -1 to record that the player has not yet
		 * selected a piece to move.
		 */

		chosenRow = -1;
		chosenCol = -1;
		
		/*
		 * As a courtesy to the user, if all legal moves use the same piece,
		 * then select that piece automatically so the use won't have to
		 * click on it to select it.
		 */

		if (possibleMoves != null) {
			boolean sameStartSquare = true;
			for (int i = 1; i < possibleMoves.length; i++)
				if (possibleMoves[i].getMoveFromRow() != possibleMoves[0].getMoveFromRow()
						|| possibleMoves[i].getMoveFromCol() != possibleMoves[0].getMoveFromCol()) {
					sameStartSquare = false;
					break;
				}
			if (sameStartSquare) {
				chosenRow = possibleMoves[0].getMoveFromRow();
				chosenCol = possibleMoves[0].getMoveFromCol();
			}
		}
	}
	
	
	
	
	
}
