package myPackage;

/*
 * Class used to manage the game flow
 */
public class GameFlow {

	static boolean gameRunning = false;// flag, is game in progress

	static GameData boardData;// object to manage game logic
	static int currentPlayer;// contain current player (BLACK or WHITE)
	static int chosenRow = -1;// coordinates of selected checker
	static int chosenCol = -1;// -1 means no selected row or column

	static CheckersMove[] possibleMoves;// array with possible moves for current
										// player

	public GameFlow() {

		initializeGame();

	}

	/*
	 * Running when program starts, needed to draw view
	 */
	private void initializeGame() {
		if (gameRunning == true) {
			// This should not be possible
			CheckersGame.infoLabel.setText("Finish the current game first!");
			return;
		}
		boardData = new GameData();

		boardData.setElementsOnStart();

	}

	/*
	 * Performed after clicking button START
	 */
	static void startNewGame() {

		if (gameRunning == true) {
			// This should not be possible
			CheckersGame.infoLabel.setText("Finish the current game first!");
			return;
		}
		boardData = new GameData();

		boardData.setElementsOnStart();

		currentPlayer = GameData.WHITE;// white starts a game
		possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
		CheckersGame.infoLabel.setText("WHITE: make your move.");
		gameRunning = true;
		CheckersGame.startButton.setEnabled(false);
		CheckersGame.stopButton.setEnabled(true);

	}

	/*
	 * Performed after clicking button STOP
	 */
	static void resignGame() {
		if (gameRunning == false) {
			CheckersGame.infoLabel.setText("There is no game in progress!");
			return;
		}
		if (currentPlayer == GameData.WHITE)
			gameIsOver("WHITE resigns.  BLACK wins!");
		else
			gameIsOver("BLACK resigns.  WHITE wins!");
	}

	private static void gameIsOver(String string) {
		CheckersGame.infoLabel.setText(string);
		CheckersGame.startButton.setEnabled(true);
		CheckersGame.stopButton.setEnabled(false);
		gameRunning = false;
	}

	/*
	 * Handle Click on board depending of current game state
	 */
	static void makeClick(int row, int col) {

		/*
		 * When no piece is selected Choose piece to move and save coordinates
		 * to the class fields
		 */
		for (int i = 0; i < possibleMoves.length; i++)
			if (possibleMoves[i].getMoveFromRow() == row && possibleMoves[i].getMoveFromCol() == col) {
				chosenRow = row;
				chosenCol = col;
				if (currentPlayer == GameData.WHITE)
					CheckersGame.infoLabel.setText("White:  Make your move.");
				else
					CheckersGame.infoLabel.setText("Black:  Make your move.");
				return;
			}
		/*
		 * When piece is not selected
		 */
		if (chosenRow < 0) {
			CheckersGame.infoLabel.setText("Click the piece you want to move.");
			return;
		}
		/*
		 * Make move from selected field to another
		 */
		for (int i = 0; i < possibleMoves.length; i++)
			if (possibleMoves[i].getMoveFromRow() == chosenRow && possibleMoves[i].getMoveFromCol() == chosenCol
					&& possibleMoves[i].getMoveToRow() == row && possibleMoves[i].getMoveToCol() == col) {
				performMove(possibleMoves[i]);
				return;
			}
		/*
		 * If no then clicked was performed not in the square where can be
		 * moved. Display info
		 */
		CheckersGame.infoLabel.setText("Click the square you want to move to.");

	}

	/*
	 * Make specific move
	 */
	private static void performMove(CheckersMove checkerMove) {

		// make a move
		boardData.makeMove(checkerMove);

		/*
		 * Check if second move is possible - when checker is beating and it is
		 * not move performed by queen or when queen is beating
		 */
		if ((checkerMove.isMoveBeating() && !checkerMove.isMovePerformedByQueen())
				|| checkerMove.isBeatingPerformedByQueen()) {
			possibleMoves = boardData.getPossibleSecondBeating(currentPlayer, checkerMove.getMoveToRow(),
					checkerMove.getMoveToCol());
			if (possibleMoves != null) {
				if (currentPlayer == GameData.WHITE)
					CheckersGame.infoLabel.setText("White:  You must beat.");
				else if (currentPlayer == GameData.BLACK)
					CheckersGame.infoLabel.setText("Black:  You must beat.");
				chosenRow = checkerMove.getMoveToRow(); // Since only one piece
														// can be moved, select
														// it.
				chosenCol = checkerMove.getMoveToCol();
				return;
			}
		}

		// restore default values
		checkerMove.setMovePerformedByQueen(false);
		checkerMove.setBeatingPerformedByQueen(false);

		/*
		 * Change player and get moves for him. If possible moves are beating,
		 * set info. Check if game is over.
		 */
		if (currentPlayer == GameData.WHITE) {
			currentPlayer = GameData.BLACK;
			possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
			if (possibleMoves == null)
				gameIsOver("BLACK has no moves.  WHITE wins!");
			else if (possibleMoves[0].isMoveBeating())// possible moves are
														// beating
				CheckersGame.infoLabel.setText("BLACK:  Make your move.  You must beat.");
			else
				CheckersGame.infoLabel.setText("BLACK:  Make your move.");
		} else {
			currentPlayer = GameData.WHITE;
			possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
			if (possibleMoves == null)
				gameIsOver("WHITE has no moves.  BLACK wins!");
			else if (possibleMoves[0].isMoveBeating())
				CheckersGame.infoLabel.setText("WHITE:  Make your move.  You must beat.");
			else
				CheckersGame.infoLabel.setText("WHITE:  Make your move.");
		}
		/*
		 * Set default values - player has not yet selected a checker to move
		 */

		chosenRow = -1;
		chosenCol = -1;

		/*
		 * If all legal moves use the same piece, it means only one move is
		 * possible select that piece automatically
		 */

		if (possibleMoves != null) {
			boolean sameSquare = true;
			for (int i = 1; i < possibleMoves.length; i++)
				if (possibleMoves[i].getMoveFromRow() != possibleMoves[0].getMoveFromRow()
						|| possibleMoves[i].getMoveFromCol() != possibleMoves[0].getMoveFromCol()) {
					sameSquare = false;
					break;
				}
			if (sameSquare) {
				chosenRow = possibleMoves[0].getMoveFromRow();
				chosenCol = possibleMoves[0].getMoveFromCol();
			}
		}
	}

}
