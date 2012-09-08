package game.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {

	
	private OutputStream socketOut;
	private InputStream socketIn;
	
	public byte map = 1;
	private byte id = 0;
	
	public ClientConnection(Socket s, byte id) {
		
		try{
			socketOut = s.getOutputStream();
			socketIn = s.getInputStream();
			
			System.out.println("Spawned ClientConnection "+id);
		} catch(IOException e) {
			System.out.println("Some Shit Happened! OHNOEZ! "+e);
		}
		
		new Thread(this).start();
	}
	
	private void floodMap(byte[] packet, int off, int len) throws IOException {
		for(ClientConnection c : Server.clients)
			if(c.map == map && c != this)
				c.sendPacket(packet, off, len);
	}
	
	public void sendPacket(byte[] packet, int off, int len) throws IOException {
		socketOut.write(packet, off, len);
		socketOut.flush();
	}

	@Override
	public void run() {
		while(true) {
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
				case Protocol.PTYPE_SHOOT:
					socketIn.read(packet, 1, 13);
					floodMap(packet, 0, 13);
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
	}
	
}
