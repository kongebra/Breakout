package breakout;

	import javafx.scene.paint.Color;
	import javafx.scene.shape.Rectangle;

	public class Racket extends Rectangle{
		
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
			//x^2 + y^2 = 5^2
			ball.setDX((ball.getCenterX() - getCenterX()) / getHalfWidth() * Ball.BALL_SPEED);
	                
	                double var = (double)(Ball.BALL_SPEED * Ball.BALL_SPEED) - (ball.getDX() * ball.getDX());
	                if(var >= 0 )
	                    ball.setDY(-Math.sqrt(var));
		}
			

	}
