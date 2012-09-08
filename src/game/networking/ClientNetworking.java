package game.networking;



import game.Mob;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;


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
				case Protocol.PTYPE_SHOOT:
					
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
	
	private static void getDespawn() throws IOException {
		byte[] data = new byte[2];
		socketIn.read(data, 0, 2);
		
		byte pid = data[0];
		byte despawnType = data[1];
		
		// TODO remove mob {pid}
		//if(despawnType == Protocol.DESPAWN_TYPE_DEATH)
			// draw blood splatter
	}
	
	private static void getRoomSync() throws IOException {
		byte[] data = new byte[2];
		socketIn.read(data, 0, 2);
		
		byte map = data[0];
		byte numAssets = data[1];
		
		// TODO clear map asset list
		
		data = new byte[numAssets * 3];
		socketIn.read(data, 0, numAssets * 3);
		for(int i=0; i<numAssets; i++) {
			byte assetID = data[i * 3];
			byte assetX  = data[i * 3 + 1];
			byte assetY  = data[i * 3  +2];
			
			// TODO create Asset, add to map
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
	
	public static void sendShoot(byte type, float xPosition, float yPosition, float orientation) throws IOException {
		byte[] packet = new byte[14];
		packet[0] = Protocol.PTYPE_SHOOT;
		packet[1] = type;
		ByteBuffer buf = ByteBuffer.allocate(12);
		buf.putFloat(xPosition);
		buf.putFloat(yPosition);
		buf.putFloat(orientation);
		buf.rewind();
		buf.get(packet, 2, 12);
		
		sendPacket(packet, 2, 14);
	}
	
	private static void sendPacket(byte[] packet, int off, int len) throws IOException {
		socketOut.write(packet, off, len);
		socketOut.flush();
	}
	
}
