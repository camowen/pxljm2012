package game;

public class Globals {
	
	public static final String GAMENAME = "TIME FLUX";
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600;
	
	public static final int PLAYER_HEIGHT = 256;
	public static final int PLAYER_WIDTH = 256;
	public static final double MAX_VELOCITY = 150.00;
	public static final double ONE_OVER_ROOT_2 = 1.00/Math.sqrt(2);
	
	public static final String PARKINGLOT_BACKGROUND = "img/parkinglot/bg_carpark.png";
	public static final String PARKINGLOT_CAR = "img/parkinglot/carpark_car_200x100.png"; 
	public static final String PARKINGLOT_NOPARKING = "img/parkinglot/carpark_noParking_200x200.png";
	public static final String PARKINGLOT_PARKINGSPACE = "img/parkinglot/carpark_parkingSpace_200x200.png";
	public static final String PARKINGLOT_RUBBISHTIP = "img/parkinglot/carpark_rubbishTip_200x100.png";
	public static final String PARKINGLOT_TRUCK = "img/parkinglot/carpark_truck_200x100.png";
	
	public static final String CHARACTER_ARMS_IDLE = "img/char/char_arms_blue.png";
	public static final String CHARACTER_ARMS_SHOOT = "img/char/char_armsShot_blue.png";
	public static final String CHARACTER_BODY = "img/char/char_body_blue.png";
	public static final String CHARACTER_FEET_IDLE = "img/char/char_feetStand_blue.png";
	public static final String CHARACTER_FEET_1 = "img/char/char_feet1_blue.png";
	public static final String CHARACTER_FEET_2 = "img/char/char_feet2_blue.png";
	
	public static final boolean DEBUG_MODE = false;
	
	public static boolean CONNECTED = false;

	// ASSET TYPE FINALS
	public static final byte ASSET_TYPE_PARKINGLOT_BACKGROUND = 0x01;
	public static final byte ASSET_TYPE_PARKINGLOT_CAR = 0x02;
	public static final byte ASSET_TYPE_PARKINGLOT_NOPARKING = 0x03;
	public static final byte ASSET_TYPE_PARKINGLOT_PARKINGSPACE = 0x04;
	public static final byte ASSET_TYPE_PARKINGLOT_RUBBISHTIP = 0x05;
	public static final byte ASSET_TYPE_PARKINGLOT_TRUCK = 0x06;
	
}
