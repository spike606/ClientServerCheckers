package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;


public class Checkers_server {

	public static void main(String[] args) throws IOException {

        ServerSocket serversocket = new ServerSocket(8901);
        System.out.println("Checkers server is Running");

        try {
            while (true) {
            	
            	Room room = new Room();
            	

            	
            	//TODO: wyodrebnic klase player z klasy room
            	Room.Player playerWhite = room.new Player(serversocket.accept(), "White");
            	Room.Player playerBlack = room.new Player(serversocket.accept(), "Black");
            	playerWhite.setOpponent(playerBlack);
            	playerBlack.setOpponent(playerWhite);
            	room.setCurrentPlayerMaster(playerWhite);
            	playerWhite.start();
            	playerBlack.start();
            }
        } finally {
        	serversocket.close();
        }

		
		
	}

}
