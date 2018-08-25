package com.youtube.pseudo3d.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
	public static BufferedImage readImage(String src) {
		try {
			BufferedImage bufferedImage = ImageIO.read((ImageHandler.class.getResourceAsStream(src)));
			return bufferedImage;
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
