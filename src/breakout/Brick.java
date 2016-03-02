package breakout;

import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {
	
	public Brick(double width, double height) {
		super(width, height);
	}
	
	// TODO: not working ?! or not using it right
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
		
		int closestX = ballX < rectLeft ? rectLeft : rectRight;
		int closestY = ballY < rectTop ? rectTop : rectBottom;
		if (ball.contains(closestX, closestY))
		{
			//Compare sign on direction and relative position
			if (Integer.signum(ballX - rectLeft) != Math.signum(ball.getDX()))
				ball.reverseDX();
			if (Integer.signum(ballY - rectTop) != Math.signum(ball.getDY()))
				ball.reverseDY();
			return true;
		}
		return false;
	}
	
}
