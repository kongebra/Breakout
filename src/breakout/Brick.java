package breakout;

import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {
	

	//Assume bricks never extends 
		private static int fastNegativeWidth = 0;
		private static int fastNegativeHeight = 0;
		private static int fastDoubleWidth = 0;
		private static int fastDoubleHeight = 0;
		
		public Brick(double width, double height) {
			super(width, height);
		}
		
		public static void setFastCollDetArea(int width, int height, Ball ball)
		{
			int radius = (int)ball.getRadius();
			fastNegativeWidth = -radius;
			fastNegativeHeight = -radius;
			fastDoubleWidth = width + radius;
			fastDoubleHeight = height + radius;
		}
		
		public boolean collision(Ball ball)
		{
			//Most of the time, ball is far away from brick. Perform a fast integer detection without extra addition.
			//All bricks are equal. Square vs square
			int relativeX = (int)ball.getCenterX() - (int)this.getX();
			if (relativeX < 0)
			{
				if (relativeX < fastNegativeWidth) return false; //Ball center is at least one radius left from brick
			}
			else
			{
				if (relativeX > fastDoubleWidth) return false; //Ball center is at least one radius right from brick
			}
			
			int relativeY = (int)ball.getCenterY() - (int)this.getY();
			if (relativeY < 0)
			{
				if (relativeY < fastNegativeHeight) return false; //Ball center is at least one radius above brick
			}
			else
			{
				if (relativeY > fastDoubleHeight) return false; //Ball center is at least one radius below brick
			}
			
			int ballX = (int)ball.getCenterX();
			int ballY = (int)ball.getCenterY();
			int rectLeft = (int)this.getX();
			int rectRight = rectLeft + (int)this.getWidth();
			int rectTop = (int)this.getY();
			int rectBottom = rectTop + (int)this.getHeight();
			int rectWidth = (int)this.getWidth();
			int rectHeight = (int)this.getHeight();
			
			//Brick center Y:
			if (relativeY >= 0 && relativeY <= rectHeight)
			{
				double sign = 1;
				if (relativeX < rectWidth / 2) sign = -1;
				ball.setDX(Math.abs(ball.getDX()) * sign);
				return true;
			}
			//Brick center X:
			if (relativeX >= 0 && relativeX <= rectWidth)
			{
				double sign = 1;
				if (relativeY < rectHeight / 2) sign = -1;
				ball.setDY(Math.abs(ball.getDY()) * sign);
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
