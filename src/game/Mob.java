package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Mob extends Entity {

	protected double x;
	protected double y;
	
	protected double vx = 0;
	protected double vy = 0;
	
	protected double angle = 0.00;
	
	protected double targetX = 0.00;
	protected double targetY = 0.00;		
	
	protected BufferedImage sprite;
	
	public Mob(double x, double y, double angle){
		super(x,y);
		this.x=x;
		this.y=y;
		this.angle = angle;
		
		sprite = new BufferedImage(Globals.PLAYER_WIDTH, Globals.PLAYER_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	public void render(Graphics g){
		//g.fillRect((int)x, (int)y, Globals.PLAYER_WIDTH, Globals.PLAYER_HEIGHT);
		
		AffineTransform at = new AffineTransform();
		at.translate(x,y);
		at.rotate(angle);
		at.translate(-Globals.PLAYER_WIDTH/2, -Globals.PLAYER_HEIGHT/2);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(sprite, at, null);
	}
	
	public void move(long deltat){
		double seconds = (deltat/1000.00);
		double dx = vx*seconds;
		double dy = vy*seconds;
		x += dx;
		y += dy;
		pointAt();
	}
	
	public void pointAt(){
		//My x and my y
		double myx =this.x;
		double myy = this.y;
		//Transform the target
		double targetx = targetX - myx;
		double targety = targetY - myy;
		angle = Math.atan2(targety, targetx) + Math.PI/2.0;
	}
	
	public void pointAt(int x, int y){
		targetX = x;
		targetY = y;
	}
	
	public void addVX(double vx){
		this.vx += vx;
	}
	
	public void addVY(double vy){
		this.vy += vy;
	}
	
	public double getVX(){
		return vx;
	}
	
	public double getVY(){
		return vy;
	}
}
