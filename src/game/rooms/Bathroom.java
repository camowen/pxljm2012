package game.rooms;

import game.Entity;
import game.Globals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bathroom extends Room {

	private BufferedImage bench;
	private BufferedImage locker;
	private BufferedImage sink1;
	private BufferedImage sink2;
	private BufferedImage toilet1;
	private BufferedImage toilet2;
	private BufferedImage toilet3;
	private BufferedImage toilet4;

	public Bathroom() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.BATHROOM_BACKGROUND));
		bench = ImageIO.read(new File(Globals.BATHROOM_BENCH));
		locker = ImageIO.read(new File(Globals.BATHROOM_LOCKER));
		sink1 = ImageIO.read(new File(Globals.BATHROOM_SINK));
		sink2 = ImageIO.read(new File(Globals.BATHROOM_SINK2));
		toilet1 = ImageIO.read(new File(Globals.BATHROOM_TOILET_1));
		toilet2 = ImageIO.read(new File(Globals.BATHROOM_TOILET_2));
		toilet3 = ImageIO.read(new File(Globals.BATHROOM_TOILET_3));
		toilet4 = ImageIO.read(new File(Globals.BATHROOM_TOILET_4));

		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;

	}

	public void populate() {
		super.populate();
		bathroomStalls();
		lockersAndBenches();
	}

	private BufferedImage randomToilet() {
		double roll = Math.random();
		if (roll < 0.4) {
			return toilet1;
		} else if (roll < 0.8) {
			return toilet2;
		} else if (roll < 0.9) {
			return toilet3;
		} else {
			return toilet4;
		}
	}
	// TODO RANDOM TOILET ASSET ID

	private BufferedImage randomSink() {
		double roll = Math.random();
		if (roll < 0.5) {
			return sink1;
		} else {
			return sink2;
		}
	}
	// TODO RANDOM SINK ASSET ID

	private void bathroomStalls() {
		for (int x = 0; x < 5; x++) {
			Entity e = new Entity(x * 50, 0, 0.00, 1.00, randomToilet());
			e.type = Globals.ASSET_TYPE_BATHROOM_TOILET_1;
			entities.add(e);

			if (x != 0 && x != 4) {
				e = new Entity(x * 50, 2 * 50, Math.PI, 1.00, randomSink());
				e.type = Globals.ASSET_TYPE_BATHROOM_SINK;
				entities.add(e);

				e = new Entity(x * 50, 2.5 * 50, 0.00, 1.00, randomSink());
				e.type = Globals.ASSET_TYPE_BATHROOM_SINK2;
				entities.add(e);
			}

			e = new Entity(x * 50, 4 * 50, Math.PI, 1.00, randomToilet());
			e.type = Globals.ASSET_TYPE_BATHROOM_TOILET_2;
			entities.add(e);
		}

//		for (int x = 7; x < 12; x++) {
//			Entity e = new Entity(x * 50, 0, 0.00, 1.00, randomToilet());
//			entities.add(e);
//
//			if (x != 11 && x != 7) {
//				e = new Entity(x * 50, 2 * 50, Math.PI, 1.00, randomSink());
//				entities.add(e);
//
//				e = new Entity(x * 50, 2.5 * 50, 0.00, 1.00, randomSink());
//				entities.add(e);
//			}
//			e = new Entity(x * 50, 4 * 50, Math.PI, 1.00, randomToilet());
//			entities.add(e);
//		}

		for (int x = 7; x < 12; x++) {
			Entity e = new Entity(x * 50, 7 * 50, 0.00, 1.00, randomToilet());
			e.type = Globals.ASSET_TYPE_BATHROOM_TOILET_3;
			entities.add(e);

			if (x != 11 && x != 7) {
				e = new Entity(x * 50, 9 * 50, Math.PI, 1.00, randomSink());
				e.type = Globals.ASSET_TYPE_BATHROOM_SINK;
				entities.add(e);

				e = new Entity(x * 50, 9.5 * 50, 0.00, 1.00, randomSink());
				e.type = Globals.ASSET_TYPE_BATHROOM_SINK2;
				entities.add(e);
			}

			e = new Entity(x * 50, 11 * 50, Math.PI, 1.00, randomToilet());
			e.type = Globals.ASSET_TYPE_BATHROOM_TOILET_4;
			entities.add(e);
		}
	}

	private void lockersAndBenches() {
		for (int x = 0; x < 4; x++) {
			Entity e = new Entity(x * 50, 11.5 * 50, 0.00, 1.00, locker);
			e.type = Globals.ASSET_TYPE_BATHROOM_LOCKER;
			entities.add(e);
		}
		for (int y = 7; y < 11; y++) {
			Entity e = new Entity(0, y * 50, Math.PI / 2.0, 1.00, locker);
			e.type = Globals.ASSET_TYPE_BATHROOM_LOCKER;
			entities.add(e);
		}

		for (double x = 1.5; x < 5; x += 2) {
			for (double y = 7; y < 11; y += 1.5) {
				Entity e = new Entity(x * 50, y * 50, 0.00, 1.00, bench);
				e.type = Globals.ASSET_TYPE_BATHROOM_BENCH;
				entities.add(e);
			}
		}
		
		for (int x = 7; x < 11; x++) {
			Entity e = new Entity(x * 50, 0* 50, 0.00, 1.00, locker);
			e.type = Globals.ASSET_TYPE_BATHROOM_LOCKER;
			entities.add(e);
		}
		for (int y = 0; y < 4; y++) {
			Entity e = new Entity(11.5*50, y * 50, Math.PI / 2.0, 1.00, locker);
			e.type = Globals.ASSET_TYPE_BATHROOM_LOCKER;
			entities.add(e);
		}

		for (double x = 6.5; x < 10; x += 2) {
			for (double y = 2; y < 6; y += 1.5) {
				Entity e = new Entity(x * 50, y * 50, 0.00, 1.00, bench);
				e.type = Globals.ASSET_TYPE_BATHROOM_BENCH;
				entities.add(e);
			}
		}
	}

}
