package myPackage;

import java.util.ArrayList;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import myPackage.CheckersMove;

/*
 * Class used to store data about board - pawns location etc
 */
public class GameData {

	// figures on the board
	static final int EMPTY = 0, WHITE = 1, WHITE_QUEEN = 2, BLACK = 3, BLACK_QUEEN = 4;
	static private int[][] board = new int[8][8];

	public GameData() {

		setElementsOnStart();
		/*
		 * for (int row = 0; row < 8; row++) { for (int col = 0; col < 8; col++)
		 * { System.out.print(board[row][col]); } System.out.println(); }
		 */

	}

	public void setElementsOnStart() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row % 2 != col % 2) {
					if (row < 3)
						board[row][col] = BLACK;
					else if (row > 4)
						board[row][col] = WHITE;
					else
						board[row][col] = EMPTY;
				} else {
					board[row][col] = EMPTY;
				}
			}
		}
	}

	// get current checker on selected field
	public int getFieldOnBoard(int row, int col) {
		return board[row][col];
	}
	// set selected checker on selected field
	// public void setPawnOnField(int row, int col, int checker){
	// board[row][col] = checker;
	// }NIE WIADOMO CZY POTRZEBNE

	/**
	 * Make the move. It is assumed that move is non-null and that the move it
	 * represents is legal.
	 */
	public void makeMove(CheckersMove move) {
		// makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);

		// check queen move, it is beating?
		if (move.isQueensMove()) {
			removeOpponentCheckerIfBeating(move);
			moveChecker(move);

		}
		// check if beats
		else if (move.isMoveBeating()) {
			removeOpponentChecker(move);
			moveChecker(move);
		} else
			moveChecker(move);

		checkIfNewQueen(move);

	}

	private void removeOpponentCheckerIfBeating(CheckersMove move) {
		int opponentCheckerRow = 0;
		int opponentCheckerCol = 0;

		int checkRow = move.getMoveFromRow();
		int checkCol = move.getMoveFromCol();

		System.out.println("first" + checkCol);
		System.out.println("second" + checkRow);
		if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {

			while (checkCol < 7 && checkRow < 7) {
				System.out.println("1loop");
				checkCol++;
				checkRow++;
				System.out.println(checkCol);
				System.out.println(checkRow);
				if (board[checkRow][checkCol] != EMPTY) {

					break;
				}
			}

		} else if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			while (checkCol > 0 && checkRow < 7) {
				System.out.println("2loop");
				checkCol--;
				checkRow++;
				System.out.println(checkCol);
				System.out.println(checkRow);
				if (board[checkRow][checkCol] != EMPTY) {

					break;
				}
			}

		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
			while (checkCol < 7 && checkRow > 0) {
				System.out.println("3loop");
				checkCol++;
				checkRow--;
				System.out.println(checkCol);
				System.out.println(checkRow);
				if (board[checkRow][checkCol] != EMPTY) {

					break;
				}
			}

		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			while (checkCol > 0 && checkRow > 0) {
				System.out.println("4loop");

				checkCol--;
				checkRow--;
				System.out.println(checkCol);
				System.out.println(checkCol);
				if (board[checkRow][checkCol] != EMPTY) {

					break;
				}
			}

		}
		System.out.println(checkCol);
		System.out.println(checkRow);

		opponentCheckerCol = checkCol;
		opponentCheckerRow = checkRow;

		board[opponentCheckerRow][opponentCheckerCol] = EMPTY;
	}

	private void checkIfNewQueen(CheckersMove move) {
		if (move.getMoveToRow() == 0 && board[move.getMoveToRow()][move.getMoveToCol()] == WHITE)
			board[move.getMoveToRow()][move.getMoveToCol()] = WHITE_QUEEN;
		if (move.getMoveToRow() == 7 && board[move.getMoveToRow()][move.getMoveToCol()] == BLACK)
			board[move.getMoveToRow()][move.getMoveToCol()] = BLACK_QUEEN;
	}

	private void moveChecker(CheckersMove move) {
		board[move.getMoveToRow()][move.getMoveToCol()] = board[move.getMoveFromRow()][move.getMoveFromCol()];
		board[move.getMoveFromRow()][move.getMoveFromCol()] = EMPTY;
	}

	private void removeOpponentChecker(CheckersMove move) {
		// Remove beaten checker from the board
		// int opponentCheckerRow = (move.getMoveFromRow() +
		// move.getMoveToRow()) / 2;
		// int opponentCheckerCol = (move.getMoveFromCol() +
		// move.getMoveToCol()) / 2;
		int opponentCheckerRow = 0;
		int opponentCheckerCol = 0;

		if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() - 1;
			opponentCheckerCol = move.getMoveToCol() - 1;
		} else if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() - 1;
			opponentCheckerCol = move.getMoveToCol() + 1;
		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() + 1;
			opponentCheckerCol = move.getMoveToCol() - 1;
		} else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
			opponentCheckerRow = move.getMoveToRow() + 1;
			opponentCheckerCol = move.getMoveToCol() + 1;
		}
		System.out.println(opponentCheckerRow);
		System.out.println(opponentCheckerCol);

		board[opponentCheckerRow][opponentCheckerCol] = EMPTY;

	}

	public CheckersMove[] getPossibleMovesForPlayer(int player) {

		if (player != WHITE && player != BLACK)
			return null;

		int playerQueen; // The constant representing a King belonging to
							// player.
		if (player == WHITE)
			playerQueen = WHITE_QUEEN;
		else
			playerQueen = BLACK_QUEEN;

		// temporary array for possible moves
		ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();

		// check possible beating - if so player must to beat
		checkPossibleBeating(moves, player, playerQueen);

		// if no beating is possible check for regular moves
		if (moves.size() == 0) {
			checkPossibleRegularMoves(moves, player, playerQueen);

		}

		// if no possible moves return null
		if (moves.size() == 0) {
			return null;

		} else {
			CheckersMove[] arrayOfPossibleMoves = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++) {
				arrayOfPossibleMoves[i] = moves.get(i);
				// System.out.println("From " +
				// arrayOfPossibleMoves[i].getMoveFromCol() + " " +
				// arrayOfPossibleMoves[i].getMoveFromRow()
				// + " to " + arrayOfPossibleMoves[i].getMoveToCol() + " " +
				// arrayOfPossibleMoves[i].getMoveToRow());

			}
			return arrayOfPossibleMoves;

		}

	}

	private void checkPossibleRegularMoves(ArrayList<CheckersMove> moves, int player, int playerQueen) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player) {// check for possible regular
												// move
					// 4 directions
					if (canMove(player, row, col, row + 1, col + 1))
						moves.add(new CheckersMove(row, col, row + 1, col + 1));
					if (canMove(player, row, col, row - 1, col + 1))
						moves.add(new CheckersMove(row, col, row - 1, col + 1));
					if (canMove(player, row, col, row + 1, col - 1))
						moves.add(new CheckersMove(row, col, row + 1, col - 1));
					if (canMove(player, row, col, row - 1, col - 1))
						moves.add(new CheckersMove(row, col, row - 1, col - 1));
				} // check for possible moves if it is queen checker
				else if (board[row][col] == playerQueen) {
					canQueenMove(moves, player, row, col);
					System.out.println("queen van move");
				}
			}
		}
	}

	private void canQueenMove(ArrayList<CheckersMove> moves, int player, int rowFrom, int colFrom) {

		int rowToCheck = rowFrom;
		int colToCheck = colFrom;

		// check for white or black player
		if (player == WHITE) {

			// 1 direction
			while (--rowToCheck >= 0 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			// 2 direction
			while (--rowToCheck >= 0 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			// 3 direction
			while (++rowToCheck <= 7 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			// 4 direction
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}

		} else {
			// 1 direction
			while (--rowToCheck >= 0 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			// 2 direction
			while (--rowToCheck >= 0 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			// 3 direction
			while (++rowToCheck <= 7 && --colToCheck >= 0) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			// 4 direction
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {
				if (board[rowToCheck][colToCheck] != EMPTY) {
					break;
				}
				moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

			}

		}

	}

	private boolean canMove(int player, int rowFrom, int colFrom, int rowTo, int colTo) {
		// out of board
		if (colTo > 7 || colTo < 0 || rowTo > 7 || rowTo < 0)
			return false;

		// rowTo, colTo are occupied by another checker
		if (board[rowTo][colTo] != EMPTY)
			return false;

		// check for white or black player
		if (player == WHITE) {
			if (rowTo > rowFrom)
				return false;// regular white checker can only move up
			return true; // can move
		} else {
			if (rowTo < rowFrom)
				return false;// regular black checker can only move down
			return true; // can move
		}
	}

	private void checkPossibleBeating(ArrayList<CheckersMove> moves, int player, int playerQueen) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == player) {// check for possible beating
												// for checker
					// 4 directions
					if (canBeat(player, row, col, row + 1, col + 1, row + 2, col + 2))
						moves.add(new CheckersMove(row, col, row + 2, col + 2));
					if (canBeat(player, row, col, row - 1, col + 1, row - 2, col + 2))
						moves.add(new CheckersMove(row, col, row - 2, col + 2));
					if (canBeat(player, row, col, row + 1, col - 1, row + 2, col - 2))
						moves.add(new CheckersMove(row, col, row + 2, col - 2));
					if (canBeat(player, row, col, row - 1, col - 1, row - 2, col - 2))
						moves.add(new CheckersMove(row, col, row - 2, col - 2));
				} // check for possible beating if it is queen checker
				else if (board[row][col] == playerQueen) {
					canQueenBeat(moves, player, row, col);
					System.out.println("queen can beat");
				}

			}

		}
	}

	private void canQueenBeat(ArrayList<CheckersMove> moves, int player, int rowFrom, int colFrom) {

		int rowToCheck = rowFrom;
		int colToCheck = colFrom;

		boolean enemyCheckerFound = false;
		// check for white or black player
		if (player == WHITE) {

			// 1 direction
			while (--rowToCheck >= 0 && --colToCheck >= 0) {
				if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;// found black checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			// 2 direction
			while (--rowToCheck >= 0 && ++colToCheck <= 7) {
				if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;// found black checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			// 3 direction
			while (++rowToCheck <= 7 && --colToCheck >= 0) {
				if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;// found black checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			// 4 direction
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {
				if ((board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN)
						&& enemyCheckerFound == true) {// second
														// enemy
														// checker
														// -
														// stop
														// searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
					enemyCheckerFound = true;// found black checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}

		} else {
			// 1 direction
			while (--rowToCheck >= 0 && --colToCheck >= 0) {
				if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;// found white checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			// 2 direction
			while (--rowToCheck >= 0 && ++colToCheck <= 7) {
				if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;// found white checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			// 3 direction
			while (++rowToCheck <= 7 && --colToCheck >= 0) {
				if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;// found white checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}
			rowToCheck = rowFrom;
			colToCheck = colFrom;
			enemyCheckerFound = false;
			// 4 direction
			while (++rowToCheck <= 7 && ++colToCheck <= 7) {
				if ((board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN)
						&& enemyCheckerFound == true) {// second enemy checker -
														// stop searching
					enemyCheckerFound = false;
					break;
				} else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
					enemyCheckerFound = true;// found white checker
				} else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
					moves.add(new CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));

				}

			}

		}
	}

	// check if checker can beat another checker
	private boolean canBeat(int player, int rowFrom, int colFrom, int rowJumped, int colJumped, int rowTo, int colTo) {

		// out of board
		if (colTo > 7 || colTo < 0 || rowTo > 7 || rowTo < 0) {
			return false;

		}

		// rowTo, colTo are occupied by another checker
		if (board[rowTo][colTo] != EMPTY) {
			return false;

		}

		// check for white or black player
		if (player == WHITE) {
			if (rowTo > rowFrom && board[rowFrom][colFrom] == WHITE) {

				return false;// regular white checker can only move up

			}
			if (board[rowJumped][colJumped] != BLACK && board[rowJumped][colJumped] != BLACK_QUEEN) {

				return false;// no black checker to beat

			}
			return true; // can beat
		} else {
			if (rowTo < rowFrom && board[rowFrom][colFrom] == BLACK) {

				return false;// regular black checker can only move down

			}
			if (board[rowJumped][colJumped] != WHITE && board[rowJumped][colJumped] != WHITE_QUEEN) {

				return false;// no white checker to beat

			}
			return true; // can beat
		}

	}

	public CheckersMove[] getPossibleSecondBeating(int player, int rowFrom, int colFrom) {

		if (player != WHITE && player != BLACK)
			return null;

		int playerQueen; // The constant representing a King belonging to
							// player.
		if (player == WHITE)
			playerQueen = WHITE_QUEEN;
		else
			playerQueen = BLACK_QUEEN;

		// temporary array for possible moves
		ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();

		// check possible beating - if so player must to beat
		checkPossibleSecondBeating(moves, player, playerQueen, rowFrom, colFrom);

		// if no possible moves return null
		if (moves.size() == 0) {
			return null;

		} else {
			CheckersMove[] arrayOfSecondBeat = new CheckersMove[moves.size()];
			for (int i = 0; i < moves.size(); i++)
				arrayOfSecondBeat[i] = moves.get(i);
			return arrayOfSecondBeat;

		}

	}

	private void checkPossibleSecondBeating(ArrayList<CheckersMove> moves, int player, int playerQueen, int row,
			int col) {

		if (board[row][col] == player) {// check for possible second beating
										// for checker
			// 4 directions
			System.out.println("aaaaa");
			if (canBeat(player, row, col, row + 1, col + 1, row + 2, col + 2))
				moves.add(new CheckersMove(row, col, row + 2, col + 2));
			if (canBeat(player, row, col, row - 1, col + 1, row - 2, col + 2))
				moves.add(new CheckersMove(row, col, row - 2, col + 2));
			if (canBeat(player, row, col, row + 1, col - 1, row + 2, col - 2))
				moves.add(new CheckersMove(row, col, row + 2, col - 2));
			if (canBeat(player, row, col, row - 1, col - 1, row - 2, col - 2))
				moves.add(new CheckersMove(row, col, row - 2, col - 2));
		} // check for possible beating if it is queen checker
		else if (board[row][col] == playerQueen) {
			System.out.println("bbbb");
			canQueenBeat(moves, player, row, col);
		}

	}

}
