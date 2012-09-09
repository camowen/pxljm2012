package game.ui;

import game.Globals;
import game.Player;
import game.SoundSystem;
import game.networking.ClientNetworking;
import game.rooms.Room;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 8185632274067402428L;

	public GameFrame() {
		super(Globals.GAMENAME);
		setSize(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(Color.black);
		getGraphics().setColor(Color.WHITE);
		getGraphics().drawString("Now loading TIME FLUX!", 250, 250);
		createBufferStrategy(2);
		boolean done = false;
		while (!done) {
			try {
				setBackground(Color.BLACK);
				Graphics g = getBufferStrategy().getDrawGraphics();
				g.setColor(Color.white);
				g.drawString("Now loading TIME FLUX!", 250, 250);
				g.dispose();
				getBufferStrategy().show();
				done = true;
			} catch (Exception e) {
			}
		}
		
	}

	public void render(Player p) {
		boolean done = false;
		Graphics g = null;
		while(!done){
			try{
				g = getBufferStrategy().getDrawGraphics();
				done = true;
			} catch(Exception e){}
		}

		g.clearRect(0, 0, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		p.getCurrentRoom().render(g, Globals.WINDOW_WIDTH/2-p.getX(), Globals.WINDOW_HEIGHT/2-p.getY());
		//p.render(g);
		g.dispose();
		getBufferStrategy().show();

	}
	

	public static void main(String[] args) {
		try {
			SoundSystem.Init();
			//SoundSystem.playForever(SoundSystem.BG_MUSIC);

		} catch (Exception e) {
			// shhhhhhhh....
		}
		
		try {
			GameFrame g = new GameFrame();
			
			//Globals.CONNECTED = ClientNetworking.init("192.168.0.130", 8008);
			Globals.CONNECTED = false;
			Player p = new Player("Daniel");
			
			Room r = Room.getRooms().get((int) (1+Math.random()*3));
			p.setCurrentRoom(r);
			r.addPlayer(p, 300, 300);
			
			for(int room=0; room<Room.getRooms().size(); room++) {
				int westExit = (int) (Math.random()*Room.getRooms().size());
				int eastExit = (int) (Math.random()*Room.getRooms().size());
				int northExit = (int) (Math.random()*Room.getRooms().size());
				int southExit = (int) (Math.random()*Room.getRooms().size());
				
				Room.getRooms().get(room).setWest(Room.getRooms().get(westExit));
				Room.getRooms().get(room).setEast(Room.getRooms().get(eastExit));
				Room.getRooms().get(room).setNorth(Room.getRooms().get(northExit));
				Room.getRooms().get(room).setSouth(Room.getRooms().get(southExit));
			}
			
			InputHandler ih = new InputHandler(p);
			g.addKeyListener(ih);
			g.addMouseListener(ih);
			g.addMouseMotionListener(ih);
			long start = System.currentTimeMillis();
			while (true) {
				long now = System.currentTimeMillis();
				p.update(now - start);
				for(Room room : Room.getRooms()){
					room.update(now-start);
				}
				start = now;
				g.render(p);
				
				if(Globals.CONNECTED)
					ClientNetworking.poll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
