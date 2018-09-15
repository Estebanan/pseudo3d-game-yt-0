package com.youtube.pseudo3d.engine;

import java.util.HashMap;

public class Items {
	public static enum Holding{
		HAND,
		LATTERN,
		SWORD,
		AXE,
		WAND,
		EXTRA,
	}
	
	public static HashMap<Holding, Boolean> unlocked = new HashMap<Holding, Boolean>();
	static {
		unlocked.put(Holding.HAND, true);
		unlocked.put(Holding.LATTERN, false);
		unlocked.put(Holding.SWORD, false);
		unlocked.put(Holding.AXE, false);
		unlocked.put(Holding.WAND, false);
	}
	
	public static Holding holding = Holding.HAND;
	
}
