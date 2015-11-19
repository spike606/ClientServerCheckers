package myPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CommonPackage.*;
import ServerPackage.GameFlow;

public class Connecting extends Thread {

	private static final int SERVER_PORT = 8901;
	private static final String HOST_NAME = "localhost";

	private Socket mySocket;
	private ObjectInputStream myInput;
	private ObjectOutputStream myOutput;

	Object object;

	private MessageFromClient messageToServer;
	private MessageFromServer messageFromServer;

	public Connecting() {

	}

	@Override
	public void run() {
		try {
			// Setup networking

			mySocket = new Socket("localhost", SERVER_PORT);
			myInput = new ObjectInputStream(mySocket.getInputStream());
			myOutput = new ObjectOutputStream(mySocket.getOutputStream());

		} catch (IOException e1) {
			System.out.println("IOException1.");
		}
		// while (true) {
		try {

			object = myInput.readObject();
			messageFromServer = (MessageFromServer) object;
			System.out.println(messageFromServer.getTextMessage());
			System.out.println(messageFromServer.getCurrentPlayer());
			System.out.println(messageFromServer.getWinner());
			System.out.println(messageFromServer.getPossibleMoves());
			System.out.println(messageFromServer.getBoard());
			System.out.println(messageFromServer.isGameRunning());

			getDataFromServer(messageFromServer.getBoard(), messageFromServer.isGameRunning(),
					messageFromServer.getCurrentPlayer(), messageFromServer.getPossibleMoves());

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		} catch (IOException e) {
			System.out.println("IOException2.");

		}

		// }

	}

	private void getDataFromServer(int[][] board, boolean gameRunning, int currentPlayer,
			CheckersMove[] possibleMoves) {
		GameFlowClient.setBoard(board);
		GameFlowClient.setGameRunning(gameRunning);
		GameFlowClient.setCurrentPlayer(currentPlayer);
		GameFlowClient.setPossibleMoves(possibleMoves);

	}

}
