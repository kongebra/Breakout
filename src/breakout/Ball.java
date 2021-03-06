/**
 * Gruppe; Svein Are Danielsen, Eirik Stople, Vivi Nygaard
 * ITE1900 Programmering 1, vår 2016
 * Obligatorisk innlevering 3; Breakout
 */

package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

	private double dx, dy;
	private int lastX, lastY;

	private double ballSpeed;

	public Ball(double radius) {
		super(radius);
		dx = 4;
		dy = 4;
		ballSpeed = 7.0;
		lastX = lastY = 0;
		this.setFill(Color.WHITE);
	}

	public Ball(double radius, double speed) {
		super(radius);
		dx = speed;
		dy = speed;
		ballSpeed = speed;
		lastX = lastY = 0;
		this.setFill(Color.WHITE);
	}

	public void move() {
		lastX = (int) this.getCenterX();
		lastY = (int) this.getCenterY();
		this.setCenterX(this.getCenterX() + dx);
		this.setCenterY(this.getCenterY() + dy);

		if (this.getCenterX() - this.getRadius() <= 0) {
			setDX(Math.abs(getDX()));
		}

		if (this.getCenterX() >= Game.getWidth() - this.getRadius()) {
			setDX(-Math.abs(getDX()));
		}

		if (this.getCenterY() - this.getRadius() <= 0) {
			setDY(Math.abs(getDY()));
		}
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
		ballSpeed = speed;
	}

	public double getBallSpeed() {
		return ballSpeed;
	}

	public int getLastX() {
		return lastX;
	}

	public int getLastY() {
		return lastY;
	}

}
