package com.youtube.pseudo3d.util;

public class Node {

	public Vector2d position;
	public Node parent;
	public double fCost, gCost, hCost;
	
	public Node(Vector2d position, Node parent, double gCost, double hCost) {
		this.position = position;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
}
