package game.ui;

import game.Globals;
import game.Player;
import game.rooms.ParkingLot;
import game.rooms.Room;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class GameFrame extends JFrame {

	private int p = 100;

	public GameFrame() {
		super(Globals.GAMENAME);
		setSize(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

		setUndecorated(true);
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

	public void render(Player p, Room r) {
		Graphics g = getBufferStrategy().getDrawGraphics();

		g.clearRect(0, 0, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
		r.render(g, Globals.WINDOW_WIDTH/2-p.getX(), Globals.WINDOW_HEIGHT/2-p.getY());
		p.render(g);
		g.dispose();
		getBufferStrategy().show();

	}

	public static void main(String[] args) {
		try {
			GameFrame g = new GameFrame();
			Player p = new Player("Daniel");
			Room r = new ParkingLot();
			p.setCurrentRoom(r);
			InputHandler ih = new InputHandler(p);
			g.addKeyListener(ih);
			g.addMouseListener(ih);
			g.addMouseMotionListener(ih);
			long start = System.currentTimeMillis();
			while (true) {
				long now = System.currentTimeMillis();
				p.move(now - start);
				start = now;
				g.render(p, r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
