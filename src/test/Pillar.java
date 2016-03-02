package test;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Pillar extends Rectangle {
	
	private double dy; 
	
	
	public Pillar(double width, double height) {
		super(width, height);
		dy = Math.random() * 10 + 3;
		
		setFill(new Color(Math.random() * 0.9, Math.random() * 0.9, Math.random() * 0.9, 1));
	}
	
	public double halfHeight() {
		return getHeight() / 2;
	}
	
	public void move() {
		this.setY(this.getY() + dy);
	}
	
	public void reverse() {
		dy *= -1;
	}
	
}
