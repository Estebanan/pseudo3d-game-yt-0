package com.youtube.pseudo3d.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioHandler {
	public static Thread playAudio(String src) {
		return new Thread() {
			public void run() {
				try {
					File file = new File(src);
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					
					Player player;
					try {
						player = new Player(bis);
						player.play();
															
						if(player.isComplete())
							player.close();
		
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
				
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		};
	}	
}