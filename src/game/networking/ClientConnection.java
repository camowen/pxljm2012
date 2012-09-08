package game.networking;

import game.Entity;

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
			
			syncMaps();
		} catch(IOException e) {
			System.out.println("Some Shit Happened! OHNOEZ! "+e);
		}
		
		new Thread(this).start();
	}
	
	private void syncMaps() throws IOException {
		System.out.println("["+id+"]Syncing "+Server.rooms.size()+" Maps...");
		for(byte i=0; i < Server.rooms.size(); i++)
			syncRoom(i);
	}
	
	public void syncRoom(byte room) throws IOException {
		byte numEntities = (byte) Server.rooms.get(room).getEntities().size();
		
		byte[] packet = new byte[3 + numEntities*4];
		packet[0] = Protocol.PTYPE_SYNC_ROOM;
		packet[1] = room;
		packet[2] = numEntities;
		
		System.out.println("ROOM: "+room);
		
		for(int i=0; i<numEntities; i++) {
			Entity e = Server.rooms.get(room).getEntities().get(i);
			packet[3 + i*4] = e.type;
			packet[4 + i*4] = (byte) (e.x / 25.0);
			packet[5 + i*4] = (byte) (e.y / 25.0);
			packet[6 + i*4] = (byte) (e.angle * 2 / Math.PI);
			
			System.out.println(e.type+" : "+e.x+", "+e.y+" @ "+e.angle);
		}
		
		sendPacket(packet, 0, 3 + numEntities*4);
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
					packet[0] = Protocol.PTYPE_DESPAWN;
					packet[1] = id;
					packet[2] = Protocol.DESPAWN_TYPE_DISCONNECT;
					floodMap(packet, 0, 3);
					
					socket.close();
					break;
				}
				
			} catch(IOException e) {
				System.out.println(e);
			}
		}
		System.out.println("ClientConnection "+id+" disconnected.");
	}
	
}
