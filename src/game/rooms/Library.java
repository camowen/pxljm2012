package game.rooms;

import game.Entity;
import game.Globals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Library extends Room {

	private BufferedImage books1;
	private BufferedImage books2;
	private BufferedImage shelf1;
	private BufferedImage shelf2;
	private BufferedImage shelf3;
	private BufferedImage coffeetable;
	private BufferedImage couch1;
	private BufferedImage couch2;
	private BufferedImage desk;
	
	private boolean[][] contents;

	public Library() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.LIBRARY_BACKGROUND));
		books1 = ImageIO.read(new File(Globals.LIBRARY_BOOKS_1));
		books2 = ImageIO.read(new File(Globals.LIBRARY_BOOKS_2));
		shelf1 = ImageIO.read(new File(Globals.LIBRARY_BOOKSHELF_1));
		shelf2 = ImageIO.read(new File(Globals.LIBRARY_BOOKSHELF_2));
		shelf3 = ImageIO.read(new File(Globals.LIBRARY_BOOKSHELF_MESS));
		coffeetable = ImageIO.read(new File(Globals.LIBRARY_COFFEETABLE));
		couch1 = ImageIO.read(new File(Globals.LIBRARY_COUCH_1));
		couch2 = ImageIO.read(new File(Globals.LIBRARY_COUCH_2));
		desk = ImageIO.read(new File(Globals.LIBRARY_DESK));
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
		outerShelves();
		inside();
	}

	private void inside() {
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
			
			if(!contents[x][y]){
				double type = Math.random();
				double rotation = Math.random();
				if(rotation < 0.25)
					rotation = 0.00;
				else if(rotation < 0.5)
					rotation = Math.PI/2.0;
				else if(rotation < 0.75)
					rotation = Math.PI;
				else
					rotation = -Math.PI/2.0;
				
				Entity e = null;
				if(type < 0.1){
					//Book floor 1
					e = new Entity(x*50,y*50,rotation,1,books1,true);
					contents[x][y] = true;
					num++;
				} else if (type < 0.2){
					//Book floor 2
					e = new Entity(x*50,y*50,rotation,1,books2,true);
					contents[x][y] = true;
					num++;
				} else if (type < 0.3){
					//Book shelf 1
					e = new Entity(x*50,y*50,rotation,1,shelf1);
					contents[x][y] = true;
				} else if (type < 0.4){
					//Book shelf 2
					e = new Entity(x*50,y*50,rotation,1,shelf2);
					contents[x][y] = true;
					num++;
				} else if (type < 0.5){
					//Book shelf 3
					e = new Entity(x*50,y*50,rotation,1,shelf3);
					contents[x][y] = true;
					num++;
				} else if (type < 0.6){
					//Coffee table
					if(!contents[x+1][y]){
						e = new Entity(x*50,y*50,0.00,1,coffeetable);
						contents[x][y] = true;
						contents[x+1][y] = true;
						num+=2;
					} else if(!contents[x][y+1]){
						e = new Entity(x*50,y*50,Math.random()<0.5?Math.PI/2.0:-Math.PI/2.0,1,coffeetable);
						contents[x][y] = true;
						contents[x][y+1] = true;
						num+=2;
					}
				} else if (type < 0.7){
					//couch 1
					if(!contents[x+1][y]){
						e = new Entity(x*50,y*50,0.00,1,couch1);
						contents[x][y] = true;
						contents[x+1][y] = true;
						num+=2;
					} else if(!contents[x][y+1]){
						e = new Entity(x*50,y*50,Math.random()<0.5?Math.PI/2.0:-Math.PI/2.0,1,couch1);
						contents[x][y] = true;
						contents[x][y+1] = true;
						num+=2;
					}
				} else if (type < 0.8){
					//couch 2
					e = new Entity(x*50,y*50,rotation,1,couch2);
					contents[x][y] = true;
					num++;
				} else if (type < 0.9){
					//desk
					if(!contents[x+1][y]){
						e = new Entity(x*50,y*50,Math.random()<0.5?0.00:Math.PI,1,desk);
						contents[x][y] = true;
						contents[x+1][y] = true;
						num+=2;
					} else if(!contents[x][y+1]){
						e = new Entity(x*50,y*50,Math.random()<0.5?Math.PI/2.0:-Math.PI/2.0,1,desk);
						contents[x][y] = true;
						contents[x][y+1] = true;
						num+=2;
					}
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
		entities.add(e);
		e = new Entity(0, 11*50,0,1.0,wall);
		entities.add(e);
		e = new Entity(11*50, 0,0,1.0,wall);
		entities.add(e);
		e = new Entity(11*50, 11*50,0,1.0,wall);
		entities.add(e);
		
	}

	private void outerShelves() {
		for(int i=1;i<11;i++){
			contents[i][0] = true;
			contents[i][11] = true;
			contents[i][5] = true;
			contents[i][6] = true;
			if(i < 5 || i > 6){
				Entity e = new Entity(i*50,0,0.00,1,Math.random()<0.5?shelf1:shelf2);
				entities.add(e);
				e = new Entity(i*50,11*50,0.00,1,Math.random()<0.5?shelf1:shelf2);
				entities.add(e);
			}
		}
		for(int i=1;i<11;i++){
			contents[0][i] = true;
			contents[11][i] = true;
			contents[5][i] = true;
			contents[6][i] = true;
			if(i < 5 || i > 6){
				Entity e = new Entity(0,i*50,Math.PI/2.0,1,Math.random()<0.5?shelf1:shelf2);
				entities.add(e);
				e = new Entity(11*50,i*50,Math.PI/2.0,1,Math.random()<0.5?shelf1:shelf2);
				entities.add(e);
			}
		}
		
	}

	private void clearContents() {
		for(int i=0;i<12;i++){
			for(int j=0;j<12;j++){
				contents[i][j]=false;
			}
		}
		
	}

	
}
