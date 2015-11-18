package ServerPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

		messageFromClient = new Message();
		// prepare message
		messageToClient = new Message();
		messageToClient.setTextMessage("Hello");

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

		}

		/**
		 * Accepts notification of who the opponent is.
		 */
		public void setOpponent(Player myOpponent) {
			this.myOpponent = myOpponent;
		}

		public void run() {
			messageToClient = new Message();
			messageToClient.setTextMessage("Hello");
			try {
				//myInput = new ObjectInputStream(mySocket.getInputStream());

				myOutput = new ObjectOutputStream(mySocket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			while (true) {
				try {
					myOutput.writeObject(messageToClient);

				} catch (IOException e) {
					System.out.println("Player died: " + e);
				}
//			}

		}
	}

}
