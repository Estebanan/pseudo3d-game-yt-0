package com.youtube.pseudo3d.resource;

import java.awt.image.BufferedImage;

public class Animator {

	private BufferedImage baseFrame;
	private BufferedImage currentFrame[];

	
	public Animator(BufferedImage baseFrame, int frameWidth, int frameHeight, int frames) {
		this.baseFrame = baseFrame;
		currentFrame = new BufferedImage[frames];
				
		for(int i=0; i<frames; i++) {
			currentFrame[i] = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);

			for(int y=i*frameHeight; y<i*frameHeight + frameHeight; y++) {
				for(int x=0; x<frameWidth; x++) {
					currentFrame[i].setRGB(x, y - i*frameHeight, baseFrame.getRGB(x, y));
				}
			}
		}
	}


	public BufferedImage getBaseFrame() {
		return baseFrame;
	}


	public void setBaseFrame(BufferedImage baseFrame) {
		this.baseFrame = baseFrame;
	}


	public BufferedImage[] getCurrentFrame() {
		return currentFrame;
	}


	public void setCurrentFrame(BufferedImage[] currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	
}
