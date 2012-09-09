package game.rooms;

import game.Entity;
import game.Globals;
import game.Player;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class Furnace extends Room {

	BufferedImage tile;
	BufferedImage doors;

	public Furnace() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.FURNACE_BACKGROUND));
		tile = ImageIO.read(new File(Globals.FURNACE_TILE));
		doors = ImageIO.read(new File(Globals.FURNACE_DOORS));
		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;

	}

	public void populate() {
		super.populate();
		for (int x = 0; x < 12; x++) {
			Entity e = new Entity(x * 50, 0, 0.00, 1.00, tile, true);
			e.type = Globals.ASSET_TYPE_FURNACE_TILE;
			entities.add(e);
			e = new Entity(x * 50, 11 * 50, 0.00, 1.00, tile, true);
			e.type = Globals.ASSET_TYPE_FURNACE_TILE;
			entities.add(e);
		}
		for (int y = 1; y < 11; y++) {
			Entity e = new Entity(0, y * 50, 0.00, 1.00, tile, true);
			e.type = Globals.ASSET_TYPE_FURNACE_TILE;
			entities.add(e);
			e = new Entity(11 * 50, y * 50, 0.00, 1.00, tile, true);
			e.type = Globals.ASSET_TYPE_FURNACE_TILE;
			entities.add(e);
		}
		int numHorizontal = (int) (Math.random() * 5);
		int numVertical = (int) (Math.random() * 5);

		Set<Integer> doneX = new HashSet<Integer>();
		Set<Integer> doneY = new HashSet<Integer>();

		for (int i = 0; i < numHorizontal; i++) {
			int y = (int) (Math.random() * 8 + 2);
			while (doneY.contains(y)) {
				y = (int) (Math.random() * 8 + 2);
			}
			doneY.add(y);
			for (int x = 1; x < 11; x++) {
				Entity e = new Entity(x * 50, y*50, 0.00, 1.00, tile, true);
				e.type = Globals.ASSET_TYPE_FURNACE_TILE;
				entities.add(e);
			}
		}
		
		for (int i = 0; i < numVertical; i++) {
			int x = (int) (Math.random() * 8 + 2);
			while (doneX.contains(x)) {
				x = (int) (Math.random() * 8 + 2);
			}
			doneX.add(x);
			for (int y = 1; y < 11; y++) {
				Entity e = new Entity(x * 50, y*50, 0.00, 1.00, tile, true);
				e.type = Globals.ASSET_TYPE_FURNACE_TILE;
				entities.add(e);
			}
		}
		
		Entity e = new Entity(0, 0, 0.00, 1.00, doors, true);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);

	}

	@Override
	public boolean canMove(Player p, double playerX, double playerY) {
		boolean falls = true;
		for(Entity e: entities){
			if(e.type == Globals.ASSET_TYPE_FURNACE_TILE && e.contains(playerX, playerY)){
				falls = false;
				break;
			}
		}
		boolean canMove = super.canMove(p, playerX, playerY);
		if(canMove && falls &&(0<playerX && playerX < 600 && 0 < playerY && playerY<600)){
			//TODO: Kill the player, dramatically\
			//System.out.println("Fall! (" + playerX + "," + playerY+")");
			p.kill();
			return true;
		} else {
			return canMove;
		}
	}
	
	

}
