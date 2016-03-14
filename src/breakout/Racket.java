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
			//x^2 + y^2 = speed^2
			final double maxXSpeed = Ball.BALL_SPEED * 0.9;
			double newXSpeed = (ball.getCenterX() - getCenterX()) / getHalfWidth() * Ball.BALL_SPEED;
			if (Math.abs(newXSpeed) > maxXSpeed) newXSpeed = maxXSpeed * Math.signum(newXSpeed);
			ball.setDX(newXSpeed);
			ball.setDY(-Math.sqrt((double)(Ball.BALL_SPEED * Ball.BALL_SPEED) - (ball.getDX() * ball.getDX())));
		}

	}
