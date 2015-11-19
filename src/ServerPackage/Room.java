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

public class Room {

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

	public Room(int roomNumber) {

		this.roomNumber = roomNumber;

		gameFlow = new GameFlow();

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

		private MessageFromServer messageFromClient = new MessageFromServer();
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
				// myInput = new ObjectInputStream(mySocket.getInputStream());

				myOutput = new ObjectOutputStream(mySocket.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// while (true) {
			try {
				if(GameFlow.currentPlayer == myColor){
					prepareMessageToClient(GameFlow.boardData.getBoard(), true, GameData.WHITE, GameFlow.getPossibleMoves(),
							GameData.WHITE, "First message");
					myOutput.writeObject(messageToClient);
				}


			} catch (IOException e) {
				System.out.println("Player died: " + e);
			}
			// }

		}

		@SuppressWarnings("unused")
		private void prepareMessageToClient(int[][] board, boolean gameRunning, int currentPlayer,
				CheckersMove[] possibleMoves, int winner, String textMessage) {

			messageToClient.setBoard(board);
			messageToClient.setGameRunning(gameRunning);
			messageToClient.setCurrentPlayer(currentPlayer);
			messageToClient.setPossibleMoves(possibleMoves);
			messageToClient.setWinner(winner);
			messageToClient.setTextMessage(textMessage);

		}
	}

}
