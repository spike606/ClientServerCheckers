package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;

public class Checkers_server {

	private static final int SERVER_PORT = 8901;
	private static int roomNumber = 1;
//	private static final int TIMEOUT= 10000;

	public static void main(String[] args) throws IOException {

		ServerSocket serversocket = new ServerSocket(SERVER_PORT);
		System.out.println("Checkers server is Running");
		
	    //Set Timeout
//		serversocket.setSoTimeout(TIMEOUT);

		try {
			while (true) {

				Room room = new Room();
				System.out.println("Room number " + roomNumber + " created.");
				System.out.println("Waiting for players...");

				try{
					// TODO: wyodrebnic klase player z klasy room
					Room.Player playerWhite = room.new Player(serversocket.accept(), GameData.WHITE);
					System.out.println("Room #" + roomNumber + ": player #1 connected.");

					Room.Player playerBlack = room.new Player(serversocket.accept(), GameData.BLACK);
					System.out.println("Room #" + roomNumber + ": player #2 connected.");

					playerWhite.setOpponent(playerBlack);
					playerBlack.setOpponent(playerWhite);
					// TODO:room.setCurrentPlayerMaster(playerWhite);
//					room.gameFlow.startNewGame();
					playerWhite.start();
					playerBlack.start();

					roomNumber++;
				}
				catch(IOException e){
					System.out.println("IOError");
				}

			}
		} finally {
			serversocket.close();
		}

	}

}
