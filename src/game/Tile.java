package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Tile {
	
	public enum Type{Floor,Wall};
	
	private static Map<Type,BufferedImage> images = new HashMap<Type,BufferedImage>();
	
	private static boolean initd = false;
	
	public static void initMap(){
		if(!initd){
			BufferedImage floor = new BufferedImage(50,50,BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D w = floor.createGraphics();
			w.setColor(Color.gray);
			w.fillRect(0, 0, 50, 50);
			w.setColor(Color.black);
			w.drawRect(0, 0, 50, 50);
			images.put(Type.Floor, floor);
			
			
			//images.put(Type.Wall, wall);
			
			initd = true;
		}
	}
	
	private Type type;
	private boolean passable = true;
	
	public Tile(Type t, boolean passable){
		Tile.initMap();
		type = t;
		this.passable = passable;
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(images.get(type), x, y, null);
	}
	
}
