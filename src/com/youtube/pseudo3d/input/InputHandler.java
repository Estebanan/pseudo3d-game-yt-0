package com.youtube.pseudo3d.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	private static boolean keys[] = new boolean[0xffff];

	public static int mouseX;
	public static int mouseY;

	public static boolean mousePressed;

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > -0 && keyCode < keys.length)
			keys[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > -0 && keyCode < keys.length)
			keys[keyCode] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

}
