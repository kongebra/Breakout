package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Racket extends Rectangle {
	
	public Racket(double width, double height) {
		super(width, height);
		this.setFill(Color.WHITE);
	}
	
	public double getHalfWidth() {
		return this.getWidth() / 2;
	}
	
	public double getCenterX() {
		return this.getX() + this.getHalfWidth();
	}
	
	public void bounceBall(Ball ball) {
		if (ball.getDY() > 0) {
			ball.reverseDY();
		}
	}
	
}
