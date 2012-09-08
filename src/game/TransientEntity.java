package game;

import java.awt.image.BufferedImage;

public class TransientEntity extends Entity{

	private int ttl;
	private boolean scatter = false;
	
	public TransientEntity(double x, double y, double rotation, double scale,
			BufferedImage sprite, int ttl) {
		super(x, y, rotation, scale, sprite,true);
		this.ttl = ttl;
	}
	
	public TransientEntity(double x, double y, double rotation, double scale,
			BufferedImage sprite, int ttl, boolean scatter) {
		super(x, y, rotation, scale, sprite,true);
		this.ttl = ttl;
		this.scatter = scatter;
	}
	
	

	
}
