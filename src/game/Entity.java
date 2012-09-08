package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Entity {

	private double x;
	private double y;
	private double angle;
	private double scale;
	
	private boolean passable;
	
	private boolean hit = false;
	
	private int health;
	
	private BufferedImage sprite;
    private Rectangle hitbox;
    
    public Entity(double x, double y, double rotation, double scale, BufferedImage sprite){
    	this.x = x;
    	this.y = y;
    	this.angle = rotation;
    	this.scale = scale;
    	this.sprite = sprite;
    	AffineTransform at = new AffineTransform();
    	at.scale(scale, scale);
    	at.translate(sprite.getWidth()/2, sprite.getHeight()/2);
    	at.rotate(rotation);
    	at.translate(-sprite.getWidth()/2, -sprite.getHeight()/2);
    	Point2D p = new Point(sprite.getWidth(), sprite.getHeight());
    	Point2D p2 = new Point();
    	at.transform(p, p2);
    	if(Globals.DEBUG_MODE){
    		System.out.println(p + " ==> " + p2);
    	}
    	hitbox = new Rectangle((int)x, (int)y, (int)p2.getX(), (int)p2.getY());
    }
    
    public Entity(double x, double y){
    	this.x = x;
    	this.y = y;
    }
    
    public boolean contains(double x, double y){
    	if(Globals.DEBUG_MODE){
    		//System.out.println("Rectangle: " + hitbox.toString());
    	}
    	return hitbox.contains(x,y);
    }
    
    public void hit(){
    	hit = true;
    }
    
    public void render(Graphics g,int roomx, int roomy){
    	AffineTransform at = new AffineTransform();
    	at.translate(x+roomx, y+roomy);
    	at.scale(scale,scale);
    	at.translate(sprite.getWidth()/2, sprite.getWidth()/2);
    	at.rotate(angle);
    	at.translate(-sprite.getWidth()/2, -sprite.getWidth()/2);
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.drawImage(sprite, at,null);
    	
    	if(hit){
    		g.setColor(Color.RED);
    		g.fillOval((int)x, (int)y, 10, 10);
    	}
    }
	
}
