package com.youtube.pseudo3d.logic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.youtube.pseudo3d.gui.Button;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2i;

public class AboutLogic extends Logic{

	private double scale = .4;
	
	public int scaleWidth;
	public int scaleHeight;
	
	private Button backButton;
	
	public AboutLogic(Main main) {
		super(main);
		initButtons();
	}
	
	private void initButtons() {
		backButton = new Button(
				new Vector2i(main.getWidth()/2 - scaleWidth*3/2,
						main.getHeight() - scaleHeight / 2 + 10),
				new Vector2i(scaleWidth*2/3, scaleHeight*2/3),
				"BACK");
	}

	@Override
	public void handleInput(double elapsed) {
		if(backButton.pressed)
			main.setLogic(new MenuLogic(main));
	}
	
	@Override
	public void update(double elapsed) {
		scaleWidth = (int)(scale * main.getWidth() / 1.4);
		scaleHeight = (int)(scale * main.getHeight() * 1.8 / 1.4);
				
		initButtons();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(TextureHolder.get(ID.MENU_BACKGROUND), 0, 0, main.getWidth(), main.getHeight(), null);

		backButton.render(g);

		g.setFont(new Font("Aniron", Font.PLAIN, backButton.size.x / 8));
		g.setColor(new Color(192, 57, 43));
		g.drawString("CONTROLS", scaleWidth*7/4, scaleHeight/3);
		g.setColor(Color.WHITE);
		g.drawString("W,S,A,D - move", scaleWidth*7/4, scaleHeight/3 + 100);
		g.drawString("HOLD SHIFT - sprint", scaleWidth*7/4, scaleHeight/3 + 200);
		g.drawString("HOLD SPACE - attack", scaleWidth*7/4, scaleHeight/3 + 300);
		g.drawString("1,2,3,4,5,6 - change weapon", scaleWidth*7/4, scaleHeight/3 + 400);

		
		
		g.setFont(new Font("Aniron", Font.PLAIN, backButton.size.x / 14));
		g.setColor(new Color(142, 68, 173));
		g.drawString("2DARK4U is an open source pseudo 3D game", scaleWidth*7/4, scaleHeight/3 + 650);
		g.drawString("code documented on youtube - \"Text 2 Program\"", scaleWidth*7/4, scaleHeight/3 + 700);
		g.drawString("and the whole code on github - \"Estebanan\"", scaleWidth*7/4, scaleHeight/3 + 750);
		
		g.setFont(new Font("Aniron", Font.PLAIN, backButton.size.x / 10));
		g.drawString("made by Marcel Iwanicki", scaleWidth*7/4, scaleHeight/3 + 800);



	}
}
