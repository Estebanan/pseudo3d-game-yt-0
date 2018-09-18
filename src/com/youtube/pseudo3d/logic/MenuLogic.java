package com.youtube.pseudo3d.logic;

import java.awt.Graphics;

import com.youtube.pseudo3d.gui.Button;
import com.youtube.pseudo3d.main.Main;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2i;

public class MenuLogic extends Logic{

	private double scale = .4;
	
	public int scaleWidth;
	public int scaleHeight;
	
	private Button continueButton;
	private Button newGameButton;
	private Button aboutButton;
	private Button exitButton;
	
	public MenuLogic(Main main) {
		super(main);	
		initButtons();
	}
	
	private void initButtons() {
				
		continueButton = new Button(
				new Vector2i(main.getWidth()/3 - scaleWidth/3,
						main.getHeight()/2 - scaleHeight/2 + 10),
				new Vector2i(scaleWidth*2/3, scaleHeight*2/3),
				"CONTINUE");
				
		newGameButton = new Button( 
				new Vector2i(main.getWidth()/3 - scaleWidth/3,
						main.getHeight() - scaleHeight + 10),
				new Vector2i(scaleWidth*2/3, scaleHeight*2/3),
				"NEW GAME");
		
		aboutButton = new Button( 
				new Vector2i(main.getWidth()*2/3 - scaleWidth/3,
						main.getHeight()/2 - scaleHeight/2 + 10),
				new Vector2i(scaleWidth*2/3, scaleHeight*2/3),
				"ABOUT");	
		
		exitButton = new Button( 
				new Vector2i(main.getWidth()*2/3 - scaleWidth/3,
						main.getHeight() - scaleHeight + 10),
				new Vector2i(scaleWidth*2/3, scaleHeight*2/3),
				"EXIT");
	}

	@Override
	public void handleInput(double elapsed) {
		if(newGameButton.pressed)
			main.setLogic(new GameLogic(main));
		
		if(aboutButton.pressed)
			main.setLogic(new AboutLogic(main));
		
		if(exitButton.pressed)
			System.exit(0);
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
		
		g.drawImage(TextureHolder.get(ID.MENU_LOGO),
				main.getWidth()/2 - scaleWidth/2, main.getHeight()/2 - scaleHeight, 
				scaleWidth, scaleHeight, null);
		
		continueButton.render(g);
		newGameButton.render(g);
		aboutButton.render(g);
		exitButton.render(g);		
	}
	
	public Main getMain() {
		return main;
	}

}
