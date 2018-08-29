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
	

	public static void combSort(int order[], double dist[], int amount) {
		int gap = amount;
		boolean swapped = false;
		while (gap > 1 || swapped) {
			gap = (gap * 10) / 13;
			if (gap == 9 || gap == 10)
				gap = 11;
			if (gap < 1)
				gap = 1;
			swapped = false;
			for (int i = 0; i < amount - gap; i++) {
				int j = i + gap;
				if (dist[i] < dist[j]) {
					swap(dist, i, j);
					swap(order, i, j);
					swapped = true;
				}
			}
		}
	}

	public static void swap(int array[], final int i, final int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void swap(double array[], final int i, final int j) {
		double temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
}
