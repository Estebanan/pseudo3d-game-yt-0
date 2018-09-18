package com.youtube.pseudo3d.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.youtube.pseudo3d.input.InputHandler;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2i;

public class Button {
	
	public boolean hover = false;
	public boolean inactive = false;
	public boolean pressed = false;
			
	private Vector2i position;
	public Vector2i size;
	private String text;
	
	private BufferedImage texture;
	
	public Button(Vector2i position, Vector2i size, String text) {
		this.position = position;
		this.size = size;
		this.text = text;	
		
		handleInput();
		update();
	}
	
	public void handleInput() {
		if(InputHandler.mouseX >= position.x 
				&& InputHandler.mouseX <= position.x + size.x
				&& InputHandler.mouseY >= position.y
				&& InputHandler.mouseY <= position.y + size.y) {
			
			hover = true;
		}
	}
	
	public void update() {
		if(text.equals("CONTINUE"))
			inactive = true;
		
		if(inactive)
			texture = TextureHolder.get(ID.MENU_BUTTON_INACTIVE);
		else if(hover)
			texture = TextureHolder.get(ID.MENU_BUTTON_HOVER);
		else
			texture = TextureHolder.get(ID.MENU_BUTTON_DEFAULT);
		
		if(hover && InputHandler.mousePressed)
			pressed = true;
		else 
			pressed = false;
	}
	
	public void render(Graphics g) {
		g.drawImage(texture, position.x, position.y,
				size.x, size.y, null);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Aniron", Font.PLAIN, size.x / 12));
		g.drawString(text, position.x + size.x/8, position.y + size.y/2);
	}
}
