package game.networking;

public class Protocol {
	public static final byte PTYPE_UPDATE = 0x01;
	public static final byte PTYPE_DESPAWN = 0x02;
		public static final byte DESPAWN_TYPE_DEATH = 0x01;
		public static final byte DESPAWN_TYPE_DISCONNECT = 0x02;
		public static final byte DESPAWN_TYPE_CHANGEMAP = 0x03;
	public static final byte PTYPE_DISCONNECT = 0x0F;
	public static final byte PTYPE_SHOT = 0x11;
	public static final byte PTYPE_ROOM = 0x21;
	
	public static final byte PTYPE_SYNC_ROOM = 0x71;
	
	// Update Packet
	// PTYPE (1), xPos (4), yPos (4), spdX (4), spdY (4), theta (4)
	
	// Shot Packet
	// PTYPE (1), pid (1), dmg (1)
	
	// Change Room Packet
	// PTYPE (1), room (1)
	
	// Despawn Packet
	// PTYPE (1), pid (1)
}
