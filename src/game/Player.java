package game;

import game.networking.ClientNetworking;
import game.rooms.Room;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Mob {

	// private String name;

	public static Player player;
	
	public static BufferedImage feetIdle;
	public static BufferedImage feetStep1;
	public static BufferedImage feetStep2;
	public static BufferedImage armsIdle;
	public static BufferedImage armsShoot;
	public static BufferedImage character;

	public static BufferedImage blood;
	public static BufferedImage organ1;
	public static BufferedImage organ2;
	public static BufferedImage organ3;
	public static BufferedImage bone;
	public static BufferedImage rib;

	public static Room currentRoom;
	
	private int walkFrame = 0;
	private int shootFrame = 0;

	private boolean shooting = false;

	public Player(String name) {

		super(300, 300, 0);

		try {
			feetIdle = ImageIO.read(new File(Globals.CHARACTER_FEET_IDLE));
			feetStep1 = ImageIO.read(new File(Globals.CHARACTER_FEET_1));
			feetStep2 = ImageIO.read(new File(Globals.CHARACTER_FEET_2));
			armsIdle = ImageIO.read(new File(Globals.CHARACTER_ARMS_IDLE));
			armsShoot = ImageIO.read(new File(Globals.CHARACTER_ARMS_SHOOT));
			character = ImageIO.read(new File(Globals.CHARACTER_BODY));

			rib = ImageIO.read(new File(Globals.FX_RIB));
			bone = ImageIO.read(new File(Globals.FX_BONE));
			blood = ImageIO.read(new File(Globals.FX_BLOOD));
			organ1 = ImageIO.read(new File(Globals.FX_ORGAN_1));
			organ2 = ImageIO.read(new File(Globals.FX_ORGAN_2));
			organ3 = ImageIO.read(new File(Globals.FX_ORGAN_3));

			sprite = new BufferedImage(Globals.PLAYER_WIDTH,
					Globals.PLAYER_WIDTH, BufferedImage.TYPE_4BYTE_ABGR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		player = this;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room room) {
		currentRoom = room;
	}

	public void shoot() {
		if (!shooting) {
			shooting = true;
//			double r = Math.random();
//			if(r < 0.5){
				SoundSystem.play(Globals.SFX_SHOT);
//			} else {
//				SoundSystem.play(SoundSystem.SFX_SHOT2);
//			}
			currentRoom.shoot(this, x, y, angle);
		}
	}

	public void pointAt() {
		// My x and my y
		double myx = Globals.WINDOW_WIDTH / 2;
		double myy = Globals.WINDOW_HEIGHT / 2;
		// Transform the target
		double targetx = targetX - myx;
		double targety = targetY - myy;
		angle = Math.atan2(targety, targetx) + Math.PI / 2.0;
	}

	public void pointAt(int x, int y) {
		targetX = x;
		targetY = y;
	}

	public void render(Graphics g) {
		// g.fillRect((int)x, (int)y, Globals.PLAYER_WIDTH,
		// Globals.PLAYER_HEIGHT);
		if (dead) {
			return;
		}

		Graphics2D s = (Graphics2D) sprite.getGraphics();
		s.setBackground(new Color(0, 0, 0, 0));
		s.clearRect(0, 0, Globals.PLAYER_WIDTH, Globals.PLAYER_HEIGHT);
		if (vx != 0.0 || vy != 0.0) {
			SoundSystem.startRunning();
			if (walkFrame < 10) {
				s.drawImage(feetStep1, 0, 0, null);
				walkFrame++;
			} else {
				s.drawImage(feetStep2, 0, 0, null);
				walkFrame++;
				if (walkFrame >= 20) {
					walkFrame = 0;
				}
			}
		} else {
			SoundSystem.stopRunning();
			s.drawImage(feetIdle, 0, 0, null);
		}

		if (shooting) {
			if (shootFrame < 5) {
				s.drawImage(armsShoot, 0, 0, null);
				shootFrame++;
			} else {
				s.drawImage(armsIdle, 0, 0, null);
				shootFrame = 0;
				shooting = false;
			}
		} else {
			s.drawImage(armsIdle, 0, 0, null);
		}

		s.drawImage(character, 0, 0, null);

		if (Globals.DEBUG_MODE) {
			s.setColor(Color.red);
			s.drawOval(Globals.PLAYER_WIDTH / 4, Globals.PLAYER_HEIGHT / 4,
					Globals.PLAYER_WIDTH / 2, Globals.PLAYER_HEIGHT / 2);
		}

		AffineTransform at = new AffineTransform();
		at.translate(Globals.WINDOW_WIDTH / 2, Globals.WINDOW_HEIGHT / 2);
		at.scale(0.25, 0.25);
		at.rotate(angle);
		at.translate(-Globals.PLAYER_WIDTH / 2, -Globals.PLAYER_HEIGHT / 2);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(sprite, at, null);
	}

	public boolean contains(double x, double y) {

		double tx = this.x - x;
		double ty = this.y - y;

		if (Math.sqrt(tx * tx + ty * ty) <= 25) {
			return true;
		}
		return false;
	}

	public void update(long deltat) {
		double seconds = (deltat / 1000.00);
		double dx = vx * seconds;
		double dy = vy * seconds;
		x += dx;
		y += dy;
		if (!currentRoom.canMove(this, x, y)) {
			x -= dx;
			y -= dy;
		}
		pointAt();

		if (Globals.CONNECTED) {
			ClientNetworking.sendUpdate((float) x, (float) y, (float) vx,
					(float) vy, (float) angle);
		}

	}

	// x within current room
	public int getX() {
		return (int) x;
	}

	// y within current room
	public int getY() {
		return (int) y;
	}

	public void setPositionInRoom(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void kill() {
		ClientNetworking.sendDeath();
		// Generate gibs
		dead = true;
		if(Math.random()>0.5)
			SoundSystem.play(Globals.SFX_SPLATTER);
		else
			SoundSystem.play(Globals.SFX_SPLAT);
		
		synchronized (currentRoom) {
			Entity b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, blood, 1000);
			currentRoom.getEntities().add(b);

			for (int i = 0; i < (int) (Math.random() * 6); i++) {
				b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, blood, 20, true);
				currentRoom.getEntities().add(b);
			}
			for (int i = 0; i < (int) (Math.random() * 3); i++) {
				b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, rib, 20, true);
				currentRoom.getEntities().add(b);
			}
			for (int i = 0; i < (int) (Math.random() * 6); i++) {
				b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, bone, 20, true);
				currentRoom.getEntities().add(b);
			}
			for (int i = 0; i < (int) (Math.random() * 2); i++) {
				b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, organ1, 20, true);
				currentRoom.getEntities().add(b);
			}
			for (int i = 0; i < (int) (Math.random() * 2); i++) {
				b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, organ2, 20, true);
				currentRoom.getEntities().add(b);
			}
			for (int i = 0; i < (int) (Math.random() * 2); i++) {
				b = new TransientEntity(x, y, Math.random()*Math.PI*2, 1.00, organ3, 20, true);
				currentRoom.getEntities().add(b);
			}
		}

	}

}
