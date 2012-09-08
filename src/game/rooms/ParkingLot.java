package game.rooms;

import game.Entity;
import game.Globals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ParkingLot extends Room {
	
	private BufferedImage dumpster;
	private BufferedImage car;
	private BufferedImage truck;
	private BufferedImage parkingSpace;
	private BufferedImage noParking;
	
	private double dumpsterChance = 1.0;
	private double carChance = 0.15;
	private double truckChance = 0.1;
	private double parkingSpaceChance = 0.3;
	private double noParkingChance = 0.3;
	
	private int[][] validDumpster = {{2,0,3,0},{8,0,9,0},{0,2,0,3},{11,2,11,3},
									 {0,8,0,9},{11,8,11,9},{2,11,3,11},{8,11,9,11}};

	public ParkingLot() throws IOException{
		super();
		background = ImageIO.read(new File(Globals.PARKINGLOT_BACKGROUND));
		dumpster = ImageIO.read(new File(Globals.PARKINGLOT_RUBBISHTIP));
		car = ImageIO.read(new File(Globals.PARKINGLOT_CAR));
		truck = ImageIO.read(new File(Globals.PARKINGLOT_TRUCK));
		parkingSpace = ImageIO.read(new File(Globals.PARKINGLOT_PARKINGSPACE));
		noParking = ImageIO.read(new File(Globals.PARKINGLOT_NOPARKING));
		
		populate(); //To populate, put a creature token into play that is a copy of a creature token you control.
		
		north = this;
		south = this;
		west = this;
		east = this;
	}
	
	private void populate(){
			dumpsters();
	}
	
	private void dumpsters(){
		for(int i=0;i<validDumpster.length;i++){
			if(Math.random()<=dumpsterChance){
				//Rotation?[i]
				double rot = 0.00;
				double scale = 0.5;
				BufferedImage d = new BufferedImage(dumpster.getWidth(), dumpster.getHeight(), dumpster.getType());
				d.setData(dumpster.getData());
				if(validDumpster[i][0]==validDumpster[i][2]){
					Entity e = new Entity(validDumpster[i][0]*50, validDumpster[i][1]*50, Math.PI/2.0, scale, d);
					entities.add(e);
				} else {
					Entity e = new Entity(validDumpster[i][0]*50, validDumpster[i][1]*50, 0.0, scale, d);
					entities.add(e);
				}
			}
		}
		
	}

}
