package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;

public class Checkers_server {

	private static final int SERVER_PORT = 8901;
	private static int matchNumber = 1;
//	private static final int TIMEOUT= 10000;

	public static void main(String[] args) throws IOException {

		ServerSocket serversocket = new ServerSocket(SERVER_PORT);
		System.out.println("Checkers server is Running");
		
	    //Set Timeout
//		serversocket.setSoTimeout(TIMEOUT);

		try {
			while (true) {

				Match match = new Match(matchNumber);
				System.out.println("Match number " + matchNumber + " created.");
				System.out.println("Waiting for players...");

				try{
					// TODO: wyodrebnic klase player z klasy room
					Match.Player playerWhite = match.new Player(serversocket.accept(), GameData.WHITE);
					System.out.println("Match #" + matchNumber + ": player #1 connected.");

					Match.Player playerBlack = match.new Player(serversocket.accept(), GameData.BLACK);
					System.out.println("Match #" + matchNumber + ": player #2 connected.");

					playerWhite.setOpponent(playerBlack);
					playerBlack.setOpponent(playerWhite);
					// TODO:room.setCurrentPlayerMaster(playerWhite);
//					room.gameFlow.startNewGame();
					playerWhite.start();
					playerBlack.start();

					matchNumber++;
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
