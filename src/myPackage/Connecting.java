package myPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CommonPackage.Message;

public class Connecting extends Thread {

	private static final int SERVER_PORT = 8901;
	private static final String HOST_NAME = "localhost";

	private Socket mySocket;
	private ObjectInputStream myInput;
	private ObjectOutputStream myOutput;

	Object object;

	private Message messageToServer;
	private Message messageFromServer;

	public Connecting() {

	}

	@Override
	public void run() {
		try {
			// Setup networking

			mySocket = new Socket(HOST_NAME, SERVER_PORT);
			myInput = new ObjectInputStream(mySocket.getInputStream());
			myOutput = new ObjectOutputStream(mySocket.getOutputStream());

		} catch (IOException e1) {
			System.out.println("IOException.");
		}
		// while (true) {
		try {

			object = myInput.readObject();
			messageFromServer = (Message) object;
			System.out.println(messageFromServer.getTextMessage());

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found.");
		} catch (IOException e) {
			System.out.println("IOException.");

		}

		// }

	}

}
