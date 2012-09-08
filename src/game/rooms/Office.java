package game.rooms;

import game.Entity;
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
		bossDesk = ImageIO.read(new File(Globals.OFFICE_BOSS_DESK));
		cubical1 = ImageIO.read(new File(Globals.OFFICE_CUBICAL_1));
		cubical2 = ImageIO.read(new File(Globals.OFFICE_CUBICAL_2));
		plant = ImageIO.read(new File(Globals.OFFICE_PLANT));
		table = ImageIO.read(new File(Globals.OFFICE_TABLE));

		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;

	}
	
	public void populate(){
		super.populate();
		
		for(int i=0;i<4;i++){
			int style = (int)(Math.random()*4);
			switch(style){
			case 0:
				style1(i);
				break;
			case 1:
				style2(i);
				break;
			case 2:
				style3(i);
				break;
			case 3:
				style4(i);
				break;
			}
		}
	}
	
	/*   0  1
	 *   
	 *   2  3
	 */
	
	private int reflectX(int x, int quarter, int squares){
		switch(quarter){
		case 0:
		case 2:
			return x;
		case 1:
		case 3:
			return 11-x-(squares-1);
		default:
			return 0;
		}
	}
	
	private int reflectY(int y, int quarter, int squares){
		switch(quarter){
		case 0:
		case 1:
			return y;
		case 2:
		case 3:
			return 11-y-(squares-1);
		default:
			return 0;
		}
	}
	
	private void style1(int quarter){
		double horizRot = 0.00;
		double vertiRot = 0.00;
		switch(quarter){
		case 0:
			horizRot = Math.PI;
			vertiRot = Math.PI/2.0;
			break;
		case 1:
			horizRot = Math.PI;
			vertiRot = -Math.PI/2.0;
			break;
		case 2:
			horizRot = 0.00;
			vertiRot = Math.PI/2.0;
			break;
		case 3:
			horizRot = 0.00;
			vertiRot = -Math.PI/2.0;
			break;
		}
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(0,quarter,1)*50, 0.00, 1.0, plant));
		entities.add(new Entity(reflectX(4,quarter,1)*50,reflectY(2,quarter,1)*50, 0.00, 1.0, plant));
		entities.add(new Entity(reflectX(2,quarter,1)*50,reflectY(2,quarter,1)*50, 0.00, 1.0, plant));
		entities.add(new Entity(reflectX(2,quarter,1)*50,reflectY(4,quarter,1)*50, 0.00, 1.0, plant));
		entities.add(new Entity(reflectX(3,quarter,2)*50,reflectY(3,quarter,2)*50, 0.00,1.0,table));
		entities.add(new Entity(reflectX(1,quarter,2)*50,reflectY(0,quarter,1)*50, horizRot,1.0,cubical2));
		entities.add(new Entity(reflectX(3,quarter,2)*50,reflectY(0,quarter,1)*50, horizRot,1.0,cubical2));
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(1,quarter,2)*50, vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(3,quarter,2)*50, vertiRot,1.0,cubical2));
	}
	
	private void style2(int quarter){
		double horizRot = 0.00;
		double vertiRot = 0.00;
		switch(quarter){
		case 0:
			horizRot = Math.PI;
			vertiRot = Math.PI/2.0;
			break;
		case 1:
			horizRot = Math.PI;
			vertiRot = -Math.PI/2.0;
			break;
		case 2:
			horizRot = 0.00;
			vertiRot = Math.PI/2.0;
			break;
		case 3:
			horizRot = 0.00;
			vertiRot = -Math.PI/2.0;
			break;
		}
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(0,quarter,2)*50, vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(2,quarter,1)*50, vertiRot,1.0,cubical1));
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(3,quarter,2)*50, vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(2,quarter,1)*50,reflectY(0,quarter,2)*50, -vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(2,quarter,1)*50,reflectY(2,quarter,1)*50, -vertiRot,1.0,cubical1));
		entities.add(new Entity(reflectX(2,quarter,1)*50,reflectY(3,quarter,2)*50, -vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(3,quarter,1)*50,reflectY(0,quarter,2)*50, vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(3,quarter,1)*50,reflectY(2,quarter,1)*50, vertiRot,1.0,cubical1));
		entities.add(new Entity(reflectX(3,quarter,1)*50,reflectY(3,quarter,2)*50, vertiRot,1.0,cubical2));
	}
	
	private void style3(int quarter){
		double horizRot = 0.00;
		double vertiRot = 0.00;
		switch(quarter){
		case 0:
			horizRot = 0.00;
			vertiRot = Math.PI/2.0;
			break;
		case 1:
			horizRot = 0.00;
			vertiRot = -Math.PI/2.0;
			break;
		case 2:
			horizRot =Math.PI;
			vertiRot = Math.PI/2.0;
			break;
		case 3:
			horizRot = Math.PI;
			vertiRot = -Math.PI/2.0;
			break;
		}
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(1,quarter,1)*50, 0.00,1.0,plant));
		entities.add(new Entity(reflectX(1,quarter,2)*50,reflectY(1,quarter,1)*50, horizRot,1.0,bossDesk));
		entities.add(new Entity(reflectX(3,quarter,1)*50,reflectY(1,quarter,1)*50, 0.00,1.0,plant));
		entities.add(new Entity(reflectX(1,quarter,2)*50,reflectY(3,quarter,2)*50, 0.00,1.0,table));
		entities.add(new Entity(reflectX(4,quarter,1)*50,reflectY(2,quarter,2)*50, vertiRot,1.0,cubical2));
		entities.add(new Entity(reflectX(4,quarter,1)*50,reflectY(4,quarter,1)*50, vertiRot,1.0,cubical1));
	}
	
	private void style4(int quarter){
		double horizRot = 0.00;
		double vertiRot = 0.00;
		switch(quarter){
		case 0:
			horizRot = Math.PI;
			vertiRot = Math.PI/2.0;
			break;
		case 1:
			horizRot = Math.PI;
			vertiRot = -Math.PI/2.0;
			break;
		case 2:
			horizRot =0.00;
			vertiRot = Math.PI/2.0;
			break;
		case 3:
			horizRot = 0.00;
			vertiRot = -Math.PI/2.0;
			break;
		}
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(0,quarter,1)*50, 0.00,1.0,plant));
		entities.add(new Entity(reflectX(4,quarter,1)*50,reflectY(0,quarter,1)*50, 0.00,1.0,plant));
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(4,quarter,1)*50, 0.00,1.0,plant));
		entities.add(new Entity(reflectX(4,quarter,1)*50,reflectY(4,quarter,1)*50, 0.00,1.0,plant));
		entities.add(new Entity(reflectX(2,quarter,1)*50,reflectY(2,quarter,1)*50, 0.00,1.0,plant));

		entities.add(new Entity(reflectX(1,quarter,1)*50,reflectY(0,quarter,1)*50, horizRot,1.0,cubical1));
		entities.add(new Entity(reflectX(3,quarter,1)*50,reflectY(0,quarter,1)*50, horizRot,1.0,cubical1));
		entities.add(new Entity(reflectX(0,quarter,1)*50,reflectY(2,quarter,1)*50, vertiRot,1.0,cubical1));
		entities.add(new Entity(reflectX(4,quarter,1)*50,reflectY(2,quarter,1)*50, -vertiRot,1.0,cubical1));
		entities.add(new Entity(reflectX(1,quarter,1)*50,reflectY(4,quarter,1)*50, -horizRot,1.0,cubical1));
		entities.add(new Entity(reflectX(3,quarter,1)*50,reflectY(4,quarter,1)*50, -horizRot,1.0,cubical1));
	}

}
