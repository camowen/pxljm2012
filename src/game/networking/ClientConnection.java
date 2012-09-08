package game.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {

	private Socket socket;
	
	private OutputStream socketOut;
	private InputStream socketIn;
	
	public byte map = 1;
	private byte id = 0;
	
	public ClientConnection(Socket s, byte id) {
		
		try{
			socket = s;
			this.id = id;
			
			socketOut = s.getOutputStream();
			socketIn = s.getInputStream();
			
			System.out.println("Spawned ClientConnection "+id);
		} catch(IOException e) {
			System.out.println("Some Shit Happened! OHNOEZ! "+e);
		}
		
		new Thread(this).start();
	}
	
	private void floodMap(byte[] packet, int off, int len) throws IOException {
		for(ClientConnection c : Server.clients) {
			if((c.map == map) && (c.id != id)) {
				c.sendPacket(packet, off, len);
			}
		}
	}
	
	public void sendPacket(byte[] packet, int off, int len) throws IOException {
		socketOut.write(packet, off, len);
		socketOut.flush();
	}

	@Override
	public void run() {
		while(socket.isConnected()) {
			byte[] packet = new byte[50];
			
			try{
				socketIn.read(packet, 0, 1);
				//System.out.printf("GOT %02x\n", packet[0]);
				switch(packet[0]) {
				case Protocol.PTYPE_UPDATE:
					packet[1] = id;
					socketIn.read(packet, 2, 20);
					floodMap(packet, 0, 20);
					break;
				case Protocol.PTYPE_DESPAWN:
					socketIn.read(packet, 1, 2);
					floodMap(packet, 0, 3);
					break;
				case Protocol.PTYPE_SHOT:
					socketIn.read(packet, 1, 18);
					if(packet[1] == id)
						packet[1] = 0x0f;
					else
						packet[1] = 0x00;
					floodMap(packet, 0, 19);
					break;
				case Protocol.PTYPE_ROOM:
					socketIn.read(packet, 0, 1);
					map = packet[0];
					break;
				case Protocol.PTYPE_DISCONNECT:
					socketIn.read(packet, 2, 1);
					packet[1] = id;
					floodMap(packet, 0, 3);
					break;
				}
				
			} catch(IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("ClientConnection "+id+" disconnected.");
	}
	
}
