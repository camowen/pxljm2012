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

	private double dumpsterChance = 0.5;
	private double carChance = 0.2;
	private double noParkingChance = 0.5;

	private int[][] validDumpster = { { 1, 0, 2, 0 }, { 9, 0, 10, 0 },
			{ 0, 1, 0, 2 }, { 11, 1, 11, 2 }, { 0, 9, 0, 10 },
			{ 11, 9, 11, 10 }, { 1, 11, 2, 11 }, { 9, 11, 10, 11 } };

	public ParkingLot() throws IOException {
		super();
		background = ImageIO.read(new File(Globals.PARKINGLOT_BACKGROUND));
		dumpster = ImageIO.read(new File(Globals.PARKINGLOT_RUBBISHTIP));
		car = ImageIO.read(new File(Globals.PARKINGLOT_CAR));
		truck = ImageIO.read(new File(Globals.PARKINGLOT_TRUCK));
		parkingSpace = ImageIO.read(new File(Globals.PARKINGLOT_PARKINGSPACE));
		noParking = ImageIO.read(new File(Globals.PARKINGLOT_NOPARKING));

		populate(); // To populate, put a creature token into play that is a
					// copy of a creature token you control.

		north = this;
		south = this;
		west = this;
		east = this;
	}

	public void populate() {
		super.populate();
		dumpsters();
		noParking();
		parkingSpaces();
		carsAndTrucks();
	}

	private void dumpsters() {
		for (int i = 0; i < validDumpster.length; i++) {
			if (Math.random() <= dumpsterChance) {
				// Rotation?[i]
				double scale = 0.5;
				BufferedImage d = new BufferedImage(dumpster.getWidth(),
						dumpster.getHeight(), dumpster.getType());
				d.setData(dumpster.getData());
				if (validDumpster[i][0] == validDumpster[i][2]) {
					Entity e = new Entity(validDumpster[i][0] * 50,
							validDumpster[i][1] * 50, Math.PI / 2.0, scale, d);
					entities.add(e);
				} else {
					Entity e = new Entity(validDumpster[i][0] * 50,
							validDumpster[i][1] * 50, 0.0, scale, d);
					entities.add(e);
				}
				if (Math.random() <= noParkingChance) {
					double npy = 0, npx = 0;
					if (validDumpster[i][1] < 6) {
						npy = 1;
					} else {
						npy = 9;
					}
					if (validDumpster[i][0] < 6) {
						npx = 1;
					} else {
						npx = 9;
					}
					Entity e = new Entity(npx * 50, npy * 50, 0.00, scale,
							noParking, true);
					entities.add(e);
				}
			}
		}

	}

	private void noParking() {
		Entity e;
		if (Math.random() <= noParkingChance) {
			e = new Entity(1 * 50, 5 * 50, 0.00, 0.5, noParking, true);
			entities.add(e);
		}
		if (Math.random() <= noParkingChance) {
			e = new Entity(5 * 50, 1 * 50, 0.00, 0.5, noParking, true);
			entities.add(e);
		}
		if (Math.random() <= noParkingChance) {
			e = new Entity(9 * 50, 5 * 50, 0.00, 0.5, noParking, true);
			entities.add(e);
		}
		if (Math.random() <= noParkingChance) {
			e = new Entity(5 * 50, 9 * 50, 0.00, 0.5, noParking, true);
			entities.add(e);
		}
	}

	private void parkingSpaces() {
		Entity e = new Entity(1 * 50, 3 * 50, 0.00, 0.5, parkingSpace, true);
		entities.add(e);
		e = new Entity(1 * 50, 7 * 50, 0.00, 0.5, parkingSpace, true);
		entities.add(e);

		e = new Entity(9 * 50, 3 * 50, Math.PI, 0.5, parkingSpace, true);
		entities.add(e);
		e = new Entity(9 * 50, 7 * 50, Math.PI, 0.5, parkingSpace, true);
		entities.add(e);
		
		e = new Entity(6 * 50, 3 * 50, 0, 0.5, parkingSpace, true);
		entities.add(e);
		e = new Entity(6 * 50, 5 * 50, 0, 0.5, parkingSpace, true);
		entities.add(e);
		e = new Entity(6 * 50, 7 * 50, 0, 0.5, parkingSpace, true);
		entities.add(e);
		
		e = new Entity(4 * 50, 3 * 50, Math.PI, 0.5, parkingSpace, true);
		entities.add(e);
		e = new Entity(4 * 50, 5 * 50, Math.PI, 0.5, parkingSpace, true);
		entities.add(e);
		e = new Entity(4 * 50, 7 * 50, Math.PI, 0.5, parkingSpace, true);
		entities.add(e);
		
	}
	
	private void carsAndTrucks(){
		Entity e;
		for(int i=3;i<=4;i++){
			if(Math.random()<carChance){
				if(Math.random()<0.5){
					e = new Entity(1*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,car);
					entities.add(e);
				} else {
					e = new Entity(1*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,truck);
					entities.add(e);
				}
			}
		}
		for(int i=7;i<=8;i++){
			if(Math.random()<carChance){
				if(Math.random()<0.5){
					e = new Entity(1*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,car);
					entities.add(e);
				} else {
					e = new Entity(1*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,truck);
					entities.add(e);
				}
			}
		}
		for(int i=3;i<=4;i++){
			if(Math.random()<carChance){
				if(Math.random()<0.5){
					e = new Entity(9*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,car);
					entities.add(e);
				} else {
					e = new Entity(9*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,truck);
					entities.add(e);
				}
			}
		}
		for(int i=7;i<=8;i++){
			if(Math.random()<carChance){
				if(Math.random()<0.5){
					e = new Entity(9*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,car);
					entities.add(e);
				} else {
					e = new Entity(9*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,truck);
					entities.add(e);
				}
			}
		}
		
		for(int i=3;i<=8;i++){
			if(Math.random()<carChance){
				if(Math.random()<0.5){
					e = new Entity(4*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,car);
					entities.add(e);
				} else {
					e = new Entity(4*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,truck);
					entities.add(e);
				}
			}
		}
		for(int i=3;i<=8;i++){
			if(Math.random()<carChance){
				if(Math.random()<0.5){
					e = new Entity(6*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,car);
					entities.add(e);
				} else {
					e = new Entity(6*50,i*50,Math.random()<0.5?0.00:Math.PI,0.5,truck);
					entities.add(e);
				}
			}
		}
		
	}
}
