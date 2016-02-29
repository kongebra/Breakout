package breakout;

import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {
	
	public Brick(double width, double height) {
		super(width, height);
	}
	
	public boolean collision(Ball ball) {
		int x = (int) Math.floor(ball.getCenterX());
		int y = (int) Math.floor(ball.getCenterY());
		
		if (x >= getX() && x <= getX() + getWidth()) {
			ball.reverseDY();
			return true;
		} else if (y >= getY() && y <= getY() + getHeight()) {
			ball.reverseDX();
			return true;
		}
		return false;
	}
	
}
