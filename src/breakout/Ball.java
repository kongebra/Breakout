package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

	private double dx, dy;
	
	private double ballSpeed;
	
	public Ball(double radius) {
		super(radius);
		dx = 4;
		dy = 4;
		ballSpeed = 7.0;
		this.setFill(Color.WHITE);
	}
	
	public Ball(double radius, double speed) {
		super(radius);
		dx = speed;
		dy = speed;
		ballSpeed = speed;
		this.setFill(Color.WHITE);
	}
	
	public void move() {
		this.setCenterX(this.getCenterX() + dx);
		this.setCenterY(this.getCenterY() + dy);
		
		if (this.getCenterX() - this.getRadius() <= 0)
			setDX(Math.abs(getDX()));
		
		if (this.getCenterX() >= Game.getWidth() - this.getRadius())
			setDX(-Math.abs(getDX()));
		
		if (this.getCenterY() - this.getRadius() <= 0)
			setDY(Math.abs(getDY()));
	}
	
	public boolean lost() {
		if (this.getCenterY() >= Game.getHeight() - this.getRadius()) {
			return true;
		}
		return false;
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
	
	public double getDX() {
		return dx;
	}
	
	public void setDX(double dx) {
		this.dx = dx;
	}
	
	public void setSpeed(double speed) {
		dx = speed;
		dy = speed;
	}
	
	public double getBallSpeed() {
		return ballSpeed;
	}
	
}
