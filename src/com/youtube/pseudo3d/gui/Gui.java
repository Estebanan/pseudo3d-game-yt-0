package com.youtube.pseudo3d.gui;

import java.awt.Color;
import java.awt.Graphics;

import com.youtube.pseudo3d.engine.GameLogic;
import com.youtube.pseudo3d.engine.Items;
import com.youtube.pseudo3d.engine.Player;
import com.youtube.pseudo3d.engine.Items.Holding;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;

public class Gui {

	private GameLogic gameLogic;
	
	
	private double slotScale = .08;
	
	public Gui(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
	
	public void update(double elapsed) {
		
	}
	
	public void render(Graphics g) {
		int slotWidth = (int)(slotScale * gameLogic.getMain().getWidth() / 1.4);
		int slotHeight = (int)(slotScale * gameLogic.getMain().getHeight() * 1.8 / 1.4);
		
		renderSlots(g, slotWidth, slotHeight);
		renderHealth(g, slotWidth, slotHeight);
	}
	
	private void renderSlots(Graphics g, int slotWidth, int slotHeight) {
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
		g.setColor(Color.RED);
		g.fillRect((int) (gameLogic.getMain().getWidth() / 2.678), (int)(gameLogic.getMain().getHeight() - slotHeight) - slotHeight, (int)(Player.HEALTH * slotWidth * slotScale / 1.7), (int)(slotHeight * .6));
		g.drawImage(TextureHolder.get(ID.GUI_HEALTH_BAR), (int) (gameLogic.getMain().getWidth() / 3.58), (int)(gameLogic.getMain().getHeight() - 1.2*slotHeight) - slotHeight, slotWidth * 8, slotHeight, null);
	}
}
