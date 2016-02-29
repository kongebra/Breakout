package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
	
	private double dx, dy;
	
	public Ball(double radius) {
		super(radius);
		dx = 5;
		dy = 5;
		this.setFill(Color.WHITE);
	}
	
	public void move() {
		this.setCenterX(this.getCenterX() + dx);
		this.setCenterY(this.getCenterY() + dy);
		
		if (this.getCenterX() - this.getRadius() <= 0 || this.getCenterX() >= Game.getWidth() - this.getRadius()) {
			reverseDX();
		}
		if (this.getCenterY() - this.getRadius() <= 0) {
			reverseDY();
		}
		if (this.getCenterY() >= Game.getHeight() - this.getRadius()) {
			// Game Over / Life lost
		}
		
	}
	
	public void reverseDX() {
		dx *= -1;
	}
	
	public void reverseDY() {
		dy *= -1;
	}
	
	public void setDY(double dy) {
		this.dy = dy;
	}
	
	public double getDY() {
		return dy;
	}
	
}
