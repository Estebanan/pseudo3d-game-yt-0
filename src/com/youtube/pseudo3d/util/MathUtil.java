package com.youtube.pseudo3d.util;

import java.awt.Color;

public class MathUtil {

	public static int shadeColor(int color, double value) {
		Color c = new Color(color);
		float hsb[] = new float[3];
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
		hsb[2] = hsb[2] / (float)value;
		color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
		return color;
	}
}
