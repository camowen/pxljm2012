package game.rooms;

import game.Entity;
import game.Globals;
import game.Mob;
import game.networking.Enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import game.Player;

public class Room {
	
	protected List<Entity> entities;
	protected List<Player> players;
	protected BufferedImage background;
	
	protected Room north;
	protected Room east;
	protected Room south;
	protected Room west;
	
	protected boolean hasPlayer = false;
	
	public Room(){
		
		this.background = null;
		entities = new ArrayList<Entity>();
		players = new ArrayList<Player>();
		
		BufferedImage wall = new BufferedImage(265,10,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D w = wall.createGraphics();
		w.setColor(Color.black);
		w.fillRect(0, 0, 265, 5);
		entities.add(new Entity(0, 0,0,1.0, wall));
		entities.add(new Entity(335, 0,0,1.0, wall));
		entities.add(new Entity(0, 595,0,1.0, wall));
		entities.add(new Entity(335, 595,0,1.0, wall));
		
		wall = new BufferedImage(10,265,BufferedImage.TYPE_4BYTE_ABGR);
		w = wall.createGraphics();
		w.setColor(Color.black);
		w.fillRect(0, 0, 5, 265);
		entities.add(new Entity(0, 0,0,1.0, wall));
		entities.add(new Entity(0, 335,0,1.0, wall));
		entities.add(new Entity(595, 0,0,1.0, wall));
		entities.add(new Entity(595, 335,0,1.0, wall));
		
	}
	
	public void render(Graphics g, int roomx, int roomy){
		double scaleFactor = (double)Globals.WINDOW_WIDTH/(double)background.getWidth();
		AffineTransform at = new AffineTransform();
		at.translate(roomx, roomy);
		at.scale(scaleFactor, scaleFactor);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(background, at, null);
		for(Entity e : entities){
			e.render(g,roomx,roomy);
		}

//		for(Mob networkPlayer : Enemies.mobMap.values()) {
//			networkPlayer.render(g, roomx, roomy);
//		}
		
		
		if(Globals.DEBUG_MODE){
			g.setColor(Color.GREEN);
			for(int x=0;x<12;x++){
				for(int y=0;y<12;y++){
					g.drawRect(roomx+x*50,roomy+y*50,50,50);
				}
			}
		}
		
		for(Player p : players){
			p.render(g);
		}
		
//		if(hasPlayer){
//		 north.render(g,roomx,roomy-600,false);
//		 east.render(g,roomx+600,roomy,false);
//		 south.render(g,roomx,roomy+600,false);
//		 west.render(g,roomx-600,roomy,false);
//		}
	}
	

	public void render(Graphics g, int roomx, int roomy, boolean hasPlayer){
		double scaleFactor = (double)Globals.WINDOW_WIDTH/(double)background.getWidth();
		AffineTransform at = new AffineTransform();
		at.translate(roomx, roomy);
		at.scale(scaleFactor, scaleFactor);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(background, at, null);
		for(Entity e : entities){
			e.render(g,roomx,roomy);
		}

//		for(Mob networkPlayer : Enemies.mobMap.values()) {
//			networkPlayer.render(g, roomx, roomy);
//		}
		
		
		if(Globals.DEBUG_MODE){
			g.setColor(Color.GREEN);
			for(int x=0;x<12;x++){
				for(int y=0;y<12;y++){
					g.drawRect(roomx+x*50,roomy+y*50,50,50);
				}
			}
		}
		
		for(Player p : players){
			p.render(g);
		}
		
	}
	
	public void removePlayer(Player p){
		players.remove(p);
		if(players.isEmpty()){
			hasPlayer = false;
		}
	}
	
	public boolean canMove(Player p, double playerX, double playerY){
		for(Entity e : entities){
			if(e.contains(playerX, playerY)){
				return false;
			}
		}
		if(playerX < 0){
			//Move to west room
			System.out.println("Moving to west room");
			removePlayer(p);
			west.addPlayer(p, 600+playerX, playerY);
		} else if(playerX > 600){
			//Move east
			System.out.println("Moving to east room");
			removePlayer(p);
			east.addPlayer(p, playerX-600, playerY);
		}else if(playerY < 0){
			System.out.println("Moving to north room");
			removePlayer(p);
			north.addPlayer(p, playerX, 600+playerY);
		}else if(playerY > 600){
			System.out.println("Moving to south room");
			removePlayer(p);
			south.addPlayer(p, playerX, playerY-600);
		}
		return true;
	}
	
    public Entity shoot(double playerX, double playerY, double angle){
    	
    	boolean collide = false;
    	angle -= Math.PI/2.0;
    	double r = 0.00;
    	while(!collide && r < 1000.00){
    		double x = playerX+  r * Math.cos(angle);
    		double y = playerY + r * Math.sin(angle);
    		for(Entity e : entities){
    			if(e.contains(x, y)){
    				collide = true;
    				e.hit();
    				System.out.println(e);
    				break;
    			}
    		}
    		r+=1.0;
    	}
    	System.out.println(collide);
    	return null;
    }
    
    public void addPlayer(Player p, double x, double y){
    	this.players.add(p);
    	this.hasPlayer = true;
    	p.setPositionInRoom(x, y);
    }
	
}
