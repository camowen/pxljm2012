package game.ui;

import game.Globals;
import game.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener,
		MouseMotionListener {

	private Player mob;

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;

	public InputHandler(Player m) {
		mob = m;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mob.pointAt(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mob.pointAt(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mob.shoot();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !left) {
			left = true;
			if (up || down) {
				double vy = mob.getVY();
				mob.addVY(-vy);
				mob.addVY(Globals.ONE_OVER_ROOT_2 * vy);
				mob.addVX(-(Globals.ONE_OVER_ROOT_2 * Globals.MAX_VELOCITY));
			} else {
				mob.addVX(-Globals.MAX_VELOCITY);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !right) {
			right = true;
			if (up || down) {
				double vy = mob.getVY();
				mob.addVY(-vy);
				mob.addVY(Globals.ONE_OVER_ROOT_2 * vy);
				mob.addVX(Globals.ONE_OVER_ROOT_2 * Globals.MAX_VELOCITY);
			} else {
				mob.addVX(Globals.MAX_VELOCITY);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !up) {
			up = true;
			if (left || right) {
				double vx = mob.getVX();
				mob.addVX(-vx);
				mob.addVX(Globals.ONE_OVER_ROOT_2 * vx);
				mob.addVY(-(Globals.ONE_OVER_ROOT_2 * Globals.MAX_VELOCITY));
			} else {
				mob.addVY(-Globals.MAX_VELOCITY);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !down) {
			down = true;
			if (left || right) {
				double vx = mob.getVX();
				mob.addVX(-vx);
				mob.addVX(Globals.ONE_OVER_ROOT_2 * vx);
				mob.addVY((Globals.ONE_OVER_ROOT_2 * Globals.MAX_VELOCITY));
			} else {
				mob.addVY(Globals.MAX_VELOCITY);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			mob.shoot();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			//Exit code
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && left) {
			left = false;
			if(up || down){
				mob.addVY(-mob.getVY());
				if(up){
					mob.addVY(-Globals.MAX_VELOCITY);
				} else {
					mob.addVY(Globals.MAX_VELOCITY);
				}
			}
			mob.addVX(-mob.getVX());
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && right) {
			right = false;
			if(up || down){
				mob.addVY(-mob.getVY());
				if(up){
					mob.addVY(-Globals.MAX_VELOCITY);
				} else {
					mob.addVY(Globals.MAX_VELOCITY);
				}
			}
			mob.addVX(-mob.getVX());
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && up) {
			up = false;
			if(left || right){
				mob.addVX(-mob.getVX());
				if(left){
					mob.addVX(-Globals.MAX_VELOCITY);
				} else {
					mob.addVX(Globals.MAX_VELOCITY);
				}
			}
			mob.addVY(-mob.getVY());
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && down) {
			down = false;
			if(left || right){
				mob.addVX(-mob.getVX());
				if(left){
					mob.addVX(-Globals.MAX_VELOCITY);
				} else {
					mob.addVX(Globals.MAX_VELOCITY);
				}
			} 
			mob.addVY(-mob.getVY());
		}

	}

}
