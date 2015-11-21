package myPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import CommonPackage.*;
import ServerPackage.GameFlow;

public class Connecting extends Thread {

	private static final int SERVER_PORT = 8901;
	private static final String HOST_NAME = "localhost";

	private Socket mySocket;
	private ObjectInputStream myInput;
	private static ObjectOutputStream myOutput;

	Object object;

	private static MessageFromClient messageToServer;
	private MessageFromServer messageFromServer;

	public Connecting() {
		messageToServer = new MessageFromClient();

	}

	@Override
	public void run() {
		try {
			// Setup networking

			mySocket = new Socket("localhost", SERVER_PORT);
			myOutput = new ObjectOutputStream(mySocket.getOutputStream());
			myOutput.flush();
			myInput = new ObjectInputStream(mySocket.getInputStream());

		} catch (IOException e1) {
			System.out.println("IOException1.");
		}
		 while (true) {
		try {

			object = myInput.readObject();
			messageFromServer = (MessageFromServer) object;
			System.out.println("FromServer");
			System.out.println(messageFromServer.getTextMessage());
			System.out.println(messageFromServer.getCurrentPlayer());
			System.out.println(messageFromServer.getWinner());
			System.out.println(messageFromServer.getPossibleMoves());
			System.out.println(messageFromServer.getBoard());
			System.out.println(messageFromServer.isGameRunning());
			System.out.println("Row from server " + messageFromServer.getChosenRow());

			System.out.println("Col from server " + messageFromServer.getChosenCol());


			getDataFromServer(messageFromServer.getBoard(),messageFromServer.getChosenRow(),
					messageFromServer.getChosenCol(), messageFromServer.isGameRunning(),
					messageFromServer.getCurrentPlayer(), messageFromServer.getPossibleMoves());
			

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		} catch (IOException e) {
			System.out.println("IOException2.");

		}

		 }

	}

	private void getDataFromServer(int[][] board, int chosenRow, int chosenCol, boolean gameRunning, int currentPlayer,
			CheckersMove[] possibleMoves) {
		GameFlowClient.setBoard(board);
		GameFlowClient.setChosenRow(chosenRow);
		GameFlowClient.setChosenCol(chosenCol);
		GameFlowClient.setGameRunning(gameRunning);
		GameFlowClient.setCurrentPlayer(currentPlayer);
		GameFlowClient.setPossibleMoves(possibleMoves);
		System.out.println("OnClient");
		System.out.println(GameFlowClient.getBoard());
//		System.out.println(GameFlowClient.get);
//		System.out.println(messageFromServer.getWinner());
//		System.out.println(messageFromServer.getPossibleMoves());
//		System.out.println(messageFromServer.getBoard());
//		System.out.println(messageFromServer.isGameRunning());

	}

	private static void prepareMessageToServer(int row, int col) {
		messageToServer.setChosenCol(col);
		messageToServer.setChosenRow(row);

	}

	public static void sendClick(int row, int col) {
		prepareMessageToServer(row, col);
		try {
			myOutput.reset();

			myOutput.writeObject(messageToServer);
			System.out.println("kkkkk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
