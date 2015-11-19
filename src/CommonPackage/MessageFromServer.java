package CommonPackage;

import java.io.Serializable;

/*
 * Class uses to sending data from server to client and from client to server
 * TODO:ZABEZPIECZYC KLASE - MOZLIWOSC ZMIANY WRAZLIWYCH DANYCH JAK WYGRANY CZY PRZEGRANY
 */
public class MessageFromServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5889903096874602732L;
	private int[][] board = new int[8][8];// array of current board state
	private boolean gameRunning;// flag, is game in progress
	private int currentPlayer;// contain current player (BLACK or WHITE)
	private CheckersMove[] possibleMoves;// array with possible moves for
											// current
	private int winner; // contain winner player (BLACK or WHITE - 0, 1)
	private String textMessage;

	public int[][] getBoard() {
		return board;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
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
