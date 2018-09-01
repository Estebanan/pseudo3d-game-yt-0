package com.youtube.pseudo3d.engine;

import java.util.HashMap;

public class Items {
	public static enum Holding{
		HAND,
		TORCH,
		SWORD,
		AXE,
		WAND,
		EXTRA,
	}
	
	public static HashMap<Holding, Boolean> unlocked = new HashMap<Holding, Boolean>();
	static {
		unlocked.put(Holding.HAND, true);
		unlocked.put(Holding.TORCH, true);
		unlocked.put(Holding.SWORD, true);
		unlocked.put(Holding.AXE, true);
		unlocked.put(Holding.WAND, true);
	}
	
	public static Holding holding = Holding.HAND;
	
}
