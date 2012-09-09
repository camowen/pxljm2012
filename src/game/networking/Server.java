package game.networking;

import game.Globals;
import game.rooms.Bathroom;
import game.rooms.Furnace;
import game.rooms.Library;
import game.rooms.Office;
import game.rooms.ParkingLot;
import game.rooms.Room;
import game.rooms.Stairs;
import game.rooms.Warehouse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Server {
	public static final int PORT = 8008;
	
	public static List<Room> rooms = new ArrayList<Room>(6);
	public static Set<ClientConnection> clients = new HashSet<ClientConnection>();
	
	// ARGS Port (Optional)
	public static void main(String args[]) {
		byte newClientID = 1;
			
		try{
			rooms.add(Globals.ROOM_PARKINGLOT, new ParkingLot());
			rooms.add(Globals.ROOM_LIBRARY, new Library());
			rooms.add(Globals.ROOM_BATHROOM, new Bathroom());
			rooms.add(Globals.ROOM_OFFICE, new Office());
			rooms.add(Globals.ROOM_WAREHOUSE, new Warehouse());
			rooms.add(Globals.ROOM_STAIRS, new Stairs());
			rooms.add(Globals.ROOM_FURNACE, new Furnace());
			
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("Server Listening on port "+ss.getLocalPort());
			
			while(true) {
				Socket s = ss.accept();
				clients.add(new ClientConnection(s, newClientID++));
			}
			
		} catch (IOException e) {
			System.out.println("Could not create socket: "+e);
		}
	}
	
	public static void floodClients(byte[] packet, int off, int len) throws IOException {
		synchronized (clients) {
			for(ClientConnection c : clients)
				c.sendPacket(packet, off, len);
		}
	}
}
