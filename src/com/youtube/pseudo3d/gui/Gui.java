package com.youtube.pseudo3d.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.youtube.pseudo3d.engine.Items;
import com.youtube.pseudo3d.engine.Player;
import com.youtube.pseudo3d.engine.Items.Holding;
import com.youtube.pseudo3d.logic.GameLogic;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;

public class Gui{

	private GameLogic gameLogic;
	
	private double slotScale = .08;
	
	public Gui(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
	
	public void update(double elapsed) {}
	
	public void render(Graphics g) {
		int slotWidth = (int)(slotScale * gameLogic.getMain().getWidth() / 1.4);
		int slotHeight = (int)(slotScale * gameLogic.getMain().getHeight() * 1.8 / 1.4);
		
		renderSlots(g, slotWidth, slotHeight);
		renderHealth(g, slotWidth, slotHeight);
		renderCoins(g, slotWidth, slotHeight);
	}
	
	private void renderSlots(Graphics g, int slotWidth, int slotHeight) {
		if(Items.unlocked.get(Items.Holding.LATTERN))
			g.drawImage(TextureHolder.get(ID.GUI_LATTERN_ICON), 
					(int) (gameLogic.getMain().getWidth() / 3) + slotWidth, (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight), slotWidth, slotHeight, null);
		
		if(Items.unlocked.get(Items.Holding.SWORD))
			g.drawImage(TextureHolder.get(ID.GUI_SWORD_ICON), 
					(int) (gameLogic.getMain().getWidth() / 3) + 2*slotWidth, (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight), slotWidth, slotHeight, null);
		
		if(Items.unlocked.get(Items.Holding.AXE))
			g.drawImage(TextureHolder.get(ID.GUI_AXE_ICON), 
					(int) (gameLogic.getMain().getWidth() / 3) + 3*slotWidth, (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight), slotWidth, slotHeight, null);
		
		if(Items.unlocked.get(Items.Holding.WAND))
			g.drawImage(TextureHolder.get(ID.GUI_WAND_ICON), 
					(int) (gameLogic.getMain().getWidth() / 3) + 4*slotWidth, (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight), slotWidth, slotHeight, null);
		
		
		int slotIterator = 0;
		for(int i=(int) (gameLogic.getMain().getWidth() / 3); i<gameLogic.getMain().getWidth() / 3 + 6 * slotWidth; i+=slotWidth) {
			if(Items.holding != Holding.values()[slotIterator])
				g.drawImage(TextureHolder.get(ID.GUI_EMPTY_SLOT), i, (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight), slotWidth, slotHeight, null);
			else
				g.drawImage(TextureHolder.get(ID.GUI_SELECTED_SLOT), i, (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight), slotWidth, slotHeight, null);

			slotIterator++;
		}
		
	}
	
	private void renderHealth(Graphics g, int slotWidth, int slotHeight) {
		if(Player.HEALTH <= 40)
			g.drawImage(TextureHolder.get(ID.GUI_BLOOD_20), 0, 0, gameLogic.getMain().getWidth(), gameLogic.getMain().getHeight(), null);
		
		if(Player.HEALTH <= 30)
			g.drawImage(TextureHolder.get(ID.GUI_BLOOD_30), 0, 0, gameLogic.getMain().getWidth(), gameLogic.getMain().getHeight(), null);
		
		g.drawImage(TextureHolder.get(ID.GUI_COLORIZER), 0, 0, gameLogic.getMain().getWidth(), gameLogic.getMain().getHeight(), null);
		
		g.setColor(new Color(111, 2, 2, 200));
		g.fillRect((int) (gameLogic.getMain().getWidth() / 2.678), (int)(gameLogic.getMain().getHeight() - slotHeight) - slotHeight, 
				(int)(Player.HEALTH * slotWidth * slotScale / 1.7), (int)(slotHeight * .6));
		g.drawImage(TextureHolder.get(ID.GUI_HEALTH_BAR), 
				(int) (gameLogic.getMain().getWidth() / 3.58), (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight) - slotHeight, 
				slotWidth * 8, slotHeight, 
				null);
	}
	
	private void renderCoins(Graphics g, int slotWidth, int slotHeight) {
		g.drawImage(TextureHolder.get(ID.GUI_COINS), 
				(int)(0.8*slotWidth), 
				(int)(gameLogic.getMain().getHeight() - 2.4*slotHeight),
				slotWidth * 2, slotHeight * 2,
				null);
		
		g.setFont(QuickText.mediumFont(gameLogic.getMain()));
		g.setColor(new Color(111, 2, 2, 200));

		g.drawString(Player.COINS + "", (int)(1.1*slotWidth), (int)(gameLogic.getMain().getHeight() - 0.8*slotHeight));
	}
}
