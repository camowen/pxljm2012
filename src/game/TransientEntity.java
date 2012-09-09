package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TransientEntity extends Entity{

	private int ttl;
	private boolean scatter = false;
    double vx = 0.00;
    double vy = 0.00;
	
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
		if(scatter){
			vx = (Math.random()<0.5?1:-1)*(50+Math.random()*50);
			vy = (Math.random()<0.5?1:-1)*(50+Math.random()*50);
		}
	}

	@Override
	public void render(Graphics g, int roomx, int roomy) {
		if(ttl > 0){
			super.render(g, roomx, roomy);
		}
	}
	
	public void update(long millis){
		double seconds = millis/1000.00;
		x += vx*seconds;
		y += vy*seconds;
		ttl--;
	}
	
	
	
	

	
}
