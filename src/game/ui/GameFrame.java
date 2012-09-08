package game.ui;

import game.Globals;
import game.Player;
import game.networking.ClientNetworking;
import game.rooms.ParkingLot;
import game.rooms.Room;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import game.rooms.*;


public class GameFrame extends JFrame {

	private static final long serialVersionUID = 8185632274067402428L;

	public GameFrame() {
		super(Globals.GAMENAME);
		setSize(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		boolean done = false;
		while (!done) {
			try {
				createBufferStrategy(2);
				done = true;
			} catch (Exception e) {
			}
		}
		setBackground(Color.BLACK);
		getBufferStrategy().show();
	}

	public void render(Player p) {
		Graphics g = getBufferStrategy().getDrawGraphics();

		g.clearRect(0, 0, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		p.getCurrentRoom().render(g, Globals.WINDOW_WIDTH/2-p.getX(), Globals.WINDOW_HEIGHT/2-p.getY());
		//p.render(g);
		g.dispose();
		getBufferStrategy().show();

	}

	public static void main(String[] args) {
		try {
			GameFrame g = new GameFrame();
			
			Globals.CONNECTED = ClientNetworking.init("192.168.0.130", 8008);
			
			Player p = new Player("Daniel");
			Room r = new Bathroom();
			Room l = new Library();
			Room q = new ParkingLot();
			
			r.setNorth(q);
			r.setSouth(q);
			r.setEast(l);
			r.setWest(l);
			q.setEast(r);
			q.setWest(r);
			q.setNorth(l);
			q.setSouth(l);
			
			p.setCurrentRoom(r);
			r.addPlayer(p, 300, 300);
			InputHandler ih = new InputHandler(p);
			g.addKeyListener(ih);
			g.addMouseListener(ih);
			g.addMouseMotionListener(ih);
			long start = System.currentTimeMillis();
			while (true) {
				long now = System.currentTimeMillis();
				p.move(now - start);
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
