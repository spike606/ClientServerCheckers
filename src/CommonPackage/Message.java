package CommonPackage;

/*
 * Class uses to sending data from server to client and from client to server
 * TODO:ZABEZPIECZYC KLASE - MOZLIWOSC ZMIANY WRAZLIWYCH DANYCH JAK WYGRANY CZY PRZEGRANY
 */
public class Message {

	private int[][] board = new int[8][8];// array of current board state
	private boolean gameRunning;// flag, is game in progress
	private int currentPlayer;// contain current player (BLACK or WHITE)
	private CheckersMove[] possibleMoves;// array with possible moves for
											// current
	private int winner; // contain winner player (BLACK or WHITE - 0, 1)

	public int[][] getBoard() {
		return board;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public CheckersMove[] getPossibleMoves() {
		return possibleMoves;
	}

	public void setPossibleMoves(CheckersMove[] possibleMoves) {
		this.possibleMoves = possibleMoves;
	}

}
