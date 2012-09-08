package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

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
	
	private AffineTransform affine;

	public Entity(double x, double y, double rotation, double scale,
			BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.angle = rotation;
		this.scale = scale;

		Point2D p = new Point(sprite.getWidth(), sprite.getHeight());
		Point2D op = new Point();

		AffineTransform at = new AffineTransform();
		at.scale(scale, scale);
		at.rotate(rotation);
		at.transform(p, op);

		at = new AffineTransform();
		at.scale(scale, scale);
		at.translate(sprite.getWidth() / 2.0, sprite.getHeight() / 2.0);
		at.rotate(rotation);
		at.translate(-sprite.getWidth() / 2.0, -sprite.getHeight() / 2.0);
		affine = at;
//		this.sprite = new BufferedImage((int) Math.abs(op.getX()),
//				(int) Math.abs(op.getY()), sprite.getType());
//		Graphics2D s = this.sprite.createGraphics();
//		s.drawImage(sprite, at, null);
		this.sprite=sprite;
		hitbox = new Rectangle((int) x, (int) y, (int) Math.abs(op.getX()),
				(int) Math.abs(op.getY()));
	}

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public boolean contains(double x, double y) {
		return hitbox.contains(x, y);
	}

	public void hit() {
		hit = true;
	}

	public void render(Graphics g, int roomx, int roomy) {
		if (Globals.DEBUG_MODE) {
			g.setColor(Color.RED);
			g.fillRect(roomx + hitbox.x, roomy + hitbox.y, hitbox.width,
					hitbox.height);

			if (hit) {
				g.fillOval(roomx + (int) x, roomy + (int) y, 10, 10);
			}
		}

		//g.drawImage(sprite, (int) (roomx + x), (int) (roomy + y), null);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		at.translate(x+roomx,y+roomy);
		at.translate(hitbox.getWidth() / 2.0, hitbox.getHeight() / 2.0);
		at.scale(scale, scale);
		at.rotate(angle);
		at.translate(-sprite.getWidth() / 2.0, -sprite.getHeight() / 2.0);
		g2d.drawImage(sprite, at, null);
	}

}
