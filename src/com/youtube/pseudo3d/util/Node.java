package com.youtube.pseudo3d.util;

public class Node{

	public Vector2i position;
	public Node parent;
	public double fCost, gCost, hCost;

    public Node(Vector2i position, Node parent, double gCost, double hCost){
    	this.position = position;
    	this.parent = parent;
    	this.gCost = gCost;
    	this.hCost = hCost;
    	this.fCost = this.gCost + this.hCost;
    }
}