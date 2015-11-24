package ServerPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CommonPackage.*;

/*
 * Class for 1 room - 2 players
 * MASTER - means data on the server side
 */

public class Match {

	/*
	 * TODO: Method to check and autohrize moves for security, to prevent
	 * cheating 1)METHOD - first check - when players are connected and game is
	 * initilized 2)when players makes moves - check if moves are legal ????!!!
	 */

	int roomNumber;

	// obects to mengae game for this room
	GameFlow gameFlow;
	// to communicate
	MessageFromServer messageToClient;

	MessageFromClient messageFromClient;

	public Match(int roomNumber) {

		this.roomNumber = roomNumber;

		gameFlow = new GameFlow();
		gameFlow.setGameRunning(true);

	}

	private synchronized void resetChosenField() {
		gameFlow.setChosenCol(-1);
		gameFlow.setChosenRow(-1);

	}

	/*
	 * Player class Constructs a handler thread for a given socket and mark
	 * initializes the stream fields, displays the first two welcoming messages.
	 */
	class Player extends Thread {

		private int myColor;
		private Player myOpponent;
		private Socket mySocket;
		private ObjectInputStream myInput;
		private ObjectOutputStream myOutput;

		private MessageFromClient messageFromClient = new MessageFromClient();
		private MessageFromServer messageToClient = new MessageFromServer();

		public Player(Socket mySocket, int myColor) {

			this.mySocket = mySocket;
			this.myColor = myColor;

		}

		/**
		 * Accepts notification of who the opponent is.
		 */
		public void setOpponent(Player myOpponent) {
			this.myOpponent = myOpponent;
		}

		public void run() {

			try {
				myInput = new ObjectInputStream(mySocket.getInputStream());
				myOutput = new ObjectOutputStream(mySocket.getOutputStream());
				myOutput.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {

				// wiadomosci incijujace do klientow
				prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(), gameFlow.getChosenRow(),
						true, gameFlow.getCurrentPlayer(), gameFlow.getPossibleMoves(), GameData.EMPTY, myColor);
				myOutput.writeObject(messageToClient);

				while (true) {
					if (gameFlow.getCurrentPlayer() == myColor && gameFlow.isGameRunning()) {

						prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
								gameFlow.getChosenRow(), gameFlow.isGameRunning(), gameFlow.getCurrentPlayer(), gameFlow.getPossibleMoves(),
								gameFlow.getWinner(), myColor);
						myOutput.reset();
						myOutput.writeObject(messageToClient);

						// odebranie klikniecia od klienta
						try {
							messageFromClient = (MessageFromClient) myInput.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}

						// przetworznie klikniecia (ewentualnego ruchu, bicia
						// itp)
						gameFlow.makeClick(messageFromClient.getChosenRow(), messageFromClient.getChosenCol(),
								messageFromClient.isResign());

						// przygotowanie odpowiedzi dla klienta i jej wyslanie
						prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
								gameFlow.getChosenRow(), gameFlow.isGameRunning(), gameFlow.getCurrentPlayer(), gameFlow.getPossibleMoves(),
								gameFlow.getWinner(), myColor);
						myOutput.reset();
						myOutput.writeObject(messageToClient);

					} else if (!gameFlow.isGameRunning() && gameFlow.getWinner() != GameData.EMPTY) {
						prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
								gameFlow.getChosenRow(), gameFlow.isGameRunning(), gameFlow.getCurrentPlayer(), gameFlow.getPossibleMoves(),
								gameFlow.getWinner(), myColor);
						myOutput.reset();
						myOutput.writeObject(messageToClient);
					}

				}
			} catch (

			IOException e)

			{
				System.out.println("Player died: " + e);
			}
		}

		@SuppressWarnings("unused")
		synchronized private void prepareMessageToClient(int[][] board, int chosenCol, int chosenRow,
				boolean gameRunning, int currentPlayer, CheckersMove[] possibleMoves, int winner, int myColor) {

			messageToClient.setBoard(board);
			messageToClient.setChosenCol(chosenCol);
			messageToClient.setChosenRow(chosenRow);
			messageToClient.setGameRunning(gameRunning);
			messageToClient.setCurrentPlayer(currentPlayer);
			messageToClient.setPossibleMoves(possibleMoves);
			messageToClient.setWinner(winner);
			messageToClient.setGameRunning(gameRunning);
			messageToClient.setMyColor(myColor);

		}

		// private void getMessageFromClient(MessageFromClient
		// messageFromClient) {
		// gameFlow.get = messageFromClient.getChosenRow();
		// gameFlow.chosenCol = messageFromClient.getChosenCol();
		// System.out.println(gameFlow.chosenRow);
		// System.out.println(gameFlow.chosenCol);
		//
		// }
	}

}
