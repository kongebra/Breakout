package breakout;

import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {
	
	public Brick(double width, double height) {
		super(width, height);
	}
	
	public boolean collision(Ball ball)
	{
		//Quick detection (square vs square)
		if (ball.getCenterX() + ball.getRadius() < this.getX()
				|| ball.getCenterX() - ball.getRadius() > this.getX() + this.getWidth()
				|| ball.getCenterY() + ball.getRadius() < this.getY()
				|| ball.getCenterY() - ball.getRadius() > this.getY() + this.getHeight())
			return false;
		
		//Brick center Y:
		if (ball.getCenterY() > this.getY() && ball.getCenterY() < this.getY() + this.getHeight())
		{
			ball.reverseDX();
			return true;
		}
		//Brick center X:
		if (ball.getCenterX() > this.getX() && ball.getCenterX() < this.getX() + this.getWidth())
		{
			ball.reverseDY();
			return true;
		}
		//Brick corner
		//New direction must be in corner direction
		
		//Upper left corner of brick
		if (ball.contains(this.getX(), this.getY()))
		{
			if (ball.getDX() > 0) ball.reverseDX();
			if (ball.getDY() > 0) ball.reverseDY();
			return true;
		}

		//Upper right corner of brick
		if (ball.contains(this.getX() + this.getWidth(), this.getY()))
		{
			if (ball.getDX() < 0) ball.reverseDX();
			if (ball.getDY() > 0) ball.reverseDY();
			return true;
		}

		//Lower right corner of brick
		if (ball.contains(this.getX() + this.getWidth(), this.getY() + this.getHeight()))
		{
			if (ball.getDX() < 0) ball.reverseDX();
			if (ball.getDY() < 0) ball.reverseDY();
			return true;
		}

		//Lower left corner of brick
		if (ball.contains(this.getX(), this.getY() + this.getHeight()))
		{
			if (ball.getDX() > 0) ball.reverseDX();
			if (ball.getDY() < 0) ball.reverseDY();
			return true;
		}
		return false;
	}
	
}
