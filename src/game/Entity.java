package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {
	
	public byte type;
	public double x;
	public double y;
	public double angle;
	protected double scale;

	protected boolean passable;

	protected boolean hit = false;

	protected int health = 100;

	protected BufferedImage sprite;
	protected Rectangle hitbox;
	
    protected AffineTransform affine;

	public Entity(double x, double y, double rotation, double scale, BufferedImage sprite) {
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
//		System.out.println(hitbox);
	}
	
	public Entity(double x, double y, double rotation, double scale,
			BufferedImage sprite, boolean passable){
		this(x,y,rotation,scale,sprite);
		this.passable = passable;
	}
	
	public boolean isPassable(){
		return passable;
	}

	protected Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public boolean contains(double x, double y) {
		return hitbox.contains(x, y);
	}

	public void hit() {
		hit = true;
	}

	@SuppressWarnings("unused")
	public void render(Graphics g, int roomx, int roomy) {
		if (Globals.DEBUG_MODE && !passable) {
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
	
	public Entity(double x, double y, double rotation, byte entityType) throws IOException {
		String imageName = "";
		switch(entityType) {
		case Globals.ASSET_TYPE_BATHROOM_BACKROUND:
			imageName = Globals.BATHROOM_BACKGROUND;
			passable = true;
			break;
		case Globals.ASSET_TYPE_BATHROOM_BENCH:
			imageName = Globals.BATHROOM_BENCH;
			break;
		case Globals.ASSET_TYPE_BATHROOM_LOCKER:
			imageName = Globals.BATHROOM_LOCKER;
			break;
		case Globals.ASSET_TYPE_BATHROOM_SINK:
			imageName = Globals.BATHROOM_SINK;
			break;
		case Globals.ASSET_TYPE_BATHROOM_SINK2:
			imageName = Globals.BATHROOM_SINK2;
			break;
		case Globals.ASSET_TYPE_BATHROOM_TOILET_1:
			imageName = Globals.BATHROOM_TOILET_1;
			break;
		case Globals.ASSET_TYPE_BATHROOM_TOILET_2:
			imageName = Globals.BATHROOM_TOILET_2;
			break;
		case Globals.ASSET_TYPE_BATHROOM_TOILET_3:
			imageName = Globals.BATHROOM_TOILET_3;
			break;
		case Globals.ASSET_TYPE_BATHROOM_TOILET_4:
			imageName = Globals.BATHROOM_TOILET_4;
			break;
		case Globals.ASSET_TYPE_LIBRARY_BACKGROUND:
			imageName = Globals.LIBRARY_BACKGROUND;
			passable = true;
			break;
		case Globals.ASSET_TYPE_LIBRARY_BOOKS_1:
			imageName = Globals.LIBRARY_BOOKS_1;
			passable = true;
			break;
		case Globals.ASSET_TYPE_LIBRARY_BOOKS_2:
			imageName = Globals.LIBRARY_BOOKS_2;
			passable = true;
			break;
		case Globals.ASSET_TYPE_LIBRARY_BOOKSHELF_1:
			imageName = Globals.LIBRARY_BOOKSHELF_1;
			break;
		case Globals.ASSET_TYPE_LIBRARY_BOOKSHELF_2:
			imageName = Globals.LIBRARY_BOOKSHELF_2;
			break;
		case Globals.ASSET_TYPE_LIBRARY_BOOKSHELF_MESS:
			imageName = Globals.LIBRARY_BOOKSHELF_MESS;
			break;
		case Globals.ASSET_TYPE_LIBRARY_COFFEETABLE:
			imageName = Globals.LIBRARY_COFFEETABLE;
			break;
		case Globals.ASSET_TYPE_LIBRARY_COUCH_1:
			imageName = Globals.LIBRARY_COUCH_1;
			break;
		case Globals.ASSET_TYPE_LIBRARY_COUCH_2:
			imageName = Globals.LIBRARY_COUCH_2;
			break;
		case Globals.ASSET_TYPE_LIBRARY_DESK:
			imageName = Globals.LIBRARY_DESK;
			break;
		case Globals.ASSET_TYPE_PARKINGLOT_BACKGROUND:
			imageName = Globals.PARKINGLOT_BACKGROUND;
			passable = true;
			break;
		case Globals.ASSET_TYPE_PARKINGLOT_CAR:
			imageName = Globals.PARKINGLOT_CAR;
			break;
		case Globals.ASSET_TYPE_PARKINGLOT_NOPARKING:
			imageName = Globals.PARKINGLOT_NOPARKING;
			passable = true;
			break;
		case Globals.ASSET_TYPE_PARKINGLOT_PARKINGSPACE:
			imageName = Globals.PARKINGLOT_PARKINGSPACE;
			passable = true;
			break;
		case Globals.ASSET_TYPE_PARKINGLOT_RUBBISHTIP:
			imageName = Globals.PARKINGLOT_RUBBISHTIP;
			break;
		case Globals.ASSET_TYPE_PARKINGLOT_TRUCK:
			imageName = Globals.PARKINGLOT_TRUCK;
			break;
		case Globals.ASSET_TYPE_OFFICE_BACKGROUND:
			imageName = Globals.OFFICE_BACKGROUND;
			break;
		case Globals.ASSET_TYPE_OFFICE_BOSS_DESK:
			imageName = Globals.OFFICE_BOSS_DESK;
			break;
		case Globals.ASSET_TYPE_OFFICE_CUBICAL_1:
			imageName = Globals.OFFICE_CUBICAL_1;
			break;
		case Globals.ASSET_TYPE_OFFICE_CUBICAL_2:
			imageName = Globals.OFFICE_CUBICAL_2;
			break;
		case Globals.ASSET_TYPE_OFFICE_PLANT:
			imageName = Globals.OFFICE_PLANT;
			break;
		case Globals.ASSET_TYPE_OFFICE_TABLE:
			imageName = Globals.OFFICE_TABLE;
			break;
		default:
			System.out.println("INVALID ASSET TYPE! ("+entityType+")");
		}
		
		BufferedImage sprite = ImageIO.read(new File(imageName));
		
		this.x = x;
		this.y = y;
		this.angle = rotation;
		this.scale = 1.0;

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
	
	public void update(long millis){
		
	}

}
