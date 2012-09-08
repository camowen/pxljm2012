package game.networking;



import game.Entity;
import game.Globals;
import game.Mob;
import game.rooms.Room;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;


public class ClientNetworking {
	
	private static OutputStream socketOut;
	private static InputStream socketIn;
	
	public static boolean init(String host, int port) {
		try{
			Socket s = new Socket(host, port);
			socketOut = s.getOutputStream();
			socketIn = s.getInputStream();
		} catch(IOException e) {
			return false;
		}
		
		return true;
	}
	
	public static void poll() {
		try{
			while(socketIn.available() != 0) {
				byte[] data = new byte[100];
				socketIn.read(data, 0, 1);
				switch(data[0]) {
				case Protocol.PTYPE_UPDATE:
					getUpdate();
					break;
				case Protocol.PTYPE_SHOT:
					getShot();
					break;
				case Protocol.PTYPE_DESPAWN:
					getDespawn();
					break;
				case Protocol.PTYPE_SYNC_ROOM:
					getRoomSync();
					break;
				}
			}
		} catch(IOException e) {
			System.out.println("Error Polling Network: "+e);
		}
	}
	
	private static void getUpdate() throws IOException {
		byte[] data = new byte[21];
		socketIn.read(data, 0, 21);
		
		byte pid = data[0];
	
		ByteBuffer buf = ByteBuffer.wrap(data, 1, 20);
		float xPos = buf.getFloat();
		float yPos = buf.getFloat();
		float spdX = buf.getFloat();
		float spdY = buf.getFloat();
		float thta = buf.getFloat();
		
		if(!Enemies.mobMap.containsKey(pid))
			Enemies.mobMap.put(pid, new Mob(xPos, yPos, thta));
		else
			Enemies.mobMap.get(pid).networkUpdate(xPos, yPos, spdX, spdY, thta);
	}
	
	private static void getShot() throws IOException {
		byte[] data = new byte[18];
		socketIn.read(data, 0, 18);
		
		byte pid = data[0];
		byte dmg = data[1];
		
		ByteBuffer buf = ByteBuffer.wrap(data, 2, 16);
		float fromX = buf.getFloat();
		float fromY = buf.getFloat();
		float toX = buf.getFloat();
		float toY = buf.getFloat();
		
		// TODO create shot graphic (Line(fromX, fromY, toX, toY)
		if(pid == 0x0f) {
			// do {dmg} damage to our player
		}
	}
	
	private static void getDespawn() throws IOException {
		byte[] data = new byte[2];
		socketIn.read(data, 0, 2);
		
		byte pid = data[0];
		byte despawnType = data[1];
		
		Enemies.mobMap.remove(pid);
		if(despawnType == Protocol.DESPAWN_TYPE_DEATH);
			// TODO draw blood splatter
	}
	
	private static void getRoomSync() throws IOException {
		byte[] data = new byte[2];
		socketIn.read(data, 0, 2);
		
		byte map = data[0];
		byte numAssets = data[1];
		
		Set<Entity> removeUs = new HashSet<Entity>();
		
		for(Entity e : Room.getRooms().get(map).getEntities())
			if(e.type != Globals.ASSET_TYPE_IGNORE)
				removeUs.add(e);
		
		Room.getRooms().get(map).getEntities().removeAll(removeUs);
		
		System.out.println("GOT MAP! "+map);
		
		data = new byte[numAssets * 4];
		socketIn.read(data, 0, numAssets * 4);
		for(int i=0; i<numAssets; i++) {
			byte assetID = data[i * 4];

			double assetX  = data[i * 4 + 1] * 25.0;
			double assetY  = data[i * 4 + 2] * 25.0;
			double rotation = data[i * 4 + 3] * Math.PI / 2;
			
			System.out.println("["+i*4 +"] "+assetID + " : "+assetX+", "+assetY+" @ "+rotation);
			
			if(assetID == Globals.ASSET_TYPE_IGNORE) continue;
			
			Entity e = new Entity(assetX, assetY, rotation, assetID);
			Room.getRooms().get(map).getEntities().add(e);
		}
	}
	
	// --- OUTBOUND ---
	
	public static void sendUpdate(float xPosition, float yPosition, float speedX, float speedY, float orientation) throws IOException {
		byte[] packet = new byte[21];
		packet[0] = Protocol.PTYPE_UPDATE;
		ByteBuffer buf = ByteBuffer.allocate(20);
		buf.putFloat(xPosition);
		buf.putFloat(yPosition);
		buf.putFloat(speedX);
		buf.putFloat(speedY);
		buf.putFloat(orientation);
		buf.rewind();
		buf.get(packet, 1, 20);
		
		sendPacket(packet, 0, 21);
	}
	
	public static void sendShot(byte hitPID, byte damage, float xPositionFrom, float yPositionFrom, float xPositionTo, float yPositionTo) throws IOException {
		byte[] packet = new byte[19];
		packet[0] = Protocol.PTYPE_SHOT;
		packet[1] = hitPID;
		packet[2] = damage;
		
		ByteBuffer buf = ByteBuffer.allocate(16);
		buf.putFloat(xPositionFrom);
		buf.putFloat(yPositionFrom);
		buf.putFloat(xPositionTo);
		buf.putFloat(yPositionTo);
		
		buf.get(packet, 3, 16);
		
		sendPacket(packet, 0, 19);
	}
	
	public static void sendChangeRoom(byte roomID) throws IOException {
		byte[] packet = new byte[2];
		packet[0] = Protocol.PTYPE_ROOM;
		packet[1] = roomID;
		
		sendPacket(packet, 0, 2);
	}
	
	public static void sendDisconnect() throws IOException {
		byte[] packet = new byte[1];
		packet[0] = Protocol.PTYPE_DISCONNECT;
		
		sendPacket(packet, 0, 1);
	}
	
	private static void sendPacket(byte[] packet, int off, int len) throws IOException {
		socketOut.write(packet, off, len);
		socketOut.flush();
	}
	
}
