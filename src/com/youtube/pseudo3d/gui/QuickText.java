package com.youtube.pseudo3d.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.youtube.pseudo3d.engine.GameLogic;

public class QuickText {

	public static final int DELAY = 100;
	
	public static int timers[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	static {
		resetTimers();
	}
	
	public static void resetTimers() {
		for(int i=0; i<timers.length; i++)
			timers[i] = 0;
	}
	
	public static Font mediumFont(GameLogic gameLogic) {
		int fontSize = gameLogic.getMain().getWidth()*gameLogic.getMain().getHeight()/30000;
		return new Font("Aniron", Font.PLAIN, fontSize);
	}
	
	private static void displayMediumTextOnTopMid(GameLogic gameLogic, Graphics g, Color color, String text) {
		FontMetrics metrics = g.getFontMetrics(mediumFont(gameLogic));
		int x = (gameLogic.getMain().getWidth() - metrics.stringWidth(text)) / 2;
		int y = (10) + metrics.getAscent();
			
		g.setColor(color);
		g.setFont(mediumFont(gameLogic));
		g.drawString(text, x, y);
	}
	
	private static void displayMediumTextOnMid(GameLogic gameLogic, Graphics g, Color color, String text) {
		FontMetrics metrics = g.getFontMetrics(mediumFont(gameLogic));
		int x = (gameLogic.getMain().getWidth() - metrics.stringWidth(text)) / 2;
		int y = ((gameLogic.getMain().getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
			
		g.setColor(color);
		g.setFont(mediumFont(gameLogic));
		g.drawString(text, x, y);
	}
	
	public static void displayChapter1Text(GameLogic gameLogic, Graphics g) {
		if(timers[0] < DELAY) {	
			String text = "CHAPTER I";
			displayMediumTextOnTopMid(gameLogic, g, new Color(189, 195, 199), text);
			
			timers[0]++;
		}
	}
	
	public static void displayChapter1NameText(GameLogic gameLogic, Graphics g) {
		if(timers[1] < DELAY) {	
			String text = "SIMURGH GARDEN";
			displayMediumTextOnMid(gameLogic, g, new Color(192, 57, 43), text);
			
			timers[1]++;
		}
	}
	
	public static void displayFindLatternText(GameLogic gameLogic, Graphics g) {
		if(timers[2] < DELAY) {	
			String text = "FIND A LATTERN";
			displayMediumTextOnTopMid(gameLogic, g, new Color(22, 160, 133), text);
			
			timers[2]++;
		}
	}
	
	public static void displayFoundLatternText(GameLogic gameLogic, Graphics g) {
		if(timers[3] < DELAY) {	
			String text = "FOUND A LATTERN";
			displayMediumTextOnMid(gameLogic, g, new Color(142, 68, 173), text);
			
			timers[3]++;
		}
	}
	
	public static void displayFindSwordText(GameLogic gameLogic, Graphics g) {
		if(timers[4] < DELAY) {	
			String text = "FIND A SWORD";
			displayMediumTextOnTopMid(gameLogic, g, new Color(22, 160, 133), text);
			
			timers[4]++;
		}
	}
	
	public static void displayFoundSwordText(GameLogic gameLogic, Graphics g) {
		if(timers[5] < DELAY) {	
			String text = "FOUND A SWORD";
			displayMediumTextOnMid(gameLogic, g, new Color(142, 68, 173), text);
			
			timers[5]++;
		}
	}
	
	public static void displayChapter2Text(GameLogic gameLogic, Graphics g) {
		if(timers[6] < DELAY) {	
			String text = "CHAPTER II";
			displayMediumTextOnTopMid(gameLogic, g, new Color(189, 195, 199), text);
			
			timers[6]++;
		}
	}
	
	public static void displayChapter2NameText(GameLogic gameLogic, Graphics g) {
		if(timers[7] < DELAY) {	
			String text = "DON'T GET LOST";
			displayMediumTextOnMid(gameLogic, g, new Color(192, 57, 43), text);
			
			timers[7]++;
		}
	}
	
	public static void displayFindAxeText(GameLogic gameLogic, Graphics g) {
		if(timers[8] < DELAY) {	
			String text = "FIND AN AXE";
			displayMediumTextOnTopMid(gameLogic, g, new Color(22, 160, 133), text);
			
			timers[8]++;
		}
	}
	
	public static void displayFoundAxeText(GameLogic gameLogic, Graphics g) {
		if(timers[9] < DELAY) {	
			String text = "FOUND AN AXE";
			displayMediumTextOnMid(gameLogic, g, new Color(142, 68, 173), text);
			
			timers[9]++;
		}
	}
	
	
	public static void displayChapter3Text(GameLogic gameLogic, Graphics g) {
		if(timers[10] < DELAY) {	
			String text = "CHAPTER III";
			displayMediumTextOnTopMid(gameLogic, g, new Color(189, 195, 199), text);
			
			timers[10]++;
		}
	}
	
	public static void displayChapter3NameText(GameLogic gameLogic, Graphics g) {
		if(timers[11] < DELAY) {	
			String text = "INFINITY GAUNTLET";
			displayMediumTextOnMid(gameLogic, g, new Color(192, 57, 43), text);
			
			timers[11]++;
		}
	}
	
	public static void displayRedSecretFound(GameLogic gameLogic, Graphics g) {
		String text = "RED SECRET";
		displayMediumTextOnMid(gameLogic, g, new Color(192, 57, 43), text);			
	}
	
	public static void displayRedDoorsOpen(GameLogic gameLogic, Graphics g) {
		String text = "RED SECRET LEVER";
		displayMediumTextOnTopMid(gameLogic, g, new Color(192, 57, 43), text);	
	}
	
	public static void displayBlueSecretFound(GameLogic gameLogic, Graphics g) {
		String text = "BLUE SECRET";
		displayMediumTextOnMid(gameLogic, g, new Color(41, 128, 185), text);			
	}
	
	public static void displayBlueDoorsOpen(GameLogic gameLogic, Graphics g) {
		String text = "BLUE SECRET LEVER";
		displayMediumTextOnTopMid(gameLogic, g, new Color(41, 128, 185), text);	
	}
	
	public static void displayThisWay(GameLogic gameLogic, Graphics g) {
		String text = "THIS WAY";
		displayMediumTextOnTopMid(gameLogic, g, new Color(44, 62, 80), text);	
	}
	
}
