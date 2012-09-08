package game.rooms;

import game.Entity;
import game.Globals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stairs extends Room {

	public Stairs() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.STAIRS_BACKGROUND));
		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;
		
	}

	public void populate() {
		super.populate();
		BufferedImage wall = new BufferedImage(198,198,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = wall.createGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 198, 198);
		Entity w = new Entity(75, 75, 0.00, 1.0, wall);
		w.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(w);
		w = new Entity(600-75-198, 75, 0.00, 1.0, wall);
		w.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(w);
		w = new Entity(75, 600-75-198, 0.00, 1.0, wall);
		w.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(w);
		w = new Entity(600-75-198, 600-75-198, 0.00, 1.0, wall);
		w.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(w);
	}



	
}
