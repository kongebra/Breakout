package breakout;

import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {
	
	public Brick(double width, double height) {
		super(width, height);
	}
	
	public boolean collision(Ball ball)
	{
		int ballX = (int)ball.getCenterX();
		int ballY = (int)ball.getCenterY();
		int ballR = (int)ball.getRadius();
		int rectLeft = (int)this.getX();
		int rectRight = rectLeft + (int)this.getWidth();
		int rectTop = (int)this.getY();
		int rectBottom = rectTop + (int)this.getHeight();
		
		//Quick detection (square vs square)
		if (ballX + ballR < rectLeft
				|| ballX - ballR > rectRight
				|| ballY + ballR < rectTop
				|| ballY - ballR > rectBottom)
			return false;
		
		//Brick center Y:
		if (ballY >= rectTop && ballY <= rectBottom)
		{
			ball.reverseDX();
			return true;
		}
		//Brick center X:
		if (ballX >= rectLeft && ballX <= rectRight)
		{
			ball.reverseDY();
			return true;
		}
		//Brick corner
		//New direction must be in corner direction
		
		//Upper left corner of brick
		if (ball.contains(rectLeft, rectTop))
		{
			if (ball.getDX() > 0) ball.reverseDX();
			if (ball.getDY() > 0) ball.reverseDY();
			return true;
		}

		//Upper right corner of brick
		if (ball.contains(rectRight, rectTop))
		{
			if (ball.getDX() < 0) ball.reverseDX();
			if (ball.getDY() > 0) ball.reverseDY();
			return true;
		}

		//Lower right corner of brick
		if (ball.contains(rectRight, rectBottom))
		{
			if (ball.getDX() < 0) ball.reverseDX();
			if (ball.getDY() < 0) ball.reverseDY();
			return true;
		}

		//Lower left corner of brick
		if (ball.contains(rectLeft, rectBottom))
		{
			if (ball.getDX() > 0) ball.reverseDX();
			if (ball.getDY() < 0) ball.reverseDY();
			return true;
		}
		return false;
	}
	
}
