package ServerPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CommonPackage.CheckersMove;
import CommonPackage.Message;

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

	// obects to mengae game for this room
	GameFlow gameFlow;
	// to communicate
	Message messageToClient;

	Message messageFromClient;

	public Room() {

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

		private Message messageFromClient = new Message();
		private Message messageToClient = new Message();

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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// while (true) {
			try {
				prepareMessageToClient(GameFlow.boardData.getBoard(), true, GameData.WHITE, GameFlow.getPossibleMoves()
						, GameData.WHITE, "First message");
				myOutput.writeObject(messageToClient);

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
