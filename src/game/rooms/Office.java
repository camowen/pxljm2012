package game.rooms;

import game.Globals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Office extends Room{
	
	private BufferedImage bossDesk;
	private BufferedImage cubical1;
	private BufferedImage cubical2;
	private BufferedImage plant;
	private BufferedImage table;
	
	
	public Office() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.OFFICE_BACKGROUND));
		bench = ImageIO.read(new File(Globals.OFFICE_BOSS_DESK));
		locker = ImageIO.read(new File(Globals.OFFICE_CUBICAL_1));
		sink1 = ImageIO.read(new File(Globals.OFFICE_CUBICAL_2));
		sink2 = ImageIO.read(new File(Globals.OFFICE_PLANT));
		toilet1 = ImageIO.read(new File(Globals.OFFICE_TABLE));

		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;

	}

}
