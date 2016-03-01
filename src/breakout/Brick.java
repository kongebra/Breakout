package breakout;

import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {
	
	public Brick(double width, double height) {
		super(width, height);
	}
	
	// TODO: not working ?! or not using it right
	public boolean collision(Ball ball) {
		int x = (int) Math.floor(ball.getCenterX());
		int y = (int) Math.floor(ball.getCenterY());
		
		if (x >= getLayoutX() && x <= getLayoutX() + getWidth()) {
			ball.reverseDY();
			return true;
		} else if (y >= getLayoutY() && y <= getLayoutY() + getHeight()) {
			ball.reverseDX();
			return true;
		}
		return false;
	}
	
}
