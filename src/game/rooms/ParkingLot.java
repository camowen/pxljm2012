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
	
	private int[][] validDumpster = {{4,0,5,0},{8,0,9,0},{0,4,0,5},{11,4,11,5},
									 {0,8,0,9},{11,8,11,9},{4,11,5,11},{8,11,9,11}};

	public ParkingLot() throws IOException{
		super();
		background = ImageIO.read(new File(Globals.PARKINGLOT_BACKGROUND));
		dumpster = ImageIO.read(new File(Globals.PARKINGLOT_RUBBISHTIP));
		car = ImageIO.read(new File(Globals.PARKINGLOT_CAR));
		truck = ImageIO.read(new File(Globals.PARKINGLOT_TRUCK));
		parkingSpace = ImageIO.read(new File(Globals.PARKINGLOT_PARKINGSPACE));
		noParking = ImageIO.read(new File(Globals.PARKINGLOT_NOPARKING));
		
		populate(); //To populate, put a creature token into play that is a copy of a creature token you control.
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
				if(validDumpster[i][0]==validDumpster[i][2]){
					Entity e = new Entity(validDumpster[i][0]*25, validDumpster[i][1]*25, Math.PI/2.0, scale, dumpster);
					entities.add(e);
				} else {
					Entity e = new Entity(validDumpster[i][0]*25, validDumpster[i][1]*25, 0.0, scale, dumpster);
				}
			}
		}
		
	}

}
