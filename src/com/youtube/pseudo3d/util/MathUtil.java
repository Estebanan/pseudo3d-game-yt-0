package com.youtube.pseudo3d.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.youtube.pseudo3d.engine.level.Level;

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
	
	public static double pythagoreanDistance(Vector2d a, Vector2d b) {
		return Math.sqrt((b.y-a.y)*(b.y-a.y) + (b.x-a.x)*(b.x-a.x));
	}
	
	public static double pythagoreanDistance(Vector2i a, Vector2i b) {
		return Math.sqrt((b.y-a.y)*(b.y-a.y) + (b.x-a.x)*(b.x-a.x));
	}

	public static double randomWithRange(double min, double max){
	   double range = (max - min) + 1;     
	   return (double)(Math.random() * range) + min;
	}
	
	public static List<Node> findPath(Level currentLevel, Vector2i start, Vector2i goal){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		
		Node current = new Node(start, null, 0, pythagoreanDistance(start, goal));
		openList.add(current);
		while(openList.size() > 0) {
			
			Collections.sort(openList, new Comparator<Node>() {
				public int compare(Node n0, Node n1) {
					if(n1.fCost < n0.fCost)
						return 1;
					if(n1.fCost > n0.fCost)
						return -1;
					return 0;
				}
			});
			
			current = openList.get(0);
			
			if(current.position.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				
				openList.clear();
				closedList.clear();
				
				return path;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			for(int x=-1; x<=1; x++)
				for(int y=-1; y<=1; y++) {
					if(x == 0 && y == 0)
						continue;
					
					int xx = current.position.x;
					int yy = current.position.y;
					
					if(x + xx <= 0 || x + xx >= currentLevel.getMap().getWidth()
							|| y + yy <= 0 || y + yy >= currentLevel.getMap().getHeight())
						continue;
						
					int color = currentLevel.getMap().getRGB(x + xx, y + yy);
					if(color != 0xff000000)
						continue;
					
					Vector2i selectedVector = new Vector2i(x + xx, y + yy);
					double gCost = current.gCost + pythagoreanDistance(current.position, selectedVector);
					double hCost = pythagoreanDistance(selectedVector, goal);
					
					Node node = new Node(selectedVector, current, gCost, hCost);
					if(vectorInList(closedList, selectedVector) && gCost >= node.gCost)
						continue;
					if(!vectorInList(openList, selectedVector) || gCost < node.gCost)
						openList.add(node);
				}
		}
		
		closedList.clear();
		return null;
	}
	
	private static boolean vectorInList(List<Node> list, Vector2i vector) {
		for(Node n : list) {
			if(n.position.equals(vector))
				return true;
		}
		return false;
	}
}
