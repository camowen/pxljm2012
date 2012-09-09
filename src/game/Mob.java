package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Mob extends Entity {

	public byte id = -1;
	
	protected double vx = 0;
	protected double vy = 0;

	protected double angle = 0.00;

	protected double targetX = 0.00;
	protected double targetY = 0.00;

	protected boolean dead = false;
	
	private int walkFrame = 0;

	public Mob(double x, double y, double angle) {
		super(x, y);
		this.x = x;
		this.y = y;
		scale = 1.0;
		this.angle = angle;
		passable = true;
		
		health = 3;
		
		sprite = new BufferedImage(Globals.PLAYER_HEIGHT, Globals.PLAYER_WIDTH, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = sprite.createGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(12, 12, 25, 25);
		g.setColor(Color.white);
		g.fillOval(20, 0, 10, 10);

		hitbox = new Rectangle((int) x, (int) y, sprite.getWidth(),
				sprite.getHeight());
		//setImage();
	}

	// Make zombies
	public Mob(double x, double y) {
		super(x, y);
		this.x = x;
		this.y = y;
		scale = 1.0;
		passable = false;
		health = 5;
		sprite = new BufferedImage(50, 50, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = sprite.createGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(12, 12, 25, 25);
		g.setColor(Color.white);
		g.fillOval(20, 0, 10, 10);

		hitbox = new Rectangle((int) x, (int) y, sprite.getWidth(),
				sprite.getHeight());
	}

	public void update(long deltat) {
		double seconds = (deltat / 1000.00);
		double dx = vx * seconds;
		double dy = vy * seconds;
		x += dx;
		y += dy;
		pointAt();
	}

	public void setImage() {
		sprite.getGraphics().setColor(new Color(255, 255, 255, 255));
		sprite.getGraphics().fillRect(0, 0, Globals.PLAYER_WIDTH,
				Globals.PLAYER_HEIGHT);
	}

	public void networkUpdate(double xLoc, double yLoc, double dX, double dY,
			double orientation) {
		x = xLoc;
		y = yLoc;
		vx = dX;
		vy = dY;
		angle = orientation;
	}

	public void pointAt() {
		// My x and my y
		double myx = this.x;
		double myy = this.y;
		// Transform the target
		double targetx = targetX - myx;
		double targety = targetY - myy;
		angle = Math.atan2(targety, targetx) + Math.PI / 2.0;
	}

	public void pointAt(int x, int y) {
		targetX = x;
		targetY = y;
	}

	public void pointAt(Player p) {
		this.pointAt(p.getX(), p.getY());
		pointAt();
	}

	public void addVX(double vx) {
		this.vx += vx;
	}

	public void addVY(double vy) {
		this.vy += vy;
	}

	public double getVX() {
		return vx;
	}

	public double getVY() {
		return vy;
	}

	public void hit() {
		health--;
		if (health <= 0) {
			//kill();
			dead = true;
		}
		
		System.out.println("I GOT HIT!");
	}

	public boolean contains(Player p) {
		if (dead)
			return false;
		
		double tx = (this.x+25) - p.getX();
		double ty = (this.y+25) - p.getY();

		if (Math.sqrt(tx * tx + ty * ty) <= 25) {
			return true;
		}
		return false;
	}
	
	public boolean contains(int x, int y) {
		if (dead)
			return false;
		
		double tx = (this.x) - x;
		double ty = (this.y) - y;

		if (Math.sqrt(tx * tx + ty * ty) <= 25) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics g, int roomx, int roomy) {
		if(dead) return;
		
		Graphics2D s = (Graphics2D) sprite.getGraphics();
		s.setBackground(new Color(0, 0, 0, 0));
		s.clearRect(0, 0, Globals.PLAYER_WIDTH, Globals.PLAYER_HEIGHT);
		if(vx != 0.0 || vy != 0.0){
			if(walkFrame < 10){
				s.drawImage(Player.feetStep1, 0, 0, null);
				walkFrame++;
			} else {
				s.drawImage(Player.feetStep2, 0, 0, null);
				walkFrame++;
				if(walkFrame >=20){
					walkFrame = 0;
				}
			}
		} else {
			s.drawImage(Player.feetIdle,0,0,null);
		}
		
		s.drawImage(Player.armsIdle, 0, 0, null);
		
		s.drawImage(Player.character,0,0,null);
		
		if(Globals.DEBUG_MODE){
			s.setColor(Color.red);
			s.drawOval(Globals.PLAYER_WIDTH/4, Globals.PLAYER_HEIGHT/4, Globals.PLAYER_WIDTH/2, Globals.PLAYER_HEIGHT/2);
		}
		
		AffineTransform at = new AffineTransform();
		at.translate(roomx+x, roomy+y);
		at.scale(0.25, 0.25);
		at.rotate(angle);
		at.translate(-Globals.PLAYER_WIDTH/2, -Globals.PLAYER_HEIGHT/2);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(sprite, at, null);
	}
}
