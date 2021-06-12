package paka1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Ray extends JPanel{
	int xStart;
	int yStart;
	int xEnd;
	int yEnd;
	int currentY;
	int currentX;
	
	Boolean isDrawn;
	
	Color color;
	
	double angle;
	
	Ray(int x1, int x2, int y1, int y2, double ang, Color col){
		xStart = x1;
		xEnd = x2;
		yStart = y1;
		yEnd = y2;
		currentX = x1;
		currentY=y1;
		angle = ang;
		color = col;
		isDrawn=false;
	}

	public void run(){
		if (currentX < xEnd) {
			currentX+=1;
			currentY = yStart + (int) ((xStart - currentX) * Math.tan(angle));
		}
		
		if (currentX == xEnd) {
			isDrawn = true;
		}
	}
	
}
