package game.rooms;

import game.Entity;
import game.Globals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Warehouse extends Room {

	private BufferedImage ccube;
	private BufferedImage crate1;
	private BufferedImage crate2;
	private BufferedImage crate3;
	private BufferedImage tile;
	
	private boolean[][] contents;

	public Warehouse() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.WAREHOUSE_BACKGROUND));
		ccube = ImageIO.read(new File(Globals.WAREHOUSE_CCUBE));
		crate1 = ImageIO.read(new File(Globals.WAREHOUSE_CRATE_1));
		crate2 = ImageIO.read(new File(Globals.WAREHOUSE_CRATE_2));
		crate3 = ImageIO.read(new File(Globals.WAREHOUSE_CRATE_3));
		tile = ImageIO.read(new File(Globals.WAREHOUSE_FLOOR));
		
		contents = new boolean[12][12];
		
		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;
	}

	public void populate() {
		super.populate();
		clearContents();
		corners();
		outerCrates();
		inside();
	}

	private void inside() {
		
		contents[1][5] = true;
		contents[1][6] = true;
		contents[10][5] = true;
		contents[10][6] = true;
		contents[5][1] = true;
		contents[6][1] = true;
		contents[5][10] = true;
		contents[6][10] = true;
		Entity w = new Entity(1*50,5*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(1*50,6*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(10*50,6*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(10*50,5*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(5*50,1*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(6*50,1*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(5*50,10*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		w = new Entity(6*50,10*50,0.00,1,tile,true);
		w.type = Globals.ASSET_TYPE_WAREHOUSE_FLOOR;
		entities.add(w);
		
		double fillPercentage = 0.00;
		int num = 0;
		for(int i=0;i<12;i++){
			for(int j=0;j<12;j++){
				if(contents[i][j]){
					num++;
				}
			}
		}
		fillPercentage = num/144.00;
		
		while(fillPercentage < 0.75){
			int x = (int)(Math.random()*12);
			int y = (int)(Math.random()*12);
			
			double type = Math.random();
			if(!contents[x][y]){
				Entity e = null;
				if(type < 0.3){
					//Crate1
					e = new Entity(x*50,y*50,0.00,1,crate1);
					e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_1;
					contents[x][y] = true;
					num++;
				} else if (type < 0.6){
					//Crate2
					e = new Entity(x*50,y*50,0.00,1,crate2);
					e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_2;
					contents[x][y] = true;
					num++;
				} else if (type < 0.9){
					//Crate3
					e = new Entity(x*50,y*50,0.00,1,crate3);
					e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_3;
					contents[x][y] = true;
					num++;
				} else {
					//CCube
					e = new Entity(x*50,y*50,0.00,1,ccube);
					e.type = Globals.ASSET_TYPE_WAREHOUSE_CCUBE;
					contents[x][y] = true;
					num++;
				}
				if(e!=null){
					entities.add(e);
				}
			}
			fillPercentage = num/144.00;
		}
		
	}

	private void corners() {
		contents[0][0]=true;
		contents[0][11]=true;
		contents[11][0]=true;
		contents[11][11]=true;
		BufferedImage wall = new BufferedImage(50,50,BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = wall.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, 50, 50);
		Entity e = new Entity(0, 0,0,1.0,wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(0, 11*50,0,1.0,wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(11*50, 0,0,1.0,wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		e = new Entity(11*50, 11*50,0,1.0,wall);
		e.type = Globals.ASSET_TYPE_IGNORE;
		entities.add(e);
		
	}

	private void outerCrates() {
		for(int i=1;i<11;i++){
			contents[i][0] = true;
			contents[i][11] = true;
			contents[i][5] = true;
			//contents[i][6] = true;
			if(i < 5 || i > 6){
				
				Entity e = new Entity(i*50,0,0.00,1,Math.random()<0.5?crate1:crate2);
				e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_1;
				entities.add(e);
				e = new Entity(i*50,11*50,0.00,1,Math.random()<0.5?crate1:crate2);
				e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_2;
				entities.add(e);
			} 
		}
		for(int i=1;i<11;i++){
			contents[0][i] = true;
			contents[11][i] = true;
			//contents[5][i] = true;
			contents[6][i] = true;
			if(i < 5 || i > 6){
				Entity e = new Entity(0,i*50,0.0,1,Math.random()<0.5?crate1:crate2);
				e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_1;
				entities.add(e);
				e = new Entity(11*50,i*50,0.0,1,Math.random()<0.5?crate1:crate2);
				e.type = Globals.ASSET_TYPE_WAREHOUSE_CRATE_2;
				entities.add(e);
			}
		}
		
	}
	
	// TODO random bookshelf entity ID

	private void clearContents() {
		for(int i=0;i<12;i++){
			for(int j=0;j<12;j++){
				contents[i][j]=false;
			}
		}
		
	}

	
}
