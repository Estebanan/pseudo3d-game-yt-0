package com.youtube.pseudo3d.logic;

import java.awt.Graphics;

import com.youtube.pseudo3d.main.Main;

public abstract class Logic {

	protected Main main;
	
	public Logic(Main main) {
		this.main = main;
	}
	
	public abstract void handleInput(double elapsed);
	public abstract void update(double elapsed);
	public abstract void render(Graphics g);
}
