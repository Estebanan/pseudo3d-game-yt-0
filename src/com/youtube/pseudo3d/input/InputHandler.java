package com.youtube.pseudo3d.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener{

	private static boolean keys[] = new boolean[0xffff];
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode >- 0 && keyCode < keys.length)
			keys[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode >- 0 && keyCode < keys.length)
			keys[keyCode] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}

}
