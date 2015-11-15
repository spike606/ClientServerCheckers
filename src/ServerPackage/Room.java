package ServerPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

	// figures on the board
	static final int EMPTY = 0, WHITE = 1, WHITE_QUEEN = 2, BLACK = 3, BLACK_QUEEN = 4;
	private int[][] boardMaster = new int[8][8];// array of current board
												// state on server -
												// MASTER BOARD!

	private Player currentPlayerMaster;// contain current player (BLACK or
										// WHITE)

	public Player getCurrentPlayerMaster() {
		return currentPlayerMaster;
	}

	public void setCurrentPlayerMaster(Player currentPlayerMaster) {
		this.currentPlayerMaster = currentPlayerMaster;
	}

	public Room() {

		setElementsOnStart();

	}

	/*
	 * Set up board on start on server just like on the client side
	 */
	public void setElementsOnStart() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row % 2 != col % 2) {
					if (row < 3)
						boardMaster[row][col] = BLACK;
					else if (row > 4)
						boardMaster[row][col] = WHITE;
					else
						boardMaster[row][col] = EMPTY;
				} else {
					boardMaster[row][col] = EMPTY;
				}
			}
		}
	}

	public synchronized void getMoveFromPlayer(String myColor, ObjectInputStream myInput) {
		try {
			if (myColor.equals(currentPlayerMaster)) {// jesli kolory sie
														// zgadzaja to ruch moze
														// byc odebrany
				boardMaster = (int[][]) myInput.readObject();

			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized void sendMoveToPlayer(String myColor, Player myOpponent, ObjectOutputStream myOutput) {
		try {
			if (myColor.equals(currentPlayerMaster)) {// jesli kolory sie
														// zgadzaja to ruch moze
														// byc odebrany
				myOutput.writeObject(boardMaster);

				currentPlayerMaster = myOpponent;

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Player class Constructs a handler thread for a given socket and mark
	 * initializes the stream fields, displays the first two welcoming messages.
	 */
	class Player extends Thread {

		private String myColor;
		private Player myOpponent;
		private Socket mySocket;
		private ObjectInputStream myInput;
		private ObjectOutputStream myOutput;

		public Player(Socket mySocket, String myColor) {

			this.mySocket = mySocket;
			this.myColor = myColor;
			try {
				myInput = new ObjectInputStream(mySocket.getInputStream());
				myOutput = new ObjectOutputStream(mySocket.getOutputStream());
				myOutput.writeObject("WELCOME " + myColor);
				myOutput.writeObject("MESSAGE Waiting for opponent to connect");
			} catch (IOException e) {
				System.out.println("Player died: " + e);
			}

		}

		/**
		 * Accepts notification of who the opponent is.
		 */
		public void setOpponent(Player myOpponent) {
			this.myOpponent = myOpponent;
		}

		public void run() {

			try {
				// The thread is only started after everyone connects.
				myOutput.writeObject("MESSAGE All players connected");

				// Tell the first player that it is her turn.
				if (myColor.equals("White")) {
					myOutput.writeObject("MESSAGE Your move");
				}

				// Repeatedly get commands from the client and process them.
				while (true) {

					if (myInput.readObject().equals("GAME END")) {// player ends
																	// game
						return;
					} else {// TODO: NARAZIE TYLKO SERWER PRZEKAZUJE BOARD I NIE
							// SPRAWDZA POPRAWNOSCI

						getMoveFromPlayer(myColor, myInput);
						sendMoveToPlayer(myColor, myOpponent, myOutput);
					}

				}

			} catch (IOException | ClassNotFoundException e)

			{
				System.out.println("Player died: " + e);

			}
		}
	}

}
