package game.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class Server {
	public static final int PORT = 8008;
	
	public static Set<ClientConnection> clients = new HashSet<ClientConnection>();
	
	// ARGS Port (Optional)
	public static void main(String args[]) {
		byte newClientID = 1;
		
		try{ 
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
		for(ClientConnection c : clients)
			c.sendPacket(packet, off, len);
	}
}
