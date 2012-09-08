package game.networking;

import game.Entity;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapChanger implements Runnable {

	private static final long SLEEPTIME = 60000;
	
	private Map<Byte, List<Entity>> mapMap = new HashMap<Byte, List<Entity>>();
	
	public MapChanger(int numberMaps) {
		for(byte b = 0; b < numberMaps; b++)
			mapMap.put(b, new ArrayList<Entity>());
		
		jumbleMap();
	}
	
	private void jumbleMap() {
		// TODO generate maps somehow, insert into arrays
	}
	
	private void syncMap(byte map) throws IOException {
		// TODO finish function for synchronising maps with host (sending info)
		byte numEntities = (byte) mapMap.get(map).size();
		
		byte[] packet = new byte[3 + numEntities*4];
		packet[0] = Protocol.PTYPE_SYNC_ROOM;
		packet[1] = map;
		packet[2] = numEntities;
		
		for(int i=0; i<numEntities; i++) {
			Entity e = mapMap.get(map).get(i);
			packet[3 + i*4] = e.type;
			packet[4 + i*4] = (byte) (e.x / 25.0);
			packet[5 + i*4] = (byte) (e.y / 25.0);
			packet[6 + i*4] = (byte) (e.angle * 2 / Math.PI);
		}
		
		Server.floodClients(packet, 0, 3 + numEntities*3);
	}
	
	@Override
	public void run() {
		while(true) {
			//jumbleMap();
			// Sleep for a little while before randomly shuffling maps again
			try{ Thread.sleep(SLEEPTIME); } catch(InterruptedException e) {}
		}
	}
	
}
