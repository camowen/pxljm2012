package game.rooms;

import game.Entity;
import game.Globals;
import game.Mob;
import game.Player;
import game.TransientEntity;
import game.networking.ClientNetworking;
import game.networking.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Room {

	public byte id = 0;

	private static List<Room> rooms;

	public static List<Room> getRooms() {
		if (rooms == null) {
			rooms = new ArrayList<Room>(6);
			try {
				Room r = new ParkingLot();
				r.id = Globals.ROOM_PARKINGLOT;
				rooms.add(Globals.ROOM_PARKINGLOT, r);
				r = new Library();
				r.id = Globals.ROOM_LIBRARY;
				rooms.add(Globals.ROOM_LIBRARY, r);
				r = new Bathroom();
				r.id = Globals.ROOM_BATHROOM;
				rooms.add(Globals.ROOM_BATHROOM, r);
				r = new Office();
				r.id = Globals.ROOM_OFFICE;
				rooms.add(Globals.ROOM_OFFICE, r);
				r = new Warehouse();
				r.id = Globals.ROOM_WAREHOUSE;
				rooms.add(Globals.ROOM_WAREHOUSE, r);
				r = new Stairs();
				r.id = Globals.ROOM_STAIRS;
				rooms.add(Globals.ROOM_STAIRS, r);
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		return rooms;
	}

	protected List<Entity> entities;

	public List<Entity> getEntities() {
		return entities;
	}

	protected List<Player> players;
	protected BufferedImage background;

	protected Room north;
	protected Room east;
	protected Room south;
	protected Room west;

	protected boolean hasPlayer = false;

	public Room() {

		this.background = null;
		entities = new ArrayList<Entity>();
		players = new ArrayList<Player>();
	}

	public void render(Graphics g, int roomx, int roomy) {
		render(g, roomx, roomy, false);
	}

	public void render(Graphics g, int roomx, int roomy, boolean hasPlayer) {
		double scaleFactor = (double) Globals.WINDOW_WIDTH
				/ (double) background.getWidth();
		AffineTransform at = new AffineTransform();
		at.translate(roomx, roomy);
		at.scale(scaleFactor, scaleFactor);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, at, null);
		synchronized (this) {
			for (Entity e : entities) {
				if (e instanceof Mob) {
					((Mob) e).pointAt(players.get(0));
				}
				e.render(g, roomx, roomy);
			}
		}

		for (Mob networkPlayer : Enemies.mobMap.values()) {
			networkPlayer.render(g, roomx, roomy);
		}

		if (Globals.DEBUG_MODE) {
			g.setColor(Color.GREEN);
			for (int x = 0; x < 12; x++) {
				for (int y = 0; y < 12; y++) {
					g.drawRect(roomx + x * 50, roomy + y * 50, 50, 50);
				}
			}
		}

		for (Player p : players) {
			p.render(g);
		}

	}

	public void removePlayer(Player p) {
		players.remove(p);
		if (players.isEmpty()) {
			hasPlayer = false;
		}
	}

	public boolean canMove(Player p, double playerX, double playerY) {
		for (Entity e : entities) {
			if (e instanceof Mob) {
				if (((Mob) e).contains(p)) {
					return false;
				}
			} else {
				if (!e.isPassable() && e.contains(playerX, playerY)) {
					return false;
				}
			}
		}
		if (playerX < 0) {
			// Move to west room
			System.out.println("Moving to west room");
			removePlayer(p);
			west.addPlayer(p, 600 + playerX, playerY);
		} else if (playerX > 600) {
			// Move east
			System.out.println("Moving to east room");
			removePlayer(p);
			east.addPlayer(p, playerX - 600, playerY);
		} else if (playerY < 0) {
			System.out.println("Moving to north room");
			removePlayer(p);
			north.addPlayer(p, playerX, 600 + playerY);
		} else if (playerY > 600) {
			System.out.println("Moving to south room");
			removePlayer(p);
			south.addPlayer(p, playerX, playerY - 600);
		}
		return true;
	}
	
    public Entity shoot(Player me, double playerX, double playerY, double angle){
    	
    	boolean collide = false;
    	angle -= Math.PI/2.0;
    	double r = 0.00;
    	while(!collide && r < 850.00){
    		double x = playerX+  r * Math.cos(angle);
    		double y = playerY + r * Math.sin(angle);
    		for(Entity e : entities){
    			if(!e.isPassable() && e.contains(x, y)){
    				collide = true;
    				e.hit();
    				System.out.println(e);
    				break;
    			}
    		}
    		for(Mob m : Enemies.mobMap.values()){
    			if(m.contains(x, y)){
    				collide = true;
    				m.hit();
    				if(Globals.CONNECTED)
    					ClientNetworking.sendShot(m.id, (byte) 1, (float) playerX, (float) playerY, (float) x, (float) y);
    				break;
    			}
    		}
    		r+=2.0;
    	}
    	System.out.println(collide);
    	return null;
    }
    
    public void addPlayer(Player p, double x, double y){
    	this.players.add(p);
    	this.hasPlayer = true;
    	p.setCurrentRoom(this);
    	if(Globals.CONNECTED) {
    		Enemies.mobMap.clear();
	    	ClientNetworking.sendChangeRoom(id);
    	}
    	
    	p.setPositionInRoom(x, y);
    }
    
    public void populate(){
    	Entity e;
    	
    	entities.clear();
    	BufferedImage wall = new BufferedImage(265,10,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D w = wall.createGraphics();
		w.setColor(Color.black);
		w.fillRect(0, 0, 265, 5);
		e = new Entity(0, 0, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(335, 0, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(0, 595, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(335, 595, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);

		wall = new BufferedImage(10, 265, BufferedImage.TYPE_4BYTE_ABGR);
		w = wall.createGraphics();
		w.setColor(Color.black);
		w.fillRect(0, 0, 5, 265);
		e = new Entity(0, 0, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(0, 335, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(595, 0, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(595, 335, 0, 1.0, wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
	}

	public void setNorth(Room r) {
		north = r;
	}

	public void setSouth(Room r) {
		south = r;
	}

	public void setEast(Room r) {
		east = r;
	}

	public void setWest(Room r) {
		west = r;
	}

	public void update(long l) {
		for (Entity e : entities) {
			if (e instanceof TransientEntity) {
				e.update(l);
			}
		}
	}
}
