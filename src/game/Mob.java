package game;

import java.awt.Color;
import java.awt.Rectangle;
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
		passable = true;
		sprite = new BufferedImage(Globals.PLAYER_WIDTH, Globals.PLAYER_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		hitbox = new Rectangle((int)x,(int)y,sprite.getWidth(), sprite.getHeight());
		setImage();
	}
	
	public void move(long deltat){
		double seconds = (deltat/1000.00);
		double dx = vx*seconds;
		double dy = vy*seconds;
		x += dx;
		y += dy;
		pointAt();
	}
	
	public void setImage() {
		sprite.getGraphics().setColor(Color.RED);
		sprite.getGraphics().fillRect(0, 0, 50, 50);
	}
	
	public void networkUpdate(double xLoc, double yLoc, double dX, double dY, double orientation) {
		x = xLoc;
		y = yLoc;
		vx = dX;
		vy = dY;
		angle = orientation;
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
	
    public boolean contains(double x, double y) {
		
		double tx = this.x-x;
		double ty = this.y-y;
		
		if(Math.sqrt(tx*tx+ty*ty) <= 25){
			return true;
		}
		return false;
	}
}
