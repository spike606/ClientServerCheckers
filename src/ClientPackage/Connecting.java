package ClientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import CommonPackage.*;
import ServerPackage.GameData;
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
				System.out.println("Received from server:");
				System.out.println(messageFromServer.getCurrentPlayer());
				System.out.println(messageFromServer.getWinner());
				System.out.println(messageFromServer.getPossibleMoves());
				int[][] a = messageFromServer.getBoard();
				for (int i = 0; i < a.length; i++) {
					for (int j = 0; j < a[i].length; j++) {
						System.out.printf("%5d ", a[i][j]);
					}
					System.out.println();
				}

				System.out.println(messageFromServer.isGameRunning());
				System.out.println("Row from server " + messageFromServer.getChosenRow());
				System.out.println("Col from server " + messageFromServer.getChosenCol());

				getDataFromServer(messageFromServer.getBoard(), messageFromServer.getChosenRow(),
						messageFromServer.getChosenCol(), messageFromServer.isGameRunning(),
						messageFromServer.getCurrentPlayer(), messageFromServer.getPossibleMoves(),
						messageFromServer.getMyColor(),messageFromServer.getWinner());

				if (messageFromServer.getWinner() != GameFlowClient.EMPTY) {
					if (messageFromServer.getWinner() == GameFlowClient.getMyColor()) {
						 CheckersGame.infoLabel.setText("You won!");
//						System.out.println("You won!");
						CheckersGame.startButton.setEnabled(true);
						CheckersGame.stopButton.setEnabled(false);

						break;
					} else {
						 CheckersGame.infoLabel.setText("You lose!");
//						System.out.println("You lose!");
						CheckersGame.startButton.setEnabled(true);
						CheckersGame.stopButton.setEnabled(false);

						break;
					}
				}

			} catch (ClassNotFoundException e) {
				System.out.println("Class not found.");
			} catch (IOException e) {
				System.out.println("IOException2.");

			}

		}

	}

	private void getDataFromServer(int[][] board, int chosenRow, int chosenCol, boolean gameRunning, int currentPlayer,
			CheckersMove[] possibleMoves, int myColor, int winner) {
		GameFlowClient.setBoard(board);
		GameFlowClient.setChosenRow(chosenRow);
		GameFlowClient.setChosenCol(chosenCol);
		GameFlowClient.setGameRunning(gameRunning);
		GameFlowClient.setCurrentPlayer(currentPlayer);
		GameFlowClient.setPossibleMoves(possibleMoves);
		GameFlowClient.setMyColor(myColor);
		GameFlowClient.setWinner(winner);

		System.out.println("OnClient");
		System.out.println(GameFlowClient.getBoard());
		// System.out.println(GameFlowClient.get);
		// System.out.println(messageFromServer.getWinner());
		// System.out.println(messageFromServer.getPossibleMoves());
		// System.out.println(messageFromServer.getBoard());
		// System.out.println(messageFromServer.isGameRunning());

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
