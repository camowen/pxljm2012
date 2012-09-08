package game;

public class Globals {
	
	public static final String GAMENAME = "TIME FLUX";
	public static final boolean DEBUG_MODE = true;
	
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 600;
	
	public static final int PLAYER_HEIGHT = 256;
	public static final int PLAYER_WIDTH = 256;
	public static final double MAX_VELOCITY = 150.00;
	public static final double ONE_OVER_ROOT_2 = 1.00/Math.sqrt(2);
	
	public static final int ROOM_PARKINGLOT = 0;
	public static final int ROOM_LIBRARY = 1;
	public static final int ROOM_BATHROOM = 2;
	public static final int ROOM_OFFICE = 3;
	public static final int ROOM_WAREHOUSE = 4;
	public static final int ROOM_STAIRS = 5;
	
	public static final String PARKINGLOT_BACKGROUND = "img/parkinglot/bg_carpark.png";
	public static final String PARKINGLOT_CAR = "img/parkinglot/carpark_car_100x50.png";
	public static final String PARKINGLOT_NOPARKING = "img/parkinglot/carpark_noParking_100x100.png";
	public static final String PARKINGLOT_PARKINGSPACE = "img/parkinglot/carpark_parkingSpace_100x100.png";
	public static final String PARKINGLOT_RUBBISHTIP = "img/parkinglot/carpark_rubbishTip_100x50.png";
	public static final String PARKINGLOT_TRUCK = "img/parkinglot/carpark_truck_100x50.png";
	
	public static final String LIBRARY_BACKGROUND = "img/library/bg_library.png";
	public static final String LIBRARY_BOOKS_1 = "img/library/library_bookFloor_50x50.png";
	public static final String LIBRARY_BOOKS_2 = "img/library/library_bookFloor02_50x50.png";
	public static final String LIBRARY_BOOKSHELF_1 = "img/library/library_bookshelf01_50x50.png";
	public static final String LIBRARY_BOOKSHELF_2 = "img/library/library_bookshelf02_50x50.png";
	public static final String LIBRARY_BOOKSHELF_MESS = "img/library/library_bookshelfMess_50x50.png";
	public static final String LIBRARY_COFFEETABLE = "img/library/library_coffeeTable_100x50.png";
	public static final String LIBRARY_COUCH_1 = "img/library/library_couch_100x50.png";
	public static final String LIBRARY_COUCH_2 = "img/library/library_couch2_50x50.png";
	public static final String LIBRARY_DESK = "img/library/library_desk_100x50.png";
	
	public static final String BATHROOM_BACKGROUND = "img/bathroom/bg_bathroom.png";
	public static final String BATHROOM_BENCH = "img/bathroom/bathroom_bench_100x25.png";
	public static final String BATHROOM_LOCKER = "img/bathroom/bathroom_locker_100x25.png";
	public static final String BATHROOM_SINK = "img/bathroom/bathroom_sink_50x25.png";
	public static final String BATHROOM_SINK2 = "img/bathroom/bathroom_sink2_50x25.png";
	public static final String BATHROOM_TOILET_1 = "img/bathroom/bathroom_toilet_50x50.png";
	public static final String BATHROOM_TOILET_2 = "img/bathroom/bathroom_toilet2_50x50.png";
	public static final String BATHROOM_TOILET_3 = "img/bathroom/bathroom_toilet3_50x50.png";
	public static final String BATHROOM_TOILET_4 = "img/bathroom/bathroom_toilet4_50x50.png";
	
	public static final String OFFICE_BACKGROUND = "img/office/bg_office.png";
	public static final String OFFICE_BOSS_DESK = "img/office/office_bossDesk_100x50.png";
	public static final String OFFICE_CUBICAL_1 = "img/office/office_cubical_50x50.png";
	public static final String OFFICE_CUBICAL_2 = "img/office/office_cubical_100x50.png";
	public static final String OFFICE_TABLE = "img/office/office_glassTable_100x100.png";
	public static final String OFFICE_PLANT = "img/office/office_plant_50x50.png";
	
	public static final String STAIRS_BACKGROUND = "img/stair/bg_stairs.png";
	
	public static final String FURNACE_BACKGROUND = "img/furnace/bg_furnace.png";
	public static final String FURNACE_TILE ="img/furnace/furnace_tile1_50x50.png";
	public static final String FURNACE_DOORS = "img/fx/doors.png";
	
	public static final String WAREHOUSE_BACKGROUND = "img/warehouse/bg_warehouse.png";
	public static final String WAREHOUSE_CCUBE = "img/warehouse/warehouse_companionCube_50x50.png";
	public static final String WAREHOUSE_CRATE_1 ="img/warehouse/warehouse_crate01_50x50.png";
	public static final String WAREHOUSE_CRATE_2 = "img/warehouse/warehouse_crate02_50x50.png";
	public static final String WAREHOUSE_CRATE_3= "img/warehouse/warehouse_crateMetal01_50x50.png";
	public static final String WAREHOUSE_FLOOR = "img/warehouse/warehouse_floorWarning_50x50.png";
	
	public static final String CHARACTER_ARMS_IDLE = "img/char/char_arms_blue.png";
	public static final String CHARACTER_ARMS_SHOOT = "img/char/char_armsShot_blue.png";
	public static final String CHARACTER_BODY = "img/char/char_body_blue.png";
	public static final String CHARACTER_FEET_IDLE = "img/char/char_feetStand_blue.png";
	public static final String CHARACTER_FEET_1 = "img/char/char_feet1_blue.png";
	public static final String CHARACTER_FEET_2 = "img/char/char_feet2_blue.png";
	
	public static boolean CONNECTED = false;

	// ASSET TYPE FINALS
	public static final byte ASSET_TYPE_IGNORE = 0x71;
	
	public static final byte ASSET_TYPE_PARKINGLOT_BACKGROUND = 0x01;
	public static final byte ASSET_TYPE_PARKINGLOT_CAR = 0x02;
	public static final byte ASSET_TYPE_PARKINGLOT_NOPARKING = 0x03;
	public static final byte ASSET_TYPE_PARKINGLOT_PARKINGSPACE = 0x04;
	public static final byte ASSET_TYPE_PARKINGLOT_RUBBISHTIP = 0x05;
	public static final byte ASSET_TYPE_PARKINGLOT_TRUCK = 0x06;
	
	public static final byte ASSET_TYPE_LIBRARY_BACKGROUND = 0x07;
	public static final byte ASSET_TYPE_LIBRARY_BOOKS_1 = 0x08;
	public static final byte ASSET_TYPE_LIBRARY_BOOKS_2 = 0x09;
	public static final byte ASSET_TYPE_LIBRARY_BOOKSHELF_1 = 0x0a;
	public static final byte ASSET_TYPE_LIBRARY_BOOKSHELF_2 = 0x0b;
	public static final byte ASSET_TYPE_LIBRARY_BOOKSHELF_MESS = 0x0c;
	public static final byte ASSET_TYPE_LIBRARY_COFFEETABLE = 0x0d;
	public static final byte ASSET_TYPE_LIBRARY_COUCH_1 = 0x0e;
	public static final byte ASSET_TYPE_LIBRARY_COUCH_2 = 0x0f;
	public static final byte ASSET_TYPE_LIBRARY_DESK = 0x10;
	
	public static final byte ASSET_TYPE_BATHROOM_BACKROUND = 0x11;
	public static final byte ASSET_TYPE_BATHROOM_BENCH = 0x12;
	public static final byte ASSET_TYPE_BATHROOM_LOCKER = 0x13;
	public static final byte ASSET_TYPE_BATHROOM_SINK = 0x14;
	public static final byte ASSET_TYPE_BATHROOM_SINK2 = 0x15;
	public static final byte ASSET_TYPE_BATHROOM_TOILET_1 = 0x16;
	public static final byte ASSET_TYPE_BATHROOM_TOILET_2 = 0x17;
	public static final byte ASSET_TYPE_BATHROOM_TOILET_3 = 0x18;
	public static final byte ASSET_TYPE_BATHROOM_TOILET_4 = 0x19;
	
	public static final byte ASSET_TYPE_OFFICE_BACKGROUND = 0x1a;
	public static final byte ASSET_TYPE_OFFICE_BOSS_DESK = 0x1b;
	public static final byte ASSET_TYPE_OFFICE_CUBICAL_1 = 0x1c;
	public static final byte ASSET_TYPE_OFFICE_CUBICAL_2 = 0x1d;
	public static final byte ASSET_TYPE_OFFICE_TABLE = 0x1e;
	public static final byte ASSET_TYPE_OFFICE_PLANT = 0x1f;
	
	public static final byte ASSET_TYPE_STAIRS_BACKGROUND = 0x20;
	
	public static final byte ASSET_TYPE_FURNACE_BACKGROUND = 0x21;
	public static final byte ASSET_TYPE_FURNACE_TILE = 0x22;
	
	public static final byte ASSET_TYPE_WAREHOUSE_BACKGROUND = 0x23;
	public static final byte ASSET_TYPE_WAREHOUSE_CCUBE = 0x24;
	public static final byte ASSET_TYPE_WAREHOUSE_CRATE_1 = 0x25;
	public static final byte ASSET_TYPE_WAREHOUSE_CRATE_2 = 0x26;
	public static final byte ASSET_TYPE_WAREHOUSE_CRATE_3= 0x27;
	public static final byte ASSET_TYPE_WAREHOUSE_FLOOR = 0x28;
	
	
	// TODO remaining ASSET_TYPE finals
}
